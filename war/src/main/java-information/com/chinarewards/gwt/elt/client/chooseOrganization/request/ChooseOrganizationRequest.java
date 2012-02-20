package com.chinarewards.gwt.elt.client.chooseOrganization.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.StaffSearchCriteria;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * 
 * 
 * @author cyril
 *
 */
public class ChooseOrganizationRequest implements Action<ChooseOrganizationResponse> {

	private StaffSearchCriteria criteria;

	private UserSession userSession;


	private boolean limitDataByUserRole = false;

	public ChooseOrganizationRequest() {
	}

	public ChooseOrganizationRequest(StaffSearchCriteria criteria,
			UserSession userSession, boolean limitDataByUserRole) {
		this.criteria = criteria;
		this.userSession = userSession;
		this.limitDataByUserRole = limitDataByUserRole;
	}

	public StaffSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(StaffSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	/**
	 * Whether to limit data visible by the caller user (LoginContext). Default
	 * value is <code>false</code>.
	 * 
	 * @return the limitDataByUserRole
	 */
	public boolean isLimitDataByUserRole() {
		return limitDataByUserRole;
	}

	/**
	 * @param limitDataByUserRole
	 *            the limitDataByUserRole to set
	 */
	public void setLimitDataByUserRole(boolean limitDataByUserRole) {
		this.limitDataByUserRole = limitDataByUserRole;
	}

}
