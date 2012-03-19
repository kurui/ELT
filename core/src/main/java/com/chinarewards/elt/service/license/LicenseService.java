package com.chinarewards.elt.service.license;

import com.chinarewards.elt.domain.license.License;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.license.search.LicenseListVo;
import com.chinarewards.elt.model.license.search.LicenseStatus;
import com.chinarewards.elt.model.user.UserContext;

/**
 * Service of corporation.
 * 
 * @author yanrui
 * @since 1.5
 */
public interface LicenseService {

	/**
	 * 保存
	 * @param context
	 * @param license
	 * @return
	 */
	public License save(UserContext context, License license);

	/**
	 * 查找根据ID
	 * @param id
	 * @return
	 */
	public License findLicenseById(String id);
	/**
	 * 删除授权证书根据ID
	 * @param id
	 * @return
	 */
	public String deleteLicense(String id);
	/**
	 * 授权证书列表
	 * @param context
	 * @param license
	 * @return
	 */
	public PageStore<LicenseListVo> licenseList(UserContext context,LicenseListVo licenseListVo);

	/**
	 * 上下架
	 * @param id
	 * @return
	 */
	public String updateStatus(String id,LicenseStatus status);
}
