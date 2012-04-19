package com.chinarewards.gwt.elt.sevlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.model.staff.ImportCodeType;
import com.chinarewards.elt.model.staff.ImportStaffCodeData;
import com.chinarewards.elt.model.staff.ImportStaffCodeData.ParserCode;
import com.chinarewards.elt.model.staff.ImportStaffRawParameter;
import com.chinarewards.elt.model.staff.ImportStaffResponse;
import com.chinarewards.elt.model.staff.ImportStaffResultType;
import com.chinarewards.elt.service.staff.ImportStaffService;
import com.google.inject.Inject;


public class ImportStaffReportServlet extends HttpServlet {

	private static final long serialVersionUID = 5918652591233381471L;

	Logger logger = LoggerFactory.getLogger(ImportStaffReportServlet.class);

	private WritableWorkbook wb;

	private String fileName = "report.xls";

	@Inject
	ImportStaffService importStaffService;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String batchId = request.getParameter("batchId");
		boolean isOriginalDoc = request.getParameter("action") != null
				&& request.getParameter("action").equals("original") ? true
				: false;
		boolean isFatalDoc = request.getParameter("action") != null
				&& request.getParameter("action").equals("fatal") ? true
				: false;
		boolean isTemplate = request.getParameter("action") != null
				&& request.getParameter("action").equals("template") ? true
				: false;
		logger.debug("Process in servlet ExportStaffReportsServlet, url = "
				+ request.getRequestURL() + " - " + request.getQueryString());

		ImportStaffResponse result = null;

			if (importStaffService != null) {
				result = importStaffService.getImportStaffReport(batchId);
				logger.debug("ImportStaffReportServlet - doGet - result = {}",result);			
			} 
		

		// generate a excel to temporary file.
		try {
			File tempFile = File.createTempFile("impt-stff", ".xls");
			wb = Workbook.createWorkbook(tempFile);
			int iSheet = 0;
//			if (!isOriginalDoc && !isTemplate) {
//				WritableSheet sheetDept = wb.createSheet("部门", iSheet);
//				generateHeaderLabelForDepartment(sheetDept);
//				generateBodyLabelForDepartment(sheetDept, result);
//				iSheet++;
//			}
			String sheetStaffTitle = isTemplate ? "导入模版"
					: (isFatalDoc ? "导入结果失败分析" : (isOriginalDoc ? "原始文件内容"
							: "导入结果预览"));
			WritableSheet sheetStaff = wb.createSheet(sheetStaffTitle, iSheet);
			generateHeaderLabelForStaffSheet(sheetStaff, isOriginalDoc || isTemplate, isFatalDoc);
			generateBodyLabelForStaffSheet(sheetStaff, result, isOriginalDoc || isTemplate, isFatalDoc);

			wb.write();

			wb.close();

			logger.trace(
					"tempFile information: "
							+ "exist={}, isDirectory={}, name={}, path={}, length={}",
					new Object[] { tempFile.exists(), tempFile.isDirectory(),
							tempFile.getName(), tempFile.getParent(),
							tempFile.length() });
			// prepare the InputStream object for serving the excel file stream.
			logger.trace(
					"Preparing CloseOnExitInputStream object for input stream result for file {}",
					tempFile);
//			CloseOnExitInputStream is = new CloseOnExitInputStream(tempFile);
			InputStream is = new FileInputStream(tempFile);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			response.setHeader("Content-Transfer-Encoding","binary");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			OutputStream os = response.getOutputStream();
			byte[] data = new byte[1024];
			int length;
			while ((length = is.read(data)) != -1) {
				os.write(data, 0, length);
			}
			os.flush();
			os.close();
			is.close();

		} catch (FileNotFoundException e) {
			logger.error("cannot open the temp file", e);
		} catch (IOException ioe) {
			logger.error("export quality excel file failed.", ioe);
		} catch (RowsExceededException e) {
			logger.error("There are too many rows have been written to"
					+ " the current sheet.", e);
		} catch (WriteException e) {
			logger.error("Error in writing data to the current sheet.", e);
		} catch (Exception e) {
			logger.error("System error.", e);
		} finally {
			if (null != wb) {
				try {
					wb.close();
				} catch (Exception e) {
					logger.warn("close stream error.");
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * get file(excel) header columns.
	 * 
	 * @param isOriginalDoc
	 * 
	 * @return
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void generateHeaderLabelForStaffSheet(WritableSheet sheet,
			boolean isOriginalDoc, boolean isFatalDoc)
			throws RowsExceededException, WriteException {
		logger.debug("generate excel's header for staff sheet.");

		// cell font format.
		WritableFont wf = new jxl.write.WritableFont(
				WritableFont.createFont("宋体"), 12);

		jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);
		CellView cv = new CellView();
		cv.setAutosize(true);
		sheet.setRowView(16, 40);
		// set header
		int col = 0;
		cv.setSize(11);
		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "员工编号", wcfF));

		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "员工姓名", wcfF));

		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "邮箱", wcfF));

		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "电话", wcfF));

		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "生日", wcfF));

		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "部门", wcfF));

		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "职位", wcfF));

		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "直属领导", wcfF));


		if (isOriginalDoc)
			return;
		sheet.setRowView(16, 100);
		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "严重错误", wcfF));

		if (isFatalDoc)
			return;
		sheet.setColumnView(col, cv);
		sheet.addCell(new Label(col++, 0, "警告", wcfF));
		sheet.setRowView(16, 40);
	}

	private void generateBodyLabelForStaffSheet(WritableSheet sheet,
			ImportStaffResponse result, boolean isOriginalDoc,
			boolean isFatalDoc) throws Exception {
		logger.debug("generate excel's body for staff sheet.");
		// populate import code message
		List<ParserCode> codes = ImportStaffCodeData.getAllImportStaffCode();
		Map<Long, ParserCode> codeMap = new HashMap<Long, ParserCode>();
		for (ParserCode code : codes) {
			codeMap.put(code.getCode(), code);
		}
		// cell font format.
		WritableFont wf = new jxl.write.WritableFont(
				WritableFont.createFont("宋体"), 12);
		jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);
		List<ImportStaffRawParameter> records = result.getStaffRawList();
		List<List<Long>> rawCodes = result.getImportStaffRawCode();
		int fatalSpan = 0;
		for (int i = 0; i < records.size(); i++) {

			int col = 0;
			int row = i - fatalSpan + 1;
			ImportStaffRawParameter record = records.get(i);

			boolean isFatal = false;
			StringBuffer warnCellMsg = new StringBuffer();
			StringBuffer fatalCellMsg = new StringBuffer();
			for (Long c : rawCodes.get(i)) {
				ParserCode code = codeMap.get(c);
				if (code.getType().compareTo(ImportCodeType.FATAL) == 0) {
					fatalCellMsg.append(" - ").append(code.getMessage())
							.append("\r\n");
					if (isFatalDoc) {
						isFatal = true;
					}
				} else if (code.getType().compareTo(ImportCodeType.WARN) == 0) {
					warnCellMsg.append(" - ").append(code.getMessage())
							.append("\r\n");
				}
			}
			if (isFatalDoc) {
				if (ImportStaffResultType.FAILURE.equals(record.getResult())) {
					// final import staff is failure
					if (!isFatal) {
						fatalCellMsg.append(" - 系统或网络问题\r\n");
						isFatal = true;
					}
				}
				if (!isFatal) {
					fatalSpan++;
					continue; // ignore non-fatal record, only fatal record
								// exported
				}
			}

			sheet.addCell(new Label(col++, row, record.getJobNo(), wcfF));

			sheet.addCell(new Label(col++, row, record.getName(), wcfF));

			sheet.addCell(new Label(col++, row, record.getEmail(), wcfF));

			sheet.addCell(new Label(col++, row, record.getPhone(),
					wcfF));

			sheet.addCell(new Label(col++, row, record.getDob(),
					wcfF));
			sheet.addCell(new Label(col++, row, record.getDepartment(), wcfF));


			sheet.addCell(new Label(col++, row, record.getJobPosition(), wcfF));

			sheet.addCell(new Label(col++, row, record.getLeadership(), wcfF));


			if (isOriginalDoc)
				continue;

			sheet.addCell(new Label(col++, row, fatalCellMsg.toString(), wcfF));

			if (isFatalDoc)
				continue;

			sheet.addCell(new Label(col++, row, warnCellMsg.toString(), wcfF));

		}
	}

