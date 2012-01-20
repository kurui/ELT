/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.order.request.UpdateOrderStatusResponse;
import com.chinarewards.gwt.elt.client.order.model.OrderSeacherVo.OrderStatus;

/**
 * @author yanrui
 */
public class UpdateOrderStatusRequest implements Action<UpdateOrderStatusResponse> {

	private String orderId;
	private String userId;
	private OrderStatus status;



	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public UpdateOrderStatusRequest() {
	}

	public UpdateOrderStatusRequest(String orderId,String userId,OrderStatus status) {
		this.orderId = orderId;
		this.userId=userId;
		this.status=status;

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
