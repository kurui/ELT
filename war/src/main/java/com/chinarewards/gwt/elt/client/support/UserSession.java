package com.chinarewards.gwt.elt.client.support;

import java.io.Serializable;

public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -359488248052873568L;

	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
