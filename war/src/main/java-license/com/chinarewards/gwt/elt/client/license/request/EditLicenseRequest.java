/**
 * 
 */
package com.chinarewards.gwt.elt.client.license.request;

import java.util.List;

import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.chinarewards.gwt.elt.client.license.request.EditLicenseResponse;
import com.chinarewards.gwt.elt.client.support.UserSession;

import net.customware.gwt.dispatch.shared.Action;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 */
public class EditLicenseRequest implements Action<EditLicenseResponse> {

	String licenseId;
	String nowUserId;
	private LicenseVo licenseVo;
	private UserSession userSession;

	List<String> staffIds;

	public EditLicenseRequest(LicenseVo licenseVo, UserSession userSession) {
		this.licenseVo = licenseVo;
		this.userSession = userSession;
	}

	/**
	 * For serialization
	 */
	public EditLicenseRequest() {
	}


	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	public LicenseVo getLicenseVo() {
		return licenseVo;
	}

	public void setLicenseVo(LicenseVo licenseVo) {
		this.licenseVo = licenseVo;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

}
