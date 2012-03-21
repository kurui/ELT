package com.chinarewards.elt.service.license;

import de.schlichtherle.license.LicenseContent;

public interface LicenseLogic {
	/**
	 * 获取客户端授权证书信息
	 */
	public LicenseContent queryLicenseContent();

}
