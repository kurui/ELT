package com.chinarewards.gwt.elt.client.orderHistory.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author yanrui
 */
public class OrderHistoryViewRequest implements Action<OrderHistoryViewResponse> {


	private String orderId;
	private String userId;




	public OrderHistoryViewRequest() {
	}

	public OrderHistoryViewRequest(String orderId,String userId) {
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
