/**
 * 
 */
package com.chinarewards.gwt.elt.server;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.chinarewards.gwt.elt.client.enterprise.EnterpriseInitRequest;
import com.chinarewards.gwt.elt.client.enterprise.EnterpriseRequest;
import com.chinarewards.gwt.elt.client.login.LoginRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateInitRequest;
import com.chinarewards.gwt.elt.client.server.nominate.NominateActionHandler;
import com.chinarewards.gwt.elt.client.server.staff.HrRegisterActionHandler;
import com.chinarewards.gwt.elt.client.server.user.UserSearchActionHandler;
import com.chinarewards.gwt.elt.client.staff.HrRegisterRequest;
import com.chinarewards.gwt.elt.client.user.UserSearchRequest;
import com.chinarewards.gwt.elt.server.enterprise.*;
import com.chinarewards.gwt.elt.server.login.LoginActionHandler;

/**
 * 
 * @author cyril
 * 
 */
public class ActionModule extends ActionHandlerModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.customware.gwt.dispatch.server.guice.ActionHandlerModule#
	 * configureHandlers()
	 */
	@Override
	protected void configureHandlers() {
		// login module
		bindHandler(LoginRequest.class, LoginActionHandler.class);

		// user module
		bindHandler(UserSearchRequest.class, UserSearchActionHandler.class);
		// staff module
		bindHandler(HrRegisterRequest.class, HrRegisterActionHandler.class);

		//Nominate module
		bindHandler(NominateInitRequest.class, NominateActionHandler.class);

		bindHandler(EnterpriseRequest.class, EnterpriseActionHandler.class);
		bindHandler(EnterpriseInitRequest.class, EnterpriseInitActionHandler.class);

	}
}
