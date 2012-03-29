package com.chinarewards.gwt.elt.server.enterprise;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseRequest;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

import de.schlichtherle.license.LicenseContent;

/**
 * @author YanRui
 * */
public class SearchLicenseHandler extends
		BaseActionHandler<SearchLicenseRequest, SearchLicenseResponse> {

	@InjectLogger
	Logger logger;
	LicenseService licenseService;
	
	@Inject
	public SearchLicenseHandler(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

	@Override
	public Class<SearchLicenseRequest> getActionType() {
		return SearchLicenseRequest.class;
	}

	@Override
	public SearchLicenseResponse execute(SearchLicenseRequest action,
			ExecutionContext context) throws DispatchException {


		UserContext uc = new UserContext();
		uc.setCorporationId(action.getUserSession().getCorporationId());
		uc.setLoginName(action.getUserSession().getLoginName());
		uc.setUserId(action.getUserSession().getToken());
		uc.setUserRoles(UserRoleTool.adaptToRole(action.getUserSession()
				.getUserRoles()));


		LicenseContent licenseContent=licenseService.queryLicenseContent();
	
		return new SearchLicenseResponse(licenseContent);

	}


	@Override
	public void rollback(SearchLicenseRequest action,
			SearchLicenseResponse result, ExecutionContext context)
			throws DispatchException {

	}

}
