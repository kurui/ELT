/**
 * 
 */
package com.chinarewards.gwt.elt.client.staff.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.staff.model.ImportStaffAjaxResponseVo;


public class ImportStaffAjaxResponse implements Result {
	
	private ImportStaffAjaxResponseVo response;

	/**
	 * @return the response
	 */
	public ImportStaffAjaxResponseVo getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(ImportStaffAjaxResponseVo response) {
		this.response = response;
	}

}
