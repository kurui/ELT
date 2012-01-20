package com.chinarewards.gwt.elt.server.order;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.slf4j.Logger;

import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.gwt.elt.client.order.request.UpdateOrderStatusRequest;
import com.chinarewards.gwt.elt.client.order.request.UpdateOrderStatusResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class UpdateOrderStatusHandler extends
		BaseActionHandler<UpdateOrderStatusRequest, UpdateOrderStatusResponse> {

	@InjectLogger
	Logger logger;

	OrderService orderService;

	@Inject
	public UpdateOrderStatusHandler(OrderService orderService) {
		this.orderService = orderService;

	}

	@Override
	public UpdateOrderStatusResponse execute(UpdateOrderStatusRequest request,
			ExecutionContext context) throws DispatchException {


		 OrderStatus updateStatus=null;
		 if(OrderStatus.valueOf(request.getStatus().toString())==OrderStatus.INITIAL)
		 updateStatus=OrderStatus.NUSHIPMENTS;
		 else
		 if(OrderStatus.valueOf(request.getStatus().toString())==OrderStatus.NUSHIPMENTS)
		 updateStatus=OrderStatus.SHIPMENTS;
		 String totle = orderService.updateStatus(request.getOrderId(),updateStatus);

		return new UpdateOrderStatusResponse(totle);
	}

	@Override
	public Class<UpdateOrderStatusRequest> getActionType() {
		return UpdateOrderStatusRequest.class;
	}

	@Override
	public void rollback(UpdateOrderStatusRequest req,
			UpdateOrderStatusResponse resp, ExecutionContext cxt)
			throws DispatchException {
	}

}
