package com.chinarewards.gwt.elt.client.broadcastSave.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:32
 */
public class BroadcastSaveResponse implements Result {

	String staffId;


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public BroadcastSaveResponse() {

	}
	public BroadcastSaveResponse(String staffId) {
		this.staffId=staffId;

	}

	

}
