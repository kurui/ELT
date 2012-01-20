/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import java.util.List;

import com.chinarewards.gwt.elt.client.order.request.EditOrderResponse;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.support.UserSession;

import net.customware.gwt.dispatch.shared.Action;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 */
public class EditOrderRequest implements Action<EditOrderResponse> {

	String orderId;
	String nowUserId;
	private OrderVo orderVo;
	private UserSession userSession;

	List<String> staffIds;

	public EditOrderRequest(OrderVo orderVo, UserSession userSession) {
		this.orderVo = orderVo;
		this.userSession = userSession;
	}

	/**
	 * For serialization
	 */
	public EditOrderRequest() {
	}

	// public AddOrderRequest(List<String> staffIds, String orderId, String
	// nowUserId) {
	// this.staffIds = staffIds;
	// this.nowUserId = nowUserId;
	// this.orderId = orderId;
	// }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	public OrderVo getOrderVo() {
		return orderVo;
	}

	public void setOrderVo(OrderVo orderVo) {
		this.orderVo = orderVo;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

}
