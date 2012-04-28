/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * @author lw
 * @since 2012年1月20日 19:00:40
 */
public class InitCorpBudgetRequest implements Action<InitCorpBudgetResponse> {

	
	private UserSession userSession;
	private String   manageDepId;

	public InitCorpBudgetRequest() {
	}

	public InitCorpBudgetRequest(UserSession userSession,String   manageDepId) {
		
		this.userSession = userSession;
		this.manageDepId = manageDepId;
	}

	

	public String getManageDepId() {
		return manageDepId;
	}

	public void setManageDepId(String manageDepId) {
		this.manageDepId = manageDepId;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	

}
