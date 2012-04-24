/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.gift.model.ImportGiftAjaxResponseVo;


public class ImportGiftAjaxResponse implements Result {
	
	private ImportGiftAjaxResponseVo response;

	/**
	 * @return the response
	 */
	public ImportGiftAjaxResponseVo getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(ImportGiftAjaxResponseVo response) {
		this.response = response;
	}

}
