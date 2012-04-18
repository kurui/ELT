package com.chinarewards.gwt.elt.client.staffList.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:32
 */
public class UpdateImportStaffResponse implements Result {

	private int fal;

	public int getFal() {
		return fal;
	}

	public void setFal(int fal) {
		this.fal = fal;
	}

	public UpdateImportStaffResponse() {

	}

	public UpdateImportStaffResponse(int fal) {
		this.fal = fal;

	}
}
