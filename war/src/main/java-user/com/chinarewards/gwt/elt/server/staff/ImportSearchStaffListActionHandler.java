package com.chinarewards.gwt.elt.server.staff;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.ImportStaffRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.vo.ImportStaffSearchVo;
import com.chinarewards.elt.service.staff.ImportStaffService;
import com.chinarewards.gwt.elt.client.staffList.model.ImportStaffListClient;
import com.chinarewards.gwt.elt.client.staffList.request.ImportStaffListRequest;
import com.chinarewards.gwt.elt.client.staffList.request.ImportStaffListResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the ImportStaffListRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class ImportSearchStaffListActionHandler extends
		BaseActionHandler<ImportStaffListRequest, ImportStaffListResponse> {

	@InjectLogger
	Logger logger;

	ImportStaffService importStaffService;

	@Inject
	public ImportSearchStaffListActionHandler(ImportStaffService importStaffService) {
		this.importStaffService = importStaffService;
	}

	@Override
	public ImportStaffListResponse execute(ImportStaffListRequest request,
			ExecutionContext response) throws DispatchException {

		ImportStaffListResponse staffResponse = new ImportStaffListResponse();
		ImportStaffSearchVo criteria=new ImportStaffSearchVo();
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
		
		PageStore<ImportStaffRaw> result=importStaffService.findImportStaffList(criteria);
		List<ImportStaffListClient> lt=new ArrayList<ImportStaffListClient>();
		for (ImportStaffRaw staff:result.getResultList()) {
			ImportStaffListClient client=new ImportStaffListClient();
			client.setId(staff.getId());
			client.setStaffNo(staff.getStaffNumber());
			client.setStaffName(staff.getName());
			client.setPhone(staff.getMobileTelephoneNumber());
			client.setEmail(staff.getEmailAddress());
			client.setJobPosition(staff.getJobPosition());
			client.setLeadership(staff.getLeadership());
			client.setDepartmentName(staff.getDepartment());
			client.setImportfal(staff.getImportfal());
			lt.add(client);

		}
		staffResponse.setResult(lt);
		staffResponse.setTotal(result.getResultCount());
		staffResponse.setSelectTotal(importStaffService.findImportStaffCount(criteria));
		
		return staffResponse;
	}

	@Override
	public Class<ImportStaffListRequest> getActionType() {
		return ImportStaffListRequest.class;
	}

	@Override
	public void rollback(ImportStaffListRequest request,
			ImportStaffListResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
