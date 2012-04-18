/**
 * 
 */
package com.chinarewards.gwt.elt.server.staff;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.staff.ImportStaffService;
import com.chinarewards.gwt.elt.client.staffList.request.UpdateImportStaffRequest;
import com.chinarewards.gwt.elt.client.staffList.request.UpdateImportStaffResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;


public class UpdateImportStaffActionHandler extends
		BaseActionHandler<UpdateImportStaffRequest, UpdateImportStaffResponse> {

	@InjectLogger
	Logger logger;
	
	ImportStaffService importStaffService;
	
	@Inject
	public UpdateImportStaffActionHandler(ImportStaffService importStaffService) {
		this.importStaffService = importStaffService;
	}

	@Override
	public UpdateImportStaffResponse execute(UpdateImportStaffRequest req,
			ExecutionContext ctx) throws DispatchException {
		boolean fal= importStaffService.updateImportfal(req.getRawId(), req.getFal());
		if(fal==true)
			return new UpdateImportStaffResponse(1);
		else
			return new UpdateImportStaffResponse(0);
	}

	@Override
	public Class<UpdateImportStaffRequest> getActionType() {
		return UpdateImportStaffRequest.class;
	}

	@Override
	public void rollback(UpdateImportStaffRequest req,
			UpdateImportStaffResponse resp, ExecutionContext ctx)
			throws DispatchException {
	}


}
