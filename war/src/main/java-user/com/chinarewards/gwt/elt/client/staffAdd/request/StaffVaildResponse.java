package com.chinarewards.gwt.elt.client.staffAdd.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:32
 */
public class StaffVaildResponse implements Result {

	private boolean fal;

	public boolean isFal() {
		return fal;
	}

	public void setFal(boolean fal) {
		this.fal = fal;
	}

	public StaffVaildResponse() {

	}

	public StaffVaildResponse(boolean fal) {
		this.fal = fal;

	}

}
