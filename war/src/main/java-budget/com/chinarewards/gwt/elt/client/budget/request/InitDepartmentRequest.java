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
public class InitDepartmentRequest implements Action<InitDepartmentResponse> {

	
	private UserSession userSession;
	private String type;//得到的是几级部门
    
	public InitDepartmentRequest() {
	}

	public InitDepartmentRequest(UserSession userSession,String type) {
		
		this.userSession = userSession;
		this.type = type;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	

}
