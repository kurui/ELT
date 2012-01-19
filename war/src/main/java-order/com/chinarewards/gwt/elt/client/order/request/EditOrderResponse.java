package com.chinarewards.gwt.elt.client.order.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class EditOrderResponse implements Result {

	String orderLotId;

	public EditOrderResponse() {

	}

	public EditOrderResponse(String id) {

	}

	public String getOrderLotId() {
		return orderLotId;
	}

	public void setOrderLotId(String orderLotId) {
		this.orderLotId = orderLotId;
	}

}
