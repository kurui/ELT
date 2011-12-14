package com.chinarewards.gwt.elt.client.login;

import net.customware.gwt.dispatch.shared.Result;

public class LoginResponse implements Result {
	private String token;

	public LoginResponse(){
		
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
