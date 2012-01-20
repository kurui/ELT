/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.request;

import com.chinarewards.gwt.elt.client.order.model.OrderVo;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author yanrui
 * @since
 */
public class SearchOrderByIdResponse implements Result {

	private OrderVo orderVo;

	public SearchOrderByIdResponse() {

	}

	public SearchOrderByIdResponse(OrderVo orderVo) {
		this.orderVo = orderVo;
	}

	public OrderVo getOrder() {
		return orderVo;
	}

	public void setOrder(OrderVo orderVo) {
		this.orderVo = orderVo;
	}

}
