package com.chinarewards.gwt.elt.server.login;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.chinarewards.gwt.elt.client.login.LoginRequest;
import com.chinarewards.gwt.elt.client.login.LoginResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;

public class LoginActionHandler extends
		BaseActionHandler<LoginRequest, LoginResponse> {

	@Override
	public Class<LoginRequest> getActionType() {
		return LoginRequest.class;
	}

	@Override
	public LoginResponse execute(LoginRequest action, ExecutionContext context)
			throws DispatchException {
		System.out.println("xxxxxxxxxxxx");
		LoginResponse resp = new LoginResponse();
		resp.setToken("helloxxxx");
		return resp;
		// SysUser u = userService.authenticate(action.getUserName(),
		// action.getPwd());
		// if (u != null) {
		// } else {
		// throw new ClientException("login failure!");
		// }
	}

	@Override
	public void rollback(LoginRequest action, LoginResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
