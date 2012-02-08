package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class EditCorpBudgetResponse implements Result {

	String giftId;

	public EditCorpBudgetResponse() {

	}

	public EditCorpBudgetResponse(String id) {

	}

	public String getCorpBudgetId() {
		return giftId;
	}

	public void setCorpBudgetId(String giftId) {
		this.giftId = giftId;
	}



}
