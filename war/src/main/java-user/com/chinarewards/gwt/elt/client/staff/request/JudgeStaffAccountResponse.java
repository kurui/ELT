package com.chinarewards.gwt.elt.client.staff.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class JudgeStaffAccountResponse implements Result {

	private String result;

	public JudgeStaffAccountResponse() {

	}

	public JudgeStaffAccountResponse(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
