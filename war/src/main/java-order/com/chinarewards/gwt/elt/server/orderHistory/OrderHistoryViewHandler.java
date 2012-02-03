package com.chinarewards.gwt.elt.server.orderHistory;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.order.Orders;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.orderHistory.request.OrderHistoryViewRequest;
import com.chinarewards.gwt.elt.client.orderHistory.request.OrderHistoryViewResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class OrderHistoryViewHandler extends
		BaseActionHandler<OrderHistoryViewRequest, OrderHistoryViewResponse> {

	@InjectLogger
	Logger logger;

	OrderService orderService;
	GiftService giftService;
	IStaffService staffService;

	@Inject
	public OrderHistoryViewHandler(OrderService orderService,
			GiftService giftService, IStaffService staffService) {
		this.orderService = orderService;
		this.giftService = giftService;
		this.staffService = staffService;
	}

	@Override
	public OrderHistoryViewResponse execute(OrderHistoryViewRequest request,
			ExecutionContext context) throws DispatchException {

		System.out.println("OrderHistoryViewHandler-----execute()----");

		OrderHistoryViewResponse response = new OrderHistoryViewResponse();

		Orders orders = orderService.findOrderById(request.getOrderId());
		OrderVo orderVo = new OrderVo();
		orderVo.setId(orders.getId());
		orderVo.setOrderCode(orders.getOrderCode());
		orderVo.setExchangeDate(orders.getExchangeDate());
		// orderVo.setStatus(orders.getStatus());
		orderVo.setReceiver(orders.getReceiver());
		orderVo.setTel(orders.getTel());
		orderVo.setAddress(orders.getAddress());
		orderVo.setPostcode(orders.getPostcode());

		response.setOrderVo(orderVo);

		// Gift gift = giftService.findGiftById(request.getGiftId());
		// GiftClient client = new GiftClient();
		// client.setName(gift.getName());
		// client.setPhoto(gift.getPhoto());
		// client.setIntegral(gift.getIntegral());
		// client.setSource(gift.getSource());
		// response.setResult(client);

		// 查询员工积分
		// response.setStaffBalance(staffService.getBalance(request.getStaffId()));

		return response;
	}

	@Override
	public Class<OrderHistoryViewRequest> getActionType() {
		return OrderHistoryViewRequest.class;
	}

	@Override
	public void rollback(OrderHistoryViewRequest arg0,
			OrderHistoryViewResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
