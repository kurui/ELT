package com.chinarewards.elt.service.license;

import com.chinarewards.elt.model.vo.LicenseBo;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class LicenseServiceImpl implements LicenseService {
	private final LicenseLogic licenseLogic;

	@Inject
	public LicenseServiceImpl(LicenseLogic licenseLogic) {
		this.licenseLogic = licenseLogic;

	}

	@Override
	public LicenseBo queryLicenseContent() {
		return licenseLogic.queryLicenseContent();
	}

}
