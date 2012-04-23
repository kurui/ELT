package com.chinarewards.gwt.elt.server.gift;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.gift.ImportGiftRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.vo.ImportGiftSearchVo;
import com.chinarewards.elt.service.gift.ImportGiftService;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListClient;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftListRequest;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftListResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the ImportGiftListRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class ImportSearchGiftListActionHandler extends
		BaseActionHandler<ImportGiftListRequest, ImportGiftListResponse> {

	@InjectLogger
	Logger logger;

	ImportGiftService importGiftService;

	@Inject
	public ImportSearchGiftListActionHandler(ImportGiftService importGiftService) {
		this.importGiftService = importGiftService;
	}

	@Override
	public ImportGiftListResponse execute(ImportGiftListRequest request,
			ExecutionContext response) throws DispatchException {

		ImportGiftListResponse staffResponse = new ImportGiftListResponse();
		ImportGiftSearchVo criteria=new ImportGiftSearchVo();
		if (request.getCriteria().getPagination() != null) {
			PaginationDetail detail = new PaginationDetail();
			detail.setLimit(request.getCriteria().getPagination().getLimit());
			detail.setStart(request.getCriteria().getPagination().getStart());

			criteria.setPaginationDetail(detail);
		}

		if (request.getCriteria().getSorting() != null) {
			SortingDetail sortingDetail = new SortingDetail();
			sortingDetail.setSort(request.getCriteria().getSorting().getSort());
			sortingDetail.setDirection(request.getCriteria().getSorting().getDirection());
			criteria.setSortingDetail(sortingDetail);
		}
		criteria.setBatchId(request.getCriteria().getBatchId());
		criteria.setTitlefal(request.getCriteria().isTitlefal());
		criteria.setImportfal(request.getCriteria().isImportfal());
		UserContext context=new UserContext();
		context.setCorporationId(request.getSession().getCorporationId());
		context.setUserId(request.getSession().getToken());
		context.setLoginName(request.getSession().getLoginName());
		context.setUserRoles(UserRoleTool.adaptToRole(request.getSession().getUserRoles()));
		
		PageStore<ImportGiftRaw> result=importGiftService.findImportGiftList(criteria);
		List<ImportGiftListClient> lt=new ArrayList<ImportGiftListClient>();
		for (ImportGiftRaw staff:result.getResultList()) {
			ImportGiftListClient client=new ImportGiftListClient();
			client.setId(staff.getId());
			client.setGiftNo(staff.getGiftNumber());
			client.setGiftName(staff.getName());
			client.setPhone(staff.getMobileTelephoneNumber());
			client.setEmail(staff.getEmailAddress());
			client.setJobPosition(staff.getJobPosition());
			client.setLeadership(staff.getLeadership());
			client.setDob(staff.getDob());
			client.setDepartmentName(staff.getDepartment());
			client.setImportfal(staff.getImportfal());
			lt.add(client);

		}
		staffResponse.setResult(lt);
		staffResponse.setTotal(result.getResultCount());
		staffResponse.setSelectTotal(importGiftService.findImportGiftCount(criteria));
		
		return staffResponse;
	}

	@Override
	public Class<ImportGiftListRequest> getActionType() {
		return ImportGiftListRequest.class;
	}

	@Override
	public void rollback(ImportGiftListRequest request,
			ImportGiftListResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
