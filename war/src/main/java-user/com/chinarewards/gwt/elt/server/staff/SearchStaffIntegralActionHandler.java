package com.chinarewards.gwt.elt.server.staff;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria.StaffStatus;
import com.chinarewards.gwt.elt.client.staffIntegral.request.StaffIntegralRequest;
import com.chinarewards.gwt.elt.client.staffIntegral.request.StaffIntegralResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the StaffIntegralRequest.
 * 
 * @author yanrui
 */
public class SearchStaffIntegralActionHandler extends
		BaseActionHandler<StaffIntegralRequest, StaffIntegralResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;

	@Inject
	public SearchStaffIntegralActionHandler(IStaffService staffService) {
		this.staffService = staffService;
	}

	@Override
	public StaffIntegralResponse execute(StaffIntegralRequest request,
			ExecutionContext response) throws DispatchException {
		StaffIntegralResponse staffResponse = new StaffIntegralResponse();
		Staff staff = staffService.findStaffById(request.getStaffId());
		staffResponse.setStaffId(staff.getId());
		staffResponse.setStaffNo(staff.getJobNo());
		staffResponse.setStaffName(staff.getName());
		if (staff.getLeadTime() != null)
			staffResponse.setLeadTime(staff.getLeadTime());// 新加的返回的提前颁奖通知时间
		else
			staffResponse.setLeadTime(0);
		if (staff.getDepartment() != null) {
			staffResponse.setDepartmentId(staff.getDepartment().getId());
			staffResponse.setDepartmentName(staff.getDepartment().getName());
		}
		staffResponse.setPhoto(staff.getPhoto());
		staffResponse.setJobPosition(staff.getJobPosition());
		staffResponse.setLeadership(staff.getLeadership());
		staffResponse.setPhone(staff.getPhone());
		staffResponse.setEmail(staff.getEmail());
		staffResponse.setDob(staff.getDob());
		if (staff.getStatus() != null)
			staffResponse.setStatus(StaffStatus.valueOf(staff.getStatus()
					.toString()));
		return staffResponse;
	}

	@Override
	public Class<StaffIntegralRequest> getActionType() {
		return StaffIntegralRequest.class;
	}

	@Override
	public void rollback(StaffIntegralRequest request,
			StaffIntegralResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
