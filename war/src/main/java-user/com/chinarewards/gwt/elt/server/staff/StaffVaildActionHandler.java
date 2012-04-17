package com.chinarewards.gwt.elt.server.staff;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.chinarewards.elt.service.user.UserService;
import com.chinarewards.gwt.elt.client.staffAdd.request.StaffVaildRequest;
import com.chinarewards.gwt.elt.client.staffAdd.request.StaffVaildResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * The handler to correspond the StaffVaildRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class StaffVaildActionHandler extends
		BaseActionHandler<StaffVaildRequest, StaffVaildResponse> {


	UserService userService;

	@Inject
	public StaffVaildActionHandler(UserService userService) {
		this.userService = userService;
	
	}

	@Override
	public StaffVaildResponse execute(StaffVaildRequest request,
			ExecutionContext response) throws DispatchException {
		boolean fal=false;
		if(!StringUtil.isEmpty(request.getStaffNo()))
		{
			fal=userService.vaildStaffNo(request.getStaffNo(),request.getNowStaffId());
		}
		if(!StringUtil.isEmpty(request.getStaffEmail()))
		{
			fal=userService.vaildStaffEmail(request.getStaffEmail(),request.getNowStaffId());
		}
		return new StaffVaildResponse(fal);
	}

	@Override
	public Class<StaffVaildRequest> getActionType() {
		return StaffVaildRequest.class;
	}

	@Override
	public void rollback(StaffVaildRequest request,
			StaffVaildResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
