/**
 * 
 */
package com.chinarewards.gwt.elt.client.detailsOfGift.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author nicho
 * @since 2012年2月1日 16:55:09
 */
public class DetailsOfGiftRequest implements Action<DetailsOfGiftResponse> {


	private String orderId;
	private String userId;




	public DetailsOfGiftRequest() {
	}

	public DetailsOfGiftRequest(String orderId,String userId) {
		this.orderId = orderId;
		this.userId=userId;

	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	



}
