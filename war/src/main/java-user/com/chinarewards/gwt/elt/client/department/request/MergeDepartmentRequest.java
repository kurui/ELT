package com.chinarewards.gwt.elt.client.department.request;

import net.customware.gwt.dispatch.shared.Action;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 */
public class MergeDepartmentRequest implements Action<MergeDepartmentResponse> {

	String departmentIds;
	private UserSession userSession;

	public MergeDepartmentRequest(String departmentIds) {
		this.departmentIds = departmentIds;
	}
	
	public MergeDepartmentRequest(String departmentIds, UserSession userSession) {
		this.departmentIds = departmentIds;
		this.userSession = userSession;
	}

	/**
	 * For serialization
	 */
	public MergeDepartmentRequest() {
	}

	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
