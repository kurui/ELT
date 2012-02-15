package com.chinarewards.gwt.elt.server.staff;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.staff.StaffProcess;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.staffAdd.request.StaffAddRequest;
import com.chinarewards.gwt.elt.client.staffAdd.request.StaffAddResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the StaffAddRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class StaffAddActionHandler extends
		BaseActionHandler<StaffAddRequest, StaffAddResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;


	@Inject
	public StaffAddActionHandler(IStaffService staffService) {
		this.staffService = staffService;
	}

	@Override
	public StaffAddResponse execute(StaffAddRequest request,
			ExecutionContext response) throws DispatchException {

		UserContext context=new UserContext();
		context.setCorporationId(request.getSession().getCorporationId());
		context.setUserId(request.getSession().getToken());
		context.setLoginName(request.getSession().getLoginName());
		context.setUserRoles(UserRoleTool.adaptToRole(request.getSession().getUserRoles()));
	
		StaffProcess sp=new StaffProcess();
		sp.setStaffId(request.getStaffId());
		sp.setStaffNo(request.getStaffNo());
		sp.setStaffName(request.getStaffName());
		sp.setDepartmentId(request.getDepartmentId());
		sp.setPhoto(request.getPhoto());
		sp.setPhone(request.getPhone());
		sp.setJobPosition(request.getJobPosition());
		sp.setLeadership(request.getLeadership());
		sp.setEmail(request.getEmail());
		sp.setDob(request.getDob());
		if(request.getStatus()!=null)
		sp.setStatus(com.chinarewards.elt.model.staff.StaffStatus.valueOf(request.getStatus().toString()));
		String staffId=staffService.createOrUpdateStaff(sp, context);
		return  new StaffAddResponse(staffId);
	}

	@Override
	public Class<StaffAddRequest> getActionType() {
		return StaffAddRequest.class;
	}

	@Override
	public void rollback(StaffAddRequest request,
			StaffAddResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
