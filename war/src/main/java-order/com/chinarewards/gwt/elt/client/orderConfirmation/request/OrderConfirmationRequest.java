/**
 * 
 */
package com.chinarewards.gwt.elt.client.orderConfirmation.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author nicho
 * @since 2012年1月31日 18:52:22
 */
public class OrderConfirmationRequest implements Action<OrderConfirmationResponse> {


	private String giftId;



	public OrderConfirmationRequest() {
	}

	public OrderConfirmationRequest(String giftId) {
		this.giftId = giftId;

	}

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	



}
