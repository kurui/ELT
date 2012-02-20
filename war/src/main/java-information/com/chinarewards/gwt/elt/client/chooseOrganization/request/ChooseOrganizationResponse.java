package com.chinarewards.gwt.elt.client.chooseOrganization.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.rewards.model.StaffSearchResult;

public class ChooseOrganizationResponse implements Result {

	private StaffSearchResult result;

	public ChooseOrganizationResponse() {
	}

	public ChooseOrganizationResponse(StaffSearchResult result) {
		this.result = result;
	}

	public StaffSearchResult getResult() {
		return result;
	}

	public void setResult(StaffSearchResult result) {
		this.result = result;
	}

}
