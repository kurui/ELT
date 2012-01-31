package com.chinarewards.gwt.elt.server.orderConfirmation;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.client.orderConfirmation.request.OrderConfirmationRequest;
import com.chinarewards.gwt.elt.client.orderConfirmation.request.OrderConfirmationResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author lw
 * @since 2012年1月10日 16:09:07
 */
public class SearchOrderConfirmationHandler extends
		BaseActionHandler<OrderConfirmationRequest, OrderConfirmationResponse> {

	@InjectLogger
	Logger logger;

	GiftService giftService;

	@Inject
	public SearchOrderConfirmationHandler(GiftService giftService) {
		this.giftService = giftService;

	}

	@Override
	public OrderConfirmationResponse execute(OrderConfirmationRequest request,
			ExecutionContext context) throws DispatchException {

		OrderConfirmationResponse resp = new OrderConfirmationResponse();
		Gift gift=giftService.findGiftById(request.getGiftId());
		GiftClient client=new GiftClient();
		client.setName(gift.getName());
		client.setPhoto(gift.getPhoto());
		client.setIntegral(gift.getIntegral());
		client.setSource(gift.getSource());
		resp.setResult(client);
		return resp;
	}

	
	 
	@Override
	public Class<OrderConfirmationRequest> getActionType() {
		return OrderConfirmationRequest.class;
	}

	@Override
	public void rollback(OrderConfirmationRequest req, OrderConfirmationResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
