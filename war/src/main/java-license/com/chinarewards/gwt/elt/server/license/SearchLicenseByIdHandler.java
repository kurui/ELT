package com.chinarewards.gwt.elt.server.license;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.license.License;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.chinarewards.gwt.elt.client.license.request.SearchLicenseByIdRequest;
import com.chinarewards.gwt.elt.client.license.request.SearchLicenseByIdResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class SearchLicenseByIdHandler extends
		BaseActionHandler<SearchLicenseByIdRequest, SearchLicenseByIdResponse> {
	@InjectLogger
	Logger logger;
	LicenseService licenseService;

	@Inject
	public SearchLicenseByIdHandler(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

	@Override
	public SearchLicenseByIdResponse execute(SearchLicenseByIdRequest request,
			ExecutionContext context) throws DispatchException {
		logger.debug(" parameters:{}", request.getId());
		License license = licenseService.findLicenseById(request.getId());
		return new SearchLicenseByIdResponse(adapter(license));

	}

	private LicenseVo adapter(License license) {
		LicenseVo licenseVo = new LicenseVo();
		licenseVo.setId(license.getId());
		licenseVo.setName(license.getName());
		licenseVo.setSummary(license.getSummary());
		licenseVo.setDispatchcycle(license.getDispatchcycle());
		licenseVo.setExplains(license.getExplains());
		licenseVo.setNotes(license.getNotes());
		licenseVo.setType(license.getType());
		licenseVo.setBrand(license.getBrand());
		licenseVo.setSource(license.getSource());
		licenseVo.setBusiness(license.getBusiness());
		licenseVo.setAddress(license.getAddress());
		licenseVo.setTell(license.getTell());
		licenseVo.setServicetell(license.getServicetell());
		licenseVo.setPhoto(license.getPhoto());
		licenseVo.setStock(license.getStock());
		licenseVo.setIntegral(license.getIntegral());
		licenseVo.setPhoto(license.getPhoto());
		licenseVo.setIndate(license.getIndate());

		return licenseVo;
	}

	@Override
	public Class<SearchLicenseByIdRequest> getActionType() {
		return SearchLicenseByIdRequest.class;
	}

	@Override
	public void rollback(SearchLicenseByIdRequest arg0,
			SearchLicenseByIdResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
