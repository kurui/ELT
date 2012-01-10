package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class AddGiftResponse implements Result {

	String giftLotId;

	public AddGiftResponse() {

	}

	public AddGiftResponse(String id) {

	}

	public String getGiftLotId() {
		return giftLotId;
	}

	public void setGiftLotId(String giftLotId) {
		this.giftLotId = giftLotId;
	}

}
