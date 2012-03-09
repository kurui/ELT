package com.chinarewards.gwt.elt.client.staff.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * @author yanrui
 */
public class JudgeStaffAccountRequest implements
		Action<JudgeStaffAccountResponse> {

	private String staffId;
	private List<OrganicationClient> clientList;

	private UserSession userSession;

	public JudgeStaffAccountRequest() {
	}

	public JudgeStaffAccountRequest(String staffId) {
		this.staffId = staffId;
	}

	public JudgeStaffAccountRequest(List<OrganicationClient> clientList) {
		this.clientList = clientList;
	}

	public JudgeStaffAccountRequest(List<OrganicationClient> clientList,
			UserSession userSession) {
		this.clientList = clientList;
		this.userSession = userSession;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public List<OrganicationClient> getClientList() {
		return clientList;
	}

	public void setClientList(List<OrganicationClient> clientList) {
		this.clientList = clientList;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
