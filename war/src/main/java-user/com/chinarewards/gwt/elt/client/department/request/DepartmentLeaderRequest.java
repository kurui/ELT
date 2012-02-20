package com.chinarewards.gwt.elt.client.department.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * 
 * 
 * @author yanrui
 * 
 */
public class DepartmentLeaderRequest implements
		Action<DepartmentLeaderResponse> {

	private String corporationId;
	private String departmentId;
	
	public DepartmentLeaderRequest(String corpId,String departmentId){
		this.corporationId=corpId;
		this.departmentId=departmentId;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentIds(String departmentId) {
		this.departmentId = departmentId;
	}

}
