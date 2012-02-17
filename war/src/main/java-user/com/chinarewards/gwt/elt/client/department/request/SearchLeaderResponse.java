package com.chinarewards.gwt.elt.client.department.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.department.model.LeaderSearchResult;

public class SearchLeaderResponse implements Result {

	private LeaderSearchResult result;

	public SearchLeaderResponse() {
	}

	public SearchLeaderResponse(LeaderSearchResult result) {
		this.result = result;
	}

	public LeaderSearchResult getResult() {
		return result;
	}

	public void setResult(LeaderSearchResult result) {
		this.result = result;
	}

}
