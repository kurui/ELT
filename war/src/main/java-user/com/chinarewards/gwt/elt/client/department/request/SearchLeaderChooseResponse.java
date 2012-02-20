package com.chinarewards.gwt.elt.client.department.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.department.model.DepartmentLeaderResult;

public class SearchLeaderChooseResponse implements Result {

	private DepartmentLeaderResult result;

	public SearchLeaderChooseResponse() {
	}

	public SearchLeaderChooseResponse(DepartmentLeaderResult result) {
		this.result = result;
	}

	public DepartmentLeaderResult getResult() {
		return result;
	}

	public void setResult(DepartmentLeaderResult result) {
		this.result = result;
	}

}
