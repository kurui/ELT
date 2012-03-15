package com.chinarewards.gwt.elt.client.remote.login;

import com.chinarewards.gwt.elt.client.support.UserSession;
import com.chinarewards.gwt.elt.model.ClientException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService {

	public UserSession authLogin(String username, String password,String verifyCode) throws ClientException;

	
}
