package com.chinarewards.gwt.elt.server.order;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.gwt.elt.client.order.request.DeleteOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.DeleteOrderResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author nicho
 * @since 2012年1月13日 10:02:37
 */
public class DeleteOrderHandler extends
		BaseActionHandler<DeleteOrderRequest, DeleteOrderResponse> {

	@InjectLogger
	Logger logger;

	OrderService orderService;

	@Inject
	public DeleteOrderHandler(OrderService orderService) {
		this.orderService = orderService;

	}

	@Override
	public DeleteOrderResponse execute(DeleteOrderRequest request,
			ExecutionContext context) throws DispatchException {

//wating.....最后修改人,最后修改时间
//		String totle = orderService.deleteOrder(request.getOrderId());
		
		
//		return new DeleteOrderResponse(totle);
		
		return null;
	}

	
	@Override
	public Class<DeleteOrderRequest> getActionType() {
		return DeleteOrderRequest.class;
	}

	@Override
	public void rollback(DeleteOrderRequest req, DeleteOrderResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
