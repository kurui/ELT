package com.chinarewards.gwt.elt.client.enterprise.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 */
public class SearchLicenseRequest implements Action<SearchLicenseResponse> {

	String enterpriseId;
	String nowUserId;
	private UserSession userSession;

	List<String> staffIds;

	public SearchLicenseRequest(UserSession userSession) {
		this.userSession = userSession;
	}

	/**
	 * For serialization
	 */
	public SearchLicenseRequest() {
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}



	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

}
