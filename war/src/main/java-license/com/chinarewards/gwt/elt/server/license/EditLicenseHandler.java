package com.chinarewards.gwt.elt.server.license;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.license.License;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.chinarewards.gwt.elt.client.license.request.EditLicenseRequest;
import com.chinarewards.gwt.elt.client.license.request.EditLicenseResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author YanRui
 * */
public class EditLicenseHandler extends
		BaseActionHandler<EditLicenseRequest, EditLicenseResponse> {

	@InjectLogger
	Logger logger;
	LicenseService licenseService;

	@Inject
	public EditLicenseHandler(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

	@Override
	public Class<EditLicenseRequest> getActionType() {
		return EditLicenseRequest.class;
	}

	@Override
	public EditLicenseResponse execute(EditLicenseRequest action,
			ExecutionContext context) throws DispatchException {
		logger.debug("AddLicenseResponse , license:" + action.getLicenseVo().toString());
		logger.debug("AddLicenseResponse ,rewardId=" + action.getLicenseVo().getId());

		LicenseVo licenseVo = action.getLicenseVo();

		License license = assembleLicense(licenseVo);

		UserContext uc = new UserContext();
		uc.setCorporationId(action.getUserSession().getCorporationId());
		uc.setLoginName(action.getUserSession().getLoginName());
		uc.setUserId(action.getUserSession().getToken());
		uc.setUserRoles(UserRoleTool.adaptToRole(action.getUserSession()
				.getUserRoles()));
		License AdddItem = licenseService.save(uc, license);

		return new EditLicenseResponse(AdddItem.getId());
	}

	/**
	 * Convert from LicenseVo to GeneratorLicenseModel.
	 */
	public static License assembleLicense(LicenseVo licenseVo) {
		License license = new License();
		license.setId(licenseVo.getId());
		license.setName(licenseVo.getName());

		license.setSummary(licenseVo.getSummary());
		license.setDispatchcycle(licenseVo.getDispatchcycle());
		license.setNotes(licenseVo.getNotes());

		license.setExplains(licenseVo.getExplains());
		license.setType(licenseVo.getType().trim());
		license.setBrand(licenseVo.getBrand().trim());
		license.setSource(licenseVo.getSource());
		System.out.println("assembleLicense(licenseVo):" + licenseVo.getSummary());

		license.setPhoto(licenseVo.getPhoto());
		license.setIntegral(licenseVo.getIntegral());
		license.setStock(licenseVo.getStock());

		license.setBusiness(licenseVo.getBusiness().trim());
		license.setAddress(licenseVo.getAddress().trim());
		license.setTell(licenseVo.getTell().trim());
		license.setServicetell(licenseVo.getServicetell().trim());
		
		license.setIndate(licenseVo.getIndate());


		return license;
	}

	@Override
	public void rollback(EditLicenseRequest action, EditLicenseResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
