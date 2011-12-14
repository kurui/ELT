package com.chinarewards.gwt.elt.client.server.staff;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.user.IUserService;
import com.chinarewards.gwt.elt.client.staff.HrRegisterRequest;
import com.chinarewards.gwt.elt.client.staff.HrRegisterResponse;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the HrRegisterRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class HrRegisterActionHandler extends
		BaseActionHandler<HrRegisterRequest, HrRegisterResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;
	IUserService userService;

	@Inject
	public HrRegisterActionHandler(IStaffService staffService,
			IUserService userService) {
		this.staffService = staffService;
		this.userService = userService;
	}

	@Override
	public HrRegisterResponse execute(HrRegisterRequest request,
			ExecutionContext response) throws DispatchException {
		// FIXME implement this method
		HrRegisterResponse hrResponse = new HrRegisterResponse();
		StaffUserProcess process = new StaffUserProcess();
		process.setUsername(request.getStaffvo().getUsername());
		process.setName(request.getStaffvo().getName());
		process.setEmail(request.getStaffvo().getEmail());
		process.setPassword(request.getStaffvo().getPassword());
		process.setTell(request.getStaffvo().getTell());

		//hrResponse.setStaffId(staffService.createStaff(process));
		
		 hrResponse.setUserId(userService.createUser(process));
		return hrResponse;
	}

	@Override
	public Class<HrRegisterRequest> getActionType() {
		return HrRegisterRequest.class;
	}

	@Override
	public void rollback(HrRegisterRequest request,
			HrRegisterResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
