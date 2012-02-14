/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author lw
 * @since 2012年1月20日 19:00:32
 */
public class AddDepartmentBudgetResponse implements Result {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
	public AddDepartmentBudgetResponse(String message) {
		this.message = message;
	}

	public AddDepartmentBudgetResponse() {

	}
	
}
