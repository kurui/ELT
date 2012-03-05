package com.chinarewards.gwt.elt.client.mail.model;

import java.io.Serializable;
import java.util.Date;

public class MailVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emailAddress;
	private String content;
	public MailVo(){
		
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}