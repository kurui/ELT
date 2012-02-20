package com.chinarewards.gwt.elt.server.broadcastSave;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.broadcast.BroadcastService;
import com.chinarewards.gwt.elt.client.broadcastSave.request.BroadcastSaveRequest;
import com.chinarewards.gwt.elt.client.broadcastSave.request.BroadcastSaveResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the BroadcastSaveRequest.
 * 
 * @author nicho
 * @since 2012年2月20日 18:30:54
 */
public class SearchBroadcastingListActionHandler extends
		BaseActionHandler<BroadcastSaveRequest, BroadcastSaveResponse> {

	@InjectLogger
	Logger logger;

	BroadcastService broadcastService;


	@Inject
	public SearchBroadcastingListActionHandler(BroadcastService broadcastService) {
		this.broadcastService = broadcastService;
	}

	@Override
	public BroadcastSaveResponse execute(BroadcastSaveRequest request,
			ExecutionContext response) throws DispatchException {

		BroadcastSaveResponse staffResponse = new BroadcastSaveResponse();
	
		return staffResponse;
	}

	@Override
	public Class<BroadcastSaveRequest> getActionType() {
		return BroadcastSaveRequest.class;
	}

	@Override
	public void rollback(BroadcastSaveRequest request,
			BroadcastSaveResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
