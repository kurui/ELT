/**
 * 
 */
package com.chinarewards.gwt.elt.server.gift;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.gift.ImportGiftService;
import com.chinarewards.gwt.elt.client.gift.request.UpdateImportGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.UpdateImportGiftResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;


public class UpdateImportGiftActionHandler extends
		BaseActionHandler<UpdateImportGiftRequest, UpdateImportGiftResponse> {

	@InjectLogger
	Logger logger;
	
	ImportGiftService importGiftService;
	
	@Inject
	public UpdateImportGiftActionHandler(ImportGiftService importGiftService) {
		this.importGiftService = importGiftService;
	}

	@Override
	public UpdateImportGiftResponse execute(UpdateImportGiftRequest req,
			ExecutionContext ctx) throws DispatchException {
		boolean fal= importGiftService.updateImportfal(req.getRawId(), req.getFal());
		if(fal==true)
			return new UpdateImportGiftResponse(1);
		else
			return new UpdateImportGiftResponse(0);
	}

	@Override
	public Class<UpdateImportGiftRequest> getActionType() {
		return UpdateImportGiftRequest.class;
	}

	@Override
	public void rollback(UpdateImportGiftRequest req,
			UpdateImportGiftResponse resp, ExecutionContext ctx)
			throws DispatchException {
	}


}
