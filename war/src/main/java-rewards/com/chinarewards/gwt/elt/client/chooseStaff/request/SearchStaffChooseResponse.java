package com.chinarewards.gwt.elt.client.chooseStaff.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.rewards.model.StaffSearchResult;

public class SearchStaffChooseResponse implements Result {

	private StaffSearchResult result;

	public SearchStaffChooseResponse() {
	}

	public SearchStaffChooseResponse(StaffSearchResult result) {
		this.result = result;
	}

	public StaffSearchResult getResult() {
		return result;
	}

	public void setResult(StaffSearchResult result) {
		this.result = result;
	}

}
