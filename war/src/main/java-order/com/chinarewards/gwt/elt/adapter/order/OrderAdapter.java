package com.chinarewards.gwt.elt.adapter.order;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.domain.order.Order;
import com.chinarewards.elt.model.order.search.OrderListVo;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.model.OrderSeacherVo.OrderStatus;
import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenter.OrderDisplay;
import com.chinarewards.gwt.elt.util.StringUtil;

/**
 * This utility class use to adapter EJB entity to WAR domain.
 * 
 * @author yanrui
 */
public class OrderAdapter {

	public static OrderVo adapter(OrderListVo order) {
		if (null == order) {
			return null;
		}

		OrderVo result = new OrderVo();

		result.setId(order.getId());
		result.setName(order.getName());
		result.setSource(order.getSource());
		result.setInventory(order.getStock() + "");

		if (order.getStatus() != null) {
			result.setStatus(OrderStatus.valueOf(order.getStatus().toString()));
		}
		return result;
	}

	public static List<OrderVo> adapter(List<OrderListVo> orderList) {
		if (null == orderList) {
			return null;
		}

		List<OrderVo> resultList = new ArrayList<OrderVo>();
		for (OrderListVo order : orderList) {
			resultList.add(adapter(order));
		}
		return resultList;
	}
	
}
