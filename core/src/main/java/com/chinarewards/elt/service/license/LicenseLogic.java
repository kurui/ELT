package com.chinarewards.elt.service.license;

import com.chinarewards.elt.model.vo.LicenseBo;


public interface LicenseLogic {
	/**
	 * 获取客户端授权证书信息
	 */
	public LicenseBo queryLicenseContent();

}
