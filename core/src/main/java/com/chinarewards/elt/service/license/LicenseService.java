package com.chinarewards.elt.service.license;

import de.schlichtherle.license.LicenseContent;

/**
 * Service of corporation.
 * 
 * @author yanrui
 * @since 1.5
 */
public interface LicenseService {

	/**
	 * 获取客户端授权证书信息
	 */
	public LicenseContent queryLicenseContent();

}
