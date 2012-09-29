package com.chinarewards.gwt.elt.sevlet;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.domain.gift.ImportGiftBatch;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftRawParameter;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftRequest;
import com.chinarewards.elt.service.exception.ImportGiftNotFoundException;
import com.chinarewards.elt.service.gift.ImportGiftService;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * This is a servlet to upload a designated formatted excel, and then save into
 * database to obtain the id as the communication with other steps, finally
 * delete temp file
 * 
 * @author sunhongliang
 * 
 */
public class ImportGiftServlet extends UploadAction {

	private static final long serialVersionUID = 8472033750097764394L;

	Logger logger = LoggerFactory.getLogger(getClass());

	private final static String IMPORT_STAFF_TEMP_FILE_PREFIX = "upld-stff-";
	private final static String IMPORT_STAFF_TEMP_FILE_SUFFIX = ".bin";
	private final static int INSTRUCTION_START_ROW = 0;
	private final static int INSTRUCTION_END_ROW = 10;
	private final static int INSTRUCTION_START_COL = 0;
	private final static int INSTRUCTION_END_COL = 17;
	@Inject
	ImportGiftService importGiftService;
	/**
	 * Override executeAction to save the received files in a custom place and
	 * delete this items from session.
	 */
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {

		if (request.getSession() == null) {
			logger.debug("no user session is available, so could not execute upload action!");
			throw new UploadActionException("Failed to retrieve user session!");
		}

		String response = "";
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				try {
					// compose a xml message with the full file information
					response = obtainUploadedFileTitleInstruction(request, item)
							.toString();
					logger.debug(response);
				} catch (Exception e) {
					e.printStackTrace();
					throw new UploadActionException(e);
				}
			}
		}

		// Remove files from session because we have a temp copy of them
		removeSessionFileItems(request);

		// Send information of the received files to the client.
		return "<response>\n" + response + "</response>\n";
	}

	

	private StringBuffer obtainUploadedFileTitleInstruction(
			HttpServletRequest request, FileItem item) {
		logger.debug("prepare to save session file item into a temp file");
		
		StringBuffer responseSection = new StringBuffer();
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			File f = File.createTempFile(IMPORT_STAFF_TEMP_FILE_PREFIX,
					IMPORT_STAFF_TEMP_FILE_SUFFIX);
			item.write(f);
			Workbook wb = Workbook.getWorkbook(f);
			Sheet sheet = wb.getSheet(0);

			List<ImportGiftRawParameter> pGiftRaws = new ArrayList<ImportGiftRawParameter>();
			int instructionRow = 0;
			for (int row = 0; row < sheet.getRows(); row++) {
				int col = 0;
				List<String> t = new ArrayList<String>();
				ImportGiftRawParameter pGiftRaw = new ImportGiftRawParameter();
				boolean isBlankRow = true;
				for (Cell cell : sheet.getRow(row)) {
					String cellContent = cell.getContents();
					if (cellContent != null && !"".equals(cellContent)) {
						if (isBlankRow) {
							isBlankRow = false;
						}
						cellContent = cellContent.trim();
//						if (col == 4) {//第4列为日期格式解析
//							// dob and doe column
//							c = manufactureDateCell(c, cell);
//						}
					}
					pGiftRaw.setRowPos(new Long(row + 1));
					// header column sorting is designated
					if (col == 0) {
						pGiftRaw.setName(cellContent);
					} else if (col == 1) {
						pGiftRaw.setSourceText(cellContent);
					} else if (col == 2) {
						pGiftRaw.setIntegral(cellContent);
					} else if (col == 3) {
						pGiftRaw.setPrice(cellContent);
					} else if (col == 4) {
						pGiftRaw.setStock(cellContent);
					} else if (col == 5) {
						pGiftRaw.setStatusText(cellContent);
					} 
					if (instructionRow >= INSTRUCTION_START_ROW
							&& instructionRow <= INSTRUCTION_END_ROW) {
						if (col >= INSTRUCTION_START_COL
								&& col <= INSTRUCTION_END_COL) {
							t.add(cellContent == null || cellContent.trim().equals("") ? " " : cellContent);
						}
					}
					col++;
				}
				if (!isBlankRow) {
					if (instructionRow >= INSTRUCTION_START_ROW
							&& instructionRow <= INSTRUCTION_END_ROW) {
						// filter out blank line
						list.add(t);
						instructionRow++;
					}
					pGiftRaws.add(pGiftRaw);
				}
			}
			// 关闭文件
			wb.close();
			
			String nowUserId=request.getParameter("nowUserId");
			String corporationId=request.getParameter("corporationId");
			
			if (importGiftService != null && !StringUtil.isEmpty(nowUserId) && !StringUtil.isEmpty(corporationId)) {

				ImportGiftRequest importRequest = new ImportGiftRequest();
				importRequest.setFileName(item.getFieldName());
				importRequest.setContentType(item.getContentType());
				importRequest.setGiftRawList(pGiftRaws);

				logger.debug("corporationId - corporationId = " + corporationId);
				responseSection.append("<corporationId>").append(corporationId)
						.append("</corporationId>\n");
				importRequest.setCorporationId(corporationId);
				importRequest.setNowUserId(nowUserId);
				ImportGiftBatch batch = importGiftService.createImportGiftBatch(importRequest);

				responseSection.append("<batch-id>").append(batch.getId()).append("</batch-id>\n");
			}

			responseSection.append("<raw-count>").append(pGiftRaws.size())
					.append("</raw-count>\n");

			int row = 0;
			for (List<String> t : list) {
				int col = 0;
				for (String cell : t) {
					responseSection.append("<raw-").append(row).append("-")
							.append(col).append(">").append(cell)
							.append("</raw-").append(row).append("-")
							.append(col).append(">\n");
					col++;
				}
				row++;
			}

			f.deleteOnExit();
		} catch (IOException e) {
			logger.debug(
					"bad excel file, please make sure uploaded file should be a 97 - 2003 excel file",
					e);
			responseSection
					.append("<error-msg>读取Excel文件错误，请重新上传文件！</error-msg>\n");
		} catch (BiffException e) {
			logger.debug(
					"bad excel file, please make sure uploaded file should be a 97 - 2003 excel file",
					e);
			responseSection
					.append("<error-msg>不兼容的Excel文件，请上传一个与Excel 97-2003 完全兼容的文件簿！</error-msg>\n");
		} catch (ImportGiftNotFoundException e) {
			logger.debug(
					"bad excel file, please make sure uploaded file should be a 97 - 2003 excel file",
					e);
			responseSection.append("<error-msg>没有发现导入记录！</error-msg>\n");
		} catch (Exception e) {
			logger.debug(
					"failed to upload a file to temp file, please contact system administrator!",
					e);
			responseSection.append("<error-msg>不能导入文件，请联系系统管理员！</error-msg>\n");
		}
		return responseSection;
	}

	private String manufactureDateCell(String c, Cell cell) {
		if (cell.getType() == CellType.DATE) {
			DateCell dateCell = (DateCell) cell;
			Date date = dateCell.getDate();
			try {
				c = new SimpleDateFormat("yyyy年MM月dd日").format(date);
			} catch (Exception e) {
			}
		} else {
			try { // mandatory conversion
				long tempTimeLong = Long.parseLong(c);
				long ss = (tempTimeLong - 70 * 365 - 17 - 2) * 24 * 3600 * 1000;
				Date date = new Date(ss);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
				c = formatter.format(date);
			} catch (Exception e) {
			}
		}
//		System.out.println("parse date result = " + c);
		return c;
	}
	
	
	/**
	 * Get the content of an uploaded file from client.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		renderXmlResponse(request, response, ERROR_ITEM_NOT_FOUND);
	}

	/**
	 * Remove a file when the user sends a delete request.
	 */
	@Override
	public void removeItem(HttpServletRequest request, String fieldName)
			throws UploadActionException {

	}

}