package com.chinarewards.gwt.elt.client.staffList.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author lw
 * @since 2012年2月14日 10:35:32
 */
public class StaffListExportResponse implements Result {

	private List message;

	public StaffListExportResponse() {

	}

	public StaffListExportResponse(List message) {
		this.message = message;

	}

	public List getMessage() {
		return message;
	}

	public void setMessage(List message) {
		this.message = message;
	}
}
