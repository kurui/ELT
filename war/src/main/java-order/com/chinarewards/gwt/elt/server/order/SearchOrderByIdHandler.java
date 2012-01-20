/**
 * 
 */
package com.chinarewards.gwt.elt.server.order;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.slf4j.Logger;
import com.chinarewards.elt.domain.order.Order;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderByIdRequest;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderByIdResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class SearchOrderByIdHandler extends
		BaseActionHandler<SearchOrderByIdRequest, SearchOrderByIdResponse> {
	@InjectLogger
	Logger logger;
	OrderService orderService;

	@Inject
	public SearchOrderByIdHandler(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public SearchOrderByIdResponse execute(SearchOrderByIdRequest request,
			ExecutionContext context) throws DispatchException {
		logger.debug(" parameters:{}", request.getId());
		Order order = orderService.findOrderById(request.getId());
		return new SearchOrderByIdResponse(adapter(orderService, order));

	}

	private OrderVo adapter(OrderService orderService, Order order) {
		OrderVo orderVo = new OrderVo();
		orderVo.setId(order.getId());
		orderVo.setName(order.getName());
//		orderVo.setExplains(order.getExplains());
//		orderVo.setType(order.getType());
//		orderVo.setSource(order.getSource());
//		orderVo.setBusiness(order.getBusiness());
//		orderVo.setAddress(order.getAddress());
//		orderVo.setTell(order.getTell());
//		orderVo.setPhoto(order.getPhoto());
//		orderVo.setStock(order.getStock());
//		orderVo.setIntegral(order.getIntegral());
//		orderVo.setPhoto(order.getPhoto());
//		// orderVo.setOrderStatus();
//		// orderVo.setDeleted(order.get);
//		orderVo.setIndate(order.getIndate());

		return orderVo;
	}

	@Override
	public Class<SearchOrderByIdRequest> getActionType() {
		return SearchOrderByIdRequest.class;
	}

	@Override
	public void rollback(SearchOrderByIdRequest arg0,
			SearchOrderByIdResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
