/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.gift.model.ImportGiftAjaxRequestVo;
import com.chinarewards.gwt.elt.client.support.UserSession;


public class ImportGiftAjaxRequest implements
		Action<ImportGiftAjaxResponse> {

	private ImportGiftAjaxRequestVo request;

	private UserSession userSession;

	public ImportGiftAjaxRequest() {
	}

	public ImportGiftAjaxRequest(ImportGiftAjaxRequestVo request,
			UserSession userSession) {
		super();
		this.request = request;
		this.userSession = userSession;
	}

	/**
	 * @return the request
	 */
	public ImportGiftAjaxRequestVo getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(ImportGiftAjaxRequestVo request) {
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
