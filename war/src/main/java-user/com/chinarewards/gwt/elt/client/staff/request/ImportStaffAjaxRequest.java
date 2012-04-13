/**
 * 
 */
package com.chinarewards.gwt.elt.client.staff.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.staff.model.ImportStaffAjaxRequestVo;
import com.chinarewards.gwt.elt.client.support.UserSession;


public class ImportStaffAjaxRequest implements
		Action<ImportStaffAjaxResponse> {

	private ImportStaffAjaxRequestVo request;

	private UserSession userSession;

	public ImportStaffAjaxRequest() {
	}

	public ImportStaffAjaxRequest(ImportStaffAjaxRequestVo request,
			UserSession userSession) {
		super();
		this.request = request;
		this.userSession = userSession;
	}

	/**
	 * @return the request
	 */
	public ImportStaffAjaxRequestVo getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(ImportStaffAjaxRequestVo request) {
		this.request = request;
	}

	/**
	 * @return the userSession
	 */
	public UserSession getUserSession() {
		return userSession;
	}

	/**
	 * @param userSession the userSession to set
	 */
	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}


}
