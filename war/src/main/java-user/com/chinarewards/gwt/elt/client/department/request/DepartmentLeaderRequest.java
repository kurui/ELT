package com.chinarewards.gwt.elt.client.department.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author yanrui
 * 
 */
public class DepartmentLeaderRequest implements
		Action<DepartmentLeaderResponse> {

	private String leaderId;

	public DepartmentLeaderRequest()
	{
		
	}
	public DepartmentLeaderRequest(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

}
