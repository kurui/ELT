package com.chinarewards.gwt.elt.client.enterprise.request;

import net.customware.gwt.dispatch.shared.Result;
import de.schlichtherle.license.LicenseContent;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class SearchLicenseResponse implements Result {

	private LicenseContent licenseContent;


	public SearchLicenseResponse() {

	}

	public SearchLicenseResponse(LicenseContent licenseContent) {
		this.licenseContent=licenseContent;
	}

	public LicenseContent getLicenseContent() {
		return licenseContent;
	}

	public void setLicenseContent(LicenseContent licenseContent) {
		this.licenseContent = licenseContent;
	}




}
