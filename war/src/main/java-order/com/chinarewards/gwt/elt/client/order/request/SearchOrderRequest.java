/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.order.request.SearchOrderResponse;
import com.chinarewards.gwt.elt.client.order.model.OrderSeacherVo;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;

/**
 * @author yanrui
 */
public class SearchOrderRequest implements Action<SearchOrderResponse> {

	private OrderSeacherVo order;
	private String corporationId;
	private UserRoleVo[] userRoles;
	private String userId;

	public SearchOrderRequest() {
	}

	public SearchOrderRequest(OrderSeacherVo order, String corporationId,
			UserRoleVo[] userRoles, String userId) {
		this.order = order;
		this.corporationId = corporationId;
		this.userRoles = userRoles;
		this.userId = userId;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public UserRoleVo[] getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(UserRoleVo[] userRoles) {
		this.userRoles = userRoles;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public OrderSeacherVo getOrder() {
		return order;
	}

	public void setOrder(OrderSeacherVo order) {
		this.order = order;
	}

}
