/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.gift.model.OrderSeacherVo;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * @author lw
 * @since 2012年1月20日 19:00:40
 */
public class SearchOrderRequest implements Action<SearchOrderResponse> {

	private OrderSeacherVo orderVo;
	private UserSession userSession;
	

	public SearchOrderRequest() {
	}

	public SearchOrderRequest(OrderSeacherVo orderVo,UserSession userSession) {
		this.orderVo = orderVo;
		this.userSession = userSession;
	}

	public OrderSeacherVo getOrderVo() {
		return orderVo;
	}

	public void setOrderVo(OrderSeacherVo orderVo) {
		this.orderVo = orderVo;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	
	

}
