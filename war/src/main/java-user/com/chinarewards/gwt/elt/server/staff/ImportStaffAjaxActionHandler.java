/**
 * 
 */
package com.chinarewards.gwt.elt.server.staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.staff.ImportStaffCodeData;
import com.chinarewards.elt.model.staff.ImportStaffCodeData.ParserCode;
import com.chinarewards.elt.model.staff.ImportStaffRequest;
import com.chinarewards.elt.model.staff.ImportStaffResponse;
import com.chinarewards.elt.service.staff.ImportStaffService;
import com.chinarewards.gwt.elt.client.staff.model.ImportStaffAjaxRequestVo;
import com.chinarewards.gwt.elt.client.staff.model.ImportStaffAjaxResponseVo;
import com.chinarewards.gwt.elt.client.staff.request.ImportStaffAjaxRequest;
import com.chinarewards.gwt.elt.client.staff.request.ImportStaffAjaxResponse;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;


public class ImportStaffAjaxActionHandler extends
		BaseActionHandler<ImportStaffAjaxRequest, ImportStaffAjaxResponse> {

	@InjectLogger
	Logger logger;
	
	ImportStaffService importStaffService;
	
	@Inject
	public ImportStaffAjaxActionHandler(ImportStaffService importStaffService) {
		this.importStaffService = importStaffService;
	}

	@Override
	public ImportStaffAjaxResponse execute(ImportStaffAjaxRequest req,
			ExecutionContext ctx) throws DispatchException {
		logger.debug(
				" Process in ImportStaffAjaxActionHandler, req.request:{}",
				req.getRequest());

		ImportStaffAjaxResponse resp = new ImportStaffAjaxResponse();

		try {
			ImportStaffAjaxRequestVo reqvo = req.getRequest();
			ImportStaffRequest request = toImportStaffRequest(reqvo);
			request.setNowUserId(req.getUserSession().getToken());
			request.setCorporationId(req.getUserSession().getCorporationId());
			ImportStaffResponse response = new ImportStaffResponse();
			if ("pretreatment".equals(reqvo.getAction())) {
				response = importStaffService.pretreatmentImportStaff(request);
			} else if ("import".equals(reqvo.getAction())) {
				int breakNum = 0;
				for (response = importStaffService.finalImportOneStaff(request); response
						.isHavingPending(); response = importStaffService
						.finalImportOneStaff(request)) {
					logger.debug(
							"ImportStaffAjaxActionHandler - got response = {}",
							response);
					if (breakNum > 10) {
						// sleep 0.1 second every 10 staff imported
						Thread.sleep(100);
						breakNum = 0;
					} else {
						breakNum ++;
					}
				}
			}

			logger.debug(
					" Process in ImportStaffAjaxActionHandler, response.getImportStaffRawCode(){}",
					response.getImportStaffRawCode());

			ImportStaffAjaxResponseVo respvo = toImportStaffAjaxResponseVo(response);

			Map<Long, String> allImportStaffCodeInfos = new HashMap<Long, String>();
			Map<Long, String> allImportStaffCodeTypes = new HashMap<Long, String>();
			List<ParserCode> codes = ImportStaffCodeData
					.getAllImportStaffCode();
			for (ParserCode code : codes) {
				allImportStaffCodeInfos.put(code.getCode(), code.getMessage());
				allImportStaffCodeTypes.put(code.getCode(), code.getType()
						.getMessage());
			}
			respvo.setAllImportStaffCodeInfos(allImportStaffCodeInfos);
			respvo.setAllImportStaffCodeTypes(allImportStaffCodeTypes);

			logger.debug(" Process in ImportStaffAjaxActionHandler, respvo{}",
					respvo);

			resp.setResponse(respvo);

		} catch (Exception e) {
			logger.error("failed to perform loop of import staff!", e);
			throw new ClientException("导入员工失败，请联系系统管理员！");
		}

		return resp;
	}

	@Override
	public Class<ImportStaffAjaxRequest> getActionType() {
		return ImportStaffAjaxRequest.class;
	}

	@Override
	public void rollback(ImportStaffAjaxRequest req,
			ImportStaffAjaxResponse resp, ExecutionContext ctx)
			throws DispatchException {
	}

	public ImportStaffRequest toImportStaffRequest(ImportStaffAjaxRequestVo vo) {
		ImportStaffRequest request = new ImportStaffRequest();
		request.setContentType(vo.getContentType());
		request.setFileName(vo.getFileName());
		request.setHavingTitle(vo.isHavingTitle());
		request.setDobFormat(vo.getDobFormat());
		request.setCorporationId(vo.getCorporationId());
		request.setId(vo.getId());
		return request;
	}

	public ImportStaffAjaxResponseVo toImportStaffAjaxResponseVo(
			ImportStaffResponse response) {
		ImportStaffAjaxResponseVo vo = new ImportStaffAjaxResponseVo();
		vo.setContentType(response.getContentType());
		vo.setFileName(response.getFileName());
		vo.setId(response.getId());
		vo.setImportBatchNo(response.getImportBatchNo());
		vo.setHavingTitle(response.isHavingTitle());
		vo.setDobFormat(response.getDobFormat());
		vo.setDoeFormat(response.getDoeFormat());
		vo.setEstimateSuccessDeptNum(response.getEstimateSuccessDeptNum());
		vo.setFinalSuccessDeptNum(response.getFinalSuccessDeptNum());
		vo.setEstNewAssignCardNum(response.getEstNewAssignCardNum());
		vo.setEstNewAutoCardNum(response.getEstNewAutoCardNum());
		vo.setEstOldAssignCardNum(response.getEstOldAssignCardNum());
		vo.setFinalNewAssignCardNum(response.getFinalNewAssignCardNum());
		vo.setFinalNewAutoCardNum(response.getFinalNewAutoCardNum());
		vo.setFinalOldAssignCardNum(response.getFinalOldAssignCardNum());
		vo.setEstimateSuccessNum(response.getEstimateSuccessNum());
		vo.setFinalSuccessNum(response.getFinalSuccessNum());
		vo.setFinalFailedNum(response.getFinalFailedNum());
		vo.setLicenseMessage(response.getLicenseMessage());
		// this.setStaffRawList(response.getStaffRawList()); -- raw list is for
		// report
		vo.setImportStaffRawCode(response.getImportStaffRawCode());
		return vo;
	}

}
