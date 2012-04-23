package com.chinarewards.gwt.elt.server.gift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.slf4j.Logger;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftCodeData;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftCodeData.ParserCode;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftRequest;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftResponse;
import com.chinarewards.elt.service.gift.ImportGiftService;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftAjaxRequestVo;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftAjaxResponseVo;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftAjaxRequest;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftAjaxResponse;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

public class ImportGiftAjaxActionHandler extends
		BaseActionHandler<ImportGiftAjaxRequest, ImportGiftAjaxResponse> {

	@InjectLogger
	Logger logger;

	ImportGiftService importGiftService;

	@Inject
	public ImportGiftAjaxActionHandler(ImportGiftService importGiftService) {
		this.importGiftService = importGiftService;
	}

	@Override
	public ImportGiftAjaxResponse execute(ImportGiftAjaxRequest req,
			ExecutionContext ctx) throws DispatchException {
		logger.debug(" Process in ImportGiftAjaxActionHandler, req.request:{}",
				req.getRequest());

		ImportGiftAjaxResponse resp = new ImportGiftAjaxResponse();

		try {
			ImportGiftAjaxRequestVo reqvo = req.getRequest();
			ImportGiftRequest request = toImportGiftRequest(reqvo);
			request.setNowUserId(req.getUserSession().getToken());
			request.setCorporationId(req.getUserSession().getCorporationId());
			ImportGiftResponse response = new ImportGiftResponse();
			if ("pretreatment".equals(reqvo.getAction())) {
				response = importGiftService.pretreatmentImportGift(request);
			} else if ("import".equals(reqvo.getAction())) {
				int breakNum = 0;
				for (response = importGiftService.finalImportOneGift(request); response
						.isHavingPending(); response = importGiftService
						.finalImportOneGift(request)) {
					logger.debug(
							"ImportGiftAjaxActionHandler - got response = {}",
							response);
					if (breakNum > 10) {
						// sleep 0.1 second every 10 staff imported
						Thread.sleep(100);
						breakNum = 0;
					} else {
						breakNum++;
					}
				}
			}

			logger.debug(
					" Process in ImportGiftAjaxActionHandler, response.getImportGiftRawCode(){}",
					response.getImportGiftRawCode());

			ImportGiftAjaxResponseVo respvo = toImportGiftAjaxResponseVo(response);

			Map<Long, String> allImportGiftCodeInfos = new HashMap<Long, String>();
			Map<Long, String> allImportGiftCodeTypes = new HashMap<Long, String>();
			List<ParserCode> codes = ImportGiftCodeData.getAllImportGiftCode();
			for (ParserCode code : codes) {
				allImportGiftCodeInfos.put(code.getCode(), code.getMessage());
				allImportGiftCodeTypes.put(code.getCode(), code.getType()
						.getMessage());
			}
			respvo.setAllImportGiftCodeInfos(allImportGiftCodeInfos);
			respvo.setAllImportGiftCodeTypes(allImportGiftCodeTypes);

			logger.debug(" Process in ImportGiftAjaxActionHandler, respvo{}",
					respvo);

			resp.setResponse(respvo);

		} catch (Exception e) {
			logger.error("failed to perform loop of import staff!", e);
			throw new ClientException("导入礼品信息失败，请联系系统管理员！");
		}

		return resp;
	}

	@Override
	public Class<ImportGiftAjaxRequest> getActionType() {
		return ImportGiftAjaxRequest.class;
	}

	@Override
	public void rollback(ImportGiftAjaxRequest req,
			ImportGiftAjaxResponse resp, ExecutionContext ctx)
			throws DispatchException {
	}

	public ImportGiftRequest toImportGiftRequest(ImportGiftAjaxRequestVo vo) {
		ImportGiftRequest request = new ImportGiftRequest();
		request.setContentType(vo.getContentType());
		request.setFileName(vo.getFileName());
		request.setHavingTitle(vo.isHavingTitle());
		request.setDobFormat(vo.getDobFormat());
		request.setCorporationId(vo.getCorporationId());
		request.setId(vo.getId());
		return request;
	}

	public ImportGiftAjaxResponseVo toImportGiftAjaxResponseVo(
			ImportGiftResponse response) {
		ImportGiftAjaxResponseVo vo = new ImportGiftAjaxResponseVo();
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
		// this.setGiftRawList(response.getGiftRawList()); -- raw list is for
		// report
		vo.setImportGiftRawCode(response.getImportGiftRawCode());
		return vo;
	}

}
