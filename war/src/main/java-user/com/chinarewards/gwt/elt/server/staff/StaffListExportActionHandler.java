package com.chinarewards.gwt.elt.server.staff;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.model.staff.StaffSearchCriteria;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.staffList.request.StaffListExportRequest;
import com.chinarewards.gwt.elt.client.staffList.request.StaffListExportResponse;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the StaffListExportRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class StaffListExportActionHandler extends
		BaseActionHandler<StaffListExportRequest, StaffListExportResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;


	@Inject
	public StaffListExportActionHandler(IStaffService staffService) {
		this.staffService = staffService;
	}

	@Override
	public StaffListExportResponse execute(StaffListExportRequest request,
			ExecutionContext response) throws DispatchException {

		
		StaffSearchCriteria criteria=new StaffSearchCriteria();
			
		if(request.getCriteria().getStaffNameorNo()!=null)
			criteria.setStaffNameorNo(request.getCriteria().getStaffNameorNo());
		if(request.getCriteria().getStaffStatus()!=null)
			criteria.setStaffStatus(com.chinarewards.elt.model.staff.StaffStatus.valueOf(request.getCriteria().getStaffStatus().toString()));
		if(request.getCriteria().getStaffRole()!=null)
			criteria.setStaffRole(UserRole.valueOf(request.getCriteria().getStaffRole().toString()));
		if(request.getCriteria().isColleaguePage()==true)
			criteria.setColleaguePage(request.getCriteria().isColleaguePage());
		if(!StringUtil.isEmpty(request.getCriteria().getDepartmentId()))
			criteria.setDepartmentId(request.getCriteria().getDepartmentId());
		
		UserContext context=new UserContext();
		context.setCorporationId(request.getSession().getCorporationId());
		context.setUserId(request.getSession().getToken());
		context.setLoginName(request.getSession().getLoginName());
		context.setUserRoles(UserRoleTool.adaptToRole(request.getSession().getUserRoles()));
		List<Staff> list=staffService.queryStaffListExport(criteria, context);
		

		return new StaffListExportResponse(list);
	}

	@Override
	public Class<StaffListExportRequest> getActionType() {
		return StaffListExportRequest.class;
	}

	@Override
	public void rollback(StaffListExportRequest request,
			StaffListExportResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
