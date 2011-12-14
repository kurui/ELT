package com.chinarewards.gwt.elt.server.login;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.service.user.IUserService;
import com.chinarewards.gwt.elt.client.login.LoginRequest;
import com.chinarewards.gwt.elt.client.login.LoginResponse;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.google.inject.Inject;

public class LoginActionHandler extends
		BaseActionHandler<LoginRequest, LoginResponse> {

	IUserService userService;

	@Inject
	public LoginActionHandler(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public Class<LoginRequest> getActionType() {
		return LoginRequest.class;
	}

	@Override
	public LoginResponse execute(LoginRequest action, ExecutionContext context)
			throws DispatchException {
		System.out.println("xxxxxxxxxxxx");
		SysUser u = userService.authenticate(action.getUserName(),
				action.getPwd());
		if (u != null) {
			LoginResponse resp = new LoginResponse();
			resp.setToken("helloxxxx");
			return resp;
		} else {
			throw new ClientException("login failure!");
		}
	}

	@Override
	public void rollback(LoginRequest action, LoginResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
