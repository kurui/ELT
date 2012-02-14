package com.chinarewards.gwt.elt.client.department.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.integralManagement.model.Category;

/**
 * @author yanrui
 */
public class DepartmentManageResponse implements Result {

	private List<Category> result;

	public DepartmentManageResponse(List<Category> result) {
		this.result = result;
	}

	public DepartmentManageResponse() {
	}

	public List<Category> getResult() {
		return result;
	}

	public void setResult(List<Category> result) {
		this.result = result;
	}

}
