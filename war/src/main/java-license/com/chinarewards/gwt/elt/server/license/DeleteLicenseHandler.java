package com.chinarewards.gwt.elt.server.license;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.gwt.elt.client.license.request.DeleteLicenseRequest;
import com.chinarewards.gwt.elt.client.license.request.DeleteLicenseResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class DeleteLicenseHandler extends
		BaseActionHandler<DeleteLicenseRequest, DeleteLicenseResponse> {

	@InjectLogger
	Logger logger;

	LicenseService licenseService;

	@Inject
	public DeleteLicenseHandler(LicenseService licenseService) {
		this.licenseService = licenseService;

	}

	@Override
	public DeleteLicenseResponse execute(DeleteLicenseRequest request,
			ExecutionContext context) throws DispatchException {

//wating.....最后修改人,最后修改时间
		String totle = licenseService.deleteLicense(request.getLicenseId());
		
		return new DeleteLicenseResponse(totle);
	}

	
	@Override
	public Class<DeleteLicenseRequest> getActionType() {
		return DeleteLicenseRequest.class;
	}

	@Override
	public void rollback(DeleteLicenseRequest req, DeleteLicenseResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
