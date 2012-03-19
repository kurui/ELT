package com.chinarewards.elt.service.license.impl;

import com.chinarewards.elt.domain.license.License;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.license.search.LicenseListVo;
import com.chinarewards.elt.model.license.search.LicenseStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.license.LicenseLogic;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.elt.service.user.UserLogic;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
@Transactional
public class LicenseServiceImpl implements LicenseService {
	private final LicenseLogic licenseLogic;
	private final UserLogic userLogic;

	@Inject
	public LicenseServiceImpl(LicenseLogic licenseLogic,UserLogic userLogic) {
		this.userLogic = userLogic;
		this.licenseLogic = licenseLogic;
		
	}
	@Override
	public License save(UserContext context, License license) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		License licenses = licenseLogic.save(caller, license);
		return licenses;
	}
	

	@Override
	public License findLicenseById(String id) {
		
		return licenseLogic.findLicenseById(id);
	}

	@Override
	public String deleteLicense(String id) {
		
		return licenseLogic.deleteLicense(id);
	}

	@Override
	public PageStore<LicenseListVo> licenseList(UserContext context, LicenseListVo licenseVo) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		return licenseLogic.licenseList(caller, licenseVo);
	}

	@Override
	public String updateStatus(String id,LicenseStatus status) {
		return licenseLogic.updateStatus(id,status);
	}

}
