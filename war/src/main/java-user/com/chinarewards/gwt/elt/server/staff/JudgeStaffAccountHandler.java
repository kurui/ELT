package com.chinarewards.gwt.elt.server.staff;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.user.UserService;
import com.chinarewards.elt.util.StringUtil;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.staff.request.JudgeStaffAccountRequest;
import com.chinarewards.gwt.elt.client.staff.request.JudgeStaffAccountResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * 判断用户是否有账号
 * 
 * @author yanrui
 */
public class JudgeStaffAccountHandler extends
		BaseActionHandler<JudgeStaffAccountRequest, JudgeStaffAccountResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;
	UserService userService;

	@Inject
	public JudgeStaffAccountHandler(UserService userService,
			IStaffService staffService) {
		this.userService = userService;
		this.staffService = staffService;
	}

	@Override
	public JudgeStaffAccountResponse execute(JudgeStaffAccountRequest request,
			ExecutionContext response) throws DispatchException {
		JudgeStaffAccountResponse res = new JudgeStaffAccountResponse();

		String result = "";

		List<OrganicationClient> leaderList = request.getClientList();
		if (leaderList != null) {
			for (int i = 0; i < leaderList.size(); i++) {
				OrganicationClient leader = leaderList.get(i);
				if (leader != null) {
					String staffId = leader.getId();
					if (!StringUtil.isEmptyString(staffId)) {
						Staff staff = staffService.findStaffById(staffId);
						if (staff != null) {
							SysUser user = userService
									.findUserByStaffId(staffId);
							if (user == null) {
								result = result + staff.getName() + ",";
							}
						}
					}
				}
			}
		}
		
		if (!StringUtil.isEmptyString(result)) {
			result+=" 未生成帐号";
		}

		res.setResult(result);

		return res;
	}

	@Override
	public Class<JudgeStaffAccountRequest> getActionType() {
		return JudgeStaffAccountRequest.class;
	}

	@Override
	public void rollback(JudgeStaffAccountRequest request,
			JudgeStaffAccountResponse response, ExecutionContext context)

	throws DispatchException {
	}

}
