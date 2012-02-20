package com.chinarewards.gwt.elt.client.department.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.department.model.DepartmentNode;

public class DepartmentLeaderResponse implements Result {

	private List<DepartmentNode> result;

	public DepartmentLeaderResponse() {
	}

	public DepartmentLeaderResponse(List<DepartmentNode> result) {
		this.result = result;
	}

	public List<DepartmentNode> getResult() {
		return result;
	}

	public void setResult(List<DepartmentNode> result) {
		this.result = result;
	}

}
