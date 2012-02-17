package com.chinarewards.gwt.elt.client.department.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.department.model.LeaderSearchResult;

public class SearchLeaderChooseResponse implements Result {

	private LeaderSearchResult result;

	public SearchLeaderChooseResponse() {
	}

	public SearchLeaderChooseResponse(LeaderSearchResult result) {
		this.result = result;
	}

	public LeaderSearchResult getResult() {
		return result;
	}

	public void setResult(LeaderSearchResult result) {
		this.result = result;
	}

}
