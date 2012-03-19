package com.chinarewards.gwt.elt.adapter.license;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.model.license.search.LicenseListVo;
import com.chinarewards.gwt.elt.client.license.model.LicenseClient;
import com.chinarewards.gwt.elt.client.license.model.LicenseCriteria.LicenseStatus;

/**
 * This utility class use to adapter EJB entity to WAR domain.
 * 
 * @author yanrui
 */
public class LicenseAdapter {

	public static LicenseClient adapter(LicenseListVo license) {
		if (null == license) {
			return null;
		}

		LicenseClient result = new LicenseClient();

		result.setId(license.getId());
		result.setName(license.getName());
		result.setSource(license.getSource());
		result.setInventory(license.getStock() + "");
		result.setIntegral(license.getIntegral());
		result.setIndate(license.getIndate());
		result.setPhoto(license.getPhoto());

		if (license.getStatus() != null) {
			result.setStatus(LicenseStatus.valueOf(license.getStatus().toString()));
		}
		return result;
	}

	public static List<LicenseClient> adapter(List<LicenseListVo> licenseList) {
		if (null == licenseList) {
			return null;
		}

		List<LicenseClient> resultList = new ArrayList<LicenseClient>();
		for (LicenseListVo license : licenseList) {
			resultList.add(adapter(license));
		}
		return resultList;
	}
	
}
