package com.chinarewards.gwt.elt.server;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.service.order.OrderService;

public class AutoUpdateOrderTask extends TimerTask {
	Logger logger = LoggerFactory.getLogger(this.getClass());


	private OrderService orderService;
	private static AutoUpdateOrderTask instance;


	private AutoUpdateOrderTask() {
	}

	public static AutoUpdateOrderTask getInstance() {
		if (instance == null) {
			instance = new AutoUpdateOrderTask();
		}

		return instance;
	}


	@Override
	public void run() {
		logger.info(" BEGIN to RUN AutoGenerateRewardTask ");
		  orderService.AutoUpdateStatusForOrder(7,"INITIAL");  //过期没有付积分的
		  orderService.AutoUpdateStatusForOrder(45,"SHIPMENTS");  //过期没有确认收货的
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
