package com.chinarewards.gwt.elt.server.broadcasting;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.broadcast.BroadcastService;
import com.chinarewards.gwt.elt.client.broadcasting.request.DeleteBroadcastingRequest;
import com.chinarewards.gwt.elt.client.broadcasting.request.DeleteBroadcastingResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the DeleteBroadcastingRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class DeleteBroadcastingActionHandler extends
		BaseActionHandler<DeleteBroadcastingRequest, DeleteBroadcastingResponse> {

	@InjectLogger
	Logger logger;

	BroadcastService broadcastService;


	@Inject
	public DeleteBroadcastingActionHandler(BroadcastService broadcastService) {
		this.broadcastService = broadcastService;
	}

	@Override
	public DeleteBroadcastingResponse execute(DeleteBroadcastingRequest request,
			ExecutionContext response) throws DispatchException {

		DeleteBroadcastingResponse staffResponse = new DeleteBroadcastingResponse();
		staffResponse.setTotal(broadcastService.deleteBroadcast(request.getBroadcastingId()));
		return staffResponse ;
	}

	@Override
	public Class<DeleteBroadcastingRequest> getActionType() {
		return DeleteBroadcastingRequest.class;
	}

	@Override
	public void rollback(DeleteBroadcastingRequest request,
			DeleteBroadcastingResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
