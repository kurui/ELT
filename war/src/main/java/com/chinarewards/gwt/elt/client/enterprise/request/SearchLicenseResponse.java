package com.chinarewards.gwt.elt.client.enterprise.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.enterprise.model.LicenseVo;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class SearchLicenseResponse implements Result {

	private LicenseVo licenseVo;


	public SearchLicenseResponse() {

	}

	public SearchLicenseResponse(LicenseVo licenseVo) {
		this.licenseVo=licenseVo;
	}

	public LicenseVo getLicenseVo() {
		return licenseVo;
	}

	public void setLicenseVo(LicenseVo licenseVo) {
		this.licenseVo = licenseVo;
	}




}
