package com.chinarewards.gwt.elt.client.remote.login;

import com.chinarewards.gwt.elt.client.support.UserSession;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface LoginServiceAsync {

	void authLogin(String username, String password, String verifyCode,
			AsyncCallback<UserSession> callback);

}
