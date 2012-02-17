package com.chinarewards.gwt.elt.server.broadcasting;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.staff.StaffSearchCriteria;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.broadcasting.request.SearchBroadcastingListRequest;
import com.chinarewards.gwt.elt.client.broadcasting.request.SearchBroadcastingListResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the SearchBroadcastingListRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class SearchBroadcastingListActionHandler extends
		BaseActionHandler<SearchBroadcastingListRequest, SearchBroadcastingListResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;


	@Inject
	public SearchBroadcastingListActionHandler(IStaffService staffService) {
		this.staffService = staffService;
	}

	@Override
	public SearchBroadcastingListResponse execute(SearchBroadcastingListRequest request,
			ExecutionContext response) throws DispatchException {

		SearchBroadcastingListResponse staffResponse = new SearchBroadcastingListResponse();
		StaffSearchCriteria criteria=new StaffSearchCriteria();
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
		if(request.getCriteria().getStaffNameorNo()!=null)
			criteria.setStaffNameorNo(request.getCriteria().getStaffNameorNo());
		if(request.getCriteria().getStaffStatus()!=null)
			criteria.setStaffStatus(com.chinarewards.elt.model.staff.StaffStatus.valueOf(request.getCriteria().getStaffStatus().toString()));
		
		UserContext context=new UserContext();
		context.setCorporationId(request.getSession().getCorporationId());
		context.setUserId(request.getSession().getToken());
		context.setLoginName(request.getSession().getLoginName());
		context.setUserRoles(UserRoleTool.adaptToRole(request.getSession().getUserRoles()));
		
//		QueryStaffPageActionResult result=staffService.queryBroadcastingList(criteria, context);
//		
//		List<BroadcastingListClient> lt=new ArrayList<BroadcastingListClient>();
//		for (Staff staff:result.getResultList()) {
//			BroadcastingListClient client=new BroadcastingListClient();
//			client.setStaffId(staff.getId());
//			client.setStaffNo(staff.getJobNo());
//			client.setStaffName(staff.getName());
//			client.setPhone(staff.getPhone());
//			client.setJobPosition(staff.getJobPosition());
//			if(staff.getDepartment()!=null)
//			client.setDepartmentName(staff.getDepartment().getName());
//	
//			lt.add(client);
//		}
//		staffResponse.setResult(lt);
//		staffResponse.setTotal(result.getTotal());
		
		return staffResponse;
	}

	@Override
	public Class<SearchBroadcastingListRequest> getActionType() {
		return SearchBroadcastingListRequest.class;
	}

	@Override
	public void rollback(SearchBroadcastingListRequest request,
			SearchBroadcastingListResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
