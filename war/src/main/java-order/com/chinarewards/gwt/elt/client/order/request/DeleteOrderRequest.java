/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import com.chinarewards.gwt.elt.client.order.request.DeleteOrderResponse;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author yanrui
 */
public class DeleteOrderRequest implements Action<DeleteOrderResponse> {

	private String orderId;
	private String userId;

	public DeleteOrderRequest() {
	}

	public DeleteOrderRequest(String orderId, String userId) {
		this.orderId = orderId;

		this.userId = userId;

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
