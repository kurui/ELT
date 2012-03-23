package com.chinarewards.gwt.elt.client.mail.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.mail.model.MailVo;
import com.chinarewards.gwt.elt.client.support.UserSession;

public class MailSendAllRequest implements Action<MailSendAllResponse> {

	private MailVo mailvo;
	private UserSession userSession;
	List<String[]> organList;
	public List<String[]> getOrganList() {
		return organList;
	}

	public void setOrganList(List<String[]> organList) {
		this.organList = organList;
	}

	public MailSendAllRequest() {
	}

	public MailSendAllRequest(MailVo mailvo, UserSession userSession,List<String[]> organList) {
		this.mailvo = mailvo;
		this.userSession = userSession;
		this.organList = organList;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public MailVo getMailvo() {
		return mailvo;
	}

	public void setMailvo(MailVo mailvo) {
		this.mailvo = mailvo;
	}

	
}
