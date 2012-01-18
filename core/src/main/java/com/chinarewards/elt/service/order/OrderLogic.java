package com.chinarewards.elt.service.order;

import com.chinarewards.elt.domain.order.Order;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.model.order.search.OrderVo;

public interface OrderLogic {
	/**
	 * 保存
	 * @param context
	 * @param Order
	 * @return
	 */
	public Order save(SysUser caller, Order Order);

	/**
	 * 查找根据ID
	 * @param id
	 * @return
	 */
	public Order findOrderById(String id);
	
	
	/**
	 * 删除订单根据ID
	 * @param id
	 * @return
	 */
	public String deleteOrder(String id);
	/**
	 * 订单列表
	 * @param context
	 * @param Order
	 * @return
	 */
	public PageStore<OrderVo> OrderList(SysUser caller,OrderVo OrderVo);

	/**
	 * 执行状态
	 * @param id
	 * @return
	 */
	public String updateStatus(String id,OrderStatus status);
}


