package com.chinarewards.gwt.elt.server.enterprise;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.vo.LicenseBo;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.gwt.elt.client.enterprise.model.LicenseVo;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseRequest;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

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

		LicenseBo licenseBo = licenseService.queryLicenseContent();

		return new SearchLicenseResponse(adapter(licenseBo));

	}

	public LicenseVo adapter(LicenseBo licenseBo) {
		LicenseVo licenseVo = new LicenseVo();
		
		licenseVo.setLicenseId(licenseBo.getLicenseId());
		licenseVo.setCorporationId(licenseBo.getCorporationId());
		licenseVo.setCorporationName(licenseBo.getCorporationName());
		licenseVo.setLicenseType(licenseBo.getLicenseType());
		licenseVo.setMacaddress(licenseBo.getMacaddress());
		licenseVo.setLocalmacaddress(licenseBo.getLocalMACAddress());
		licenseVo.setNotafter(licenseBo.getNotafter());
		licenseVo.setIssued(licenseBo.getIssued());
		licenseVo.setDescription(licenseBo.getDescription());
		licenseVo.setStaffNumber(licenseBo.getStaffNumber());
		
		return licenseVo;
	}

	@Override
	public void rollback(SearchLicenseRequest action,
			SearchLicenseResponse result, ExecutionContext context)
			throws DispatchException {

	}

}
