package com.chinarewards.gwt.elt.client.mail.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;

public class MailSendAllResponse implements Result {
	private String token;
		
	public MailSendAllResponse() {

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