//	/**
//	 * get file(excel) header columns.
//	 * 
//	 * @param isOriginalDoc
//	 * 
//	 * @return
//	 * @throws WriteException
//	 * @throws RowsExceededException
//	 */
//	private void generateHeaderLabelForDepartment(WritableSheet sheet)
//			throws RowsExceededException, WriteException {
//		logger.debug("generate excel's header for department sheet.");
//
//		// cell font format.
//		WritableFont wf = new jxl.write.WritableFont(
//				WritableFont.createFont("宋体"), 12);
//
//		jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);
//		CellView cv = new CellView();
//		cv.setAutosize(true);
//		sheet.setRowView(16, 40);
//		// set header
//		int col = 0;
//		cv.setSize(20);
//		sheet.setColumnView(col, cv);
//		sheet.addCell(new Label(col++, 0, "部门", wcfF));
//	}
//
//	private void generateBodyLabelForDepartment(WritableSheet sheet,
//			ImportStaffResponse result) throws Exception {
//		logger.debug("generate excel's body for department sheet.");
//		// populate import department message
//		List<String> departmentRaws = result.getImportStaffDepartment();
//		if (departmentRaws == null) {
//			departmentRaws = new ArrayList<String>();
//		}
//		// cell font format.
//		WritableFont wf = new jxl.write.WritableFont(
//				WritableFont.createFont("宋体"), 12);
//		jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);
//		for (int i = 0; i < departmentRaws.size(); i++) {
//
//			int row = i + 1;
//			String departmentRaw = departmentRaws.get(i);
//			List<String> department = ImportStaffParser.convertRaw2Department(departmentRaw);
//
//			for (int col = 0; col < department.size(); col ++) {
//				sheet.addCell(new Label(col, row, department.get(col), wcfF));
//			}
//
//		}
//	}



	protected String formatDate(Date src) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",
				Locale.CHINESE);
		return format.format(src);
	}
}
