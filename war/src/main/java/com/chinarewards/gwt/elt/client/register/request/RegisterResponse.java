package com.chinarewards.gwt.elt.client.register.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;

public class RegisterResponse implements Result {
	private String token;
	private EnterpriseVo enterprise;

	public EnterpriseVo getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(EnterpriseVo enterprise) {
		this.enterprise = enterprise;
	}

	public RegisterResponse() {

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
