package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * 
 * @author harry
 *@since 0.2.0
 */
public class AskBudgetAddResponse implements Result {
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
