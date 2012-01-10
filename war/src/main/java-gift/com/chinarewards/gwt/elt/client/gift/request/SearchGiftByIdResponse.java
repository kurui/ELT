/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.request;

import com.chinarewards.gwt.elt.client.gift.module.GiftClient;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author yanrui
 * @since
 */
public class SearchGiftByIdResponse implements Result {

	private GiftClient gift;

	public SearchGiftByIdResponse() {

	}

	public SearchGiftByIdResponse(GiftClient gift) {
		this.gift = gift;
	}

	public GiftClient getGift() {
		return gift;
	}

	public void setGift(GiftClient gift) {
		this.gift = gift;
	}

}
