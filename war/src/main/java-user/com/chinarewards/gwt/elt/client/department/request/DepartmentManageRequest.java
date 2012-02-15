package com.chinarewards.gwt.elt.client.department.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author yanrui
 */
public class DepartmentManageRequest implements
		Action<DepartmentManageResponse> {

	private String corporationId;

	public DepartmentManageRequest() {
	}

	public DepartmentManageRequest(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}
}
