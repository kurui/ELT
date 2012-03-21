package com.chinarewards.elt.service.license;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import de.schlichtherle.license.LicenseContent;

@Transactional
public class LicenseServiceImpl implements LicenseService {
	private final LicenseLogic licenseLogic;

	@Inject
	public LicenseServiceImpl(LicenseLogic licenseLogic) {
		this.licenseLogic = licenseLogic;

	}

	@Override
	public LicenseContent queryLicenseContent() {
		return licenseLogic.queryLicenseContent();
	}

}
