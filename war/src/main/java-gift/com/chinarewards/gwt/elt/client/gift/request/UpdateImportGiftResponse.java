package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:32
 */
public class UpdateImportGiftResponse implements Result {

	private int fal;

	public int getFal() {
		return fal;
	}

	public void setFal(int fal) {
		this.fal = fal;
	}

	public UpdateImportGiftResponse() {

	}

	public UpdateImportGiftResponse(int fal) {
		this.fal = fal;

	}
}
