package com.chinarewards.gwt.elt.server.staff;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.staffList.request.DeleteStaffRequest;
import com.chinarewards.gwt.elt.client.staffList.request.DeleteStaffResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the DeleteUserRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class StaffDeleteActionHandler extends
		BaseActionHandler<DeleteStaffRequest, DeleteStaffResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;
	
	@Inject
	public StaffDeleteActionHandler(IStaffService staffService) {
		this.staffService = staffService;
		
	}

	@Override
	public DeleteStaffResponse execute(DeleteStaffRequest request,
			ExecutionContext response) throws DispatchException {
		UserContext context=new UserContext();
		context.setCorporationId(request.getSession().getCorporationId());
		context.setUserId(request.getSession().getToken());
		context.setLoginName(request.getSession().getLoginName());
		context.setUserRoles(UserRoleTool.adaptToRole(request.getSession().getUserRoles()));
		
		String message=staffService.deleteStaff(request.getStaffId(), context);
		return  new DeleteStaffResponse(message);
	}

	@Override
	public Class<DeleteStaffRequest> getActionType() {
		return DeleteStaffRequest.class;
	}

	@Override
	public void rollback(DeleteStaffRequest request,
			DeleteStaffResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
