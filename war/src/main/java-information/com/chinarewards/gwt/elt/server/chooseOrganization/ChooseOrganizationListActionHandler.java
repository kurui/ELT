package com.chinarewards.gwt.elt.server.chooseOrganization;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.dao.org.StaffDao.QueryStaffPageActionResult;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.staff.StaffSearchCriteria;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.org.CorporationService;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.elt.service.org.TeamService;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.chooseOrganization.model.OrganSearchCriteria.OrganType;
import com.chinarewards.gwt.elt.client.chooseOrganization.model.OrganSearchResult;
import com.chinarewards.gwt.elt.client.chooseOrganization.request.ChooseOrganizationRequest;
import com.chinarewards.gwt.elt.client.chooseOrganization.request.ChooseOrganizationResponse;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the ChooseOrganizationRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class ChooseOrganizationListActionHandler
		extends
		BaseActionHandler<ChooseOrganizationRequest, ChooseOrganizationResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;
	DepartmentService departmentService;
	CorporationService corporationService;
	TeamService teamService;

	@Inject
	public ChooseOrganizationListActionHandler(IStaffService staffService,
			DepartmentService departmentService,
			CorporationService corporationService, TeamService teamService) {
		this.staffService = staffService;
		this.departmentService = departmentService;
		this.corporationService = corporationService;
		this.teamService = teamService;
	}

	@Override
	public ChooseOrganizationResponse execute(
			ChooseOrganizationRequest request, ExecutionContext response)
			throws DispatchException {

		ChooseOrganizationResponse staffResponse = new ChooseOrganizationResponse();
		if(request.getCriteria().getOrganType()==OrganType.STAFF)
		{
			return queryStaff(request);
		}
		else if(request.getCriteria().getOrganType()==OrganType.DEPT)
		{
			
		}
		else if(request.getCriteria().getOrganType()==OrganType.GROUP)
		{
			
		}
		else if(request.getCriteria().getOrganType()==OrganType.ORG)
		{
			
		}
		return staffResponse;
	}

	private ChooseOrganizationResponse queryStaff(ChooseOrganizationRequest request)
	{
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
		if(request.getCriteria().getKey()!=null)
			criteria.setStaffNameorNo(request.getCriteria().getKey());

		UserContext context=new UserContext();
		context.setCorporationId(request.getSession().getCorporationId());
		context.setUserId(request.getSession().getToken());
		context.setLoginName(request.getSession().getLoginName());
		context.setUserRoles(UserRoleTool.adaptToRole(request.getSession().getUserRoles()));
		
		QueryStaffPageActionResult result=staffService.queryStaffList(criteria, context);
		
		List<OrganicationClient> lt=new ArrayList<OrganicationClient>();
		
		for (Staff staff:result.getResultList()) {
			OrganicationClient client=new OrganicationClient();
			client.setId(staff.getId());
			client.setName(staff.getName());
			lt.add(client);
		}
		OrganSearchResult rs=new OrganSearchResult();
		rs.setResult(lt);
		rs.setTotal(result.getTotal());
		return new ChooseOrganizationResponse(rs);
	}
	@Override
	public Class<ChooseOrganizationRequest> getActionType() {
		return ChooseOrganizationRequest.class;
	}

	@Override
	public void rollback(ChooseOrganizationRequest request,
			ChooseOrganizationResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
