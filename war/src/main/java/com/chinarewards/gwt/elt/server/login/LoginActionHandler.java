package com.chinarewards.gwt.elt.server.login;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.chinarewards.elt.model.user.UserSessionVo;
import com.chinarewards.elt.service.user.UserService;
import com.chinarewards.gwt.elt.client.login.LoginRequest;
import com.chinarewards.gwt.elt.client.login.LoginResponse;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

public class LoginActionHandler extends
		BaseActionHandler<LoginRequest, LoginResponse> {
	UserService userService;

	@Inject
	public LoginActionHandler(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Class<LoginRequest> getActionType() {
		return LoginRequest.class;
	}

	@Override
	public LoginResponse execute(LoginRequest action, ExecutionContext context)
			throws DispatchException {

		LoginResponse resp = new LoginResponse();
		UserSessionVo u = userService.authenticate(action.getUserName(),
				action.getPwd());
		if (u != null) {
			resp.setCorporationId(u.getCorporationId());
			resp.setLoginName(u.getUsername());
			resp.setToken(u.getId());
			resp.setDepartmentId(u.getDepartmentId());
			resp.setUserRoles(UserRoleTool.adapt(u.getUserRoles()));

		} else {
			throw new ClientException("login failure!");
		}

		return resp;

	}

	@Override
	public void rollback(LoginRequest action, LoginResponse result,
			ExecutionContext context) throws DispatchException {

	}


}
