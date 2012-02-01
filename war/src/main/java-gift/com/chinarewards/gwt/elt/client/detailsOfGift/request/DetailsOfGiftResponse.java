/**
 * 
 */
package com.chinarewards.gwt.elt.client.detailsOfGift.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author nicho
 * @since 2012年2月1日 15:07:52
 */
public class DetailsOfGiftResponse implements Result {

	private String result;



	  public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}

	public DetailsOfGiftResponse(){
		
	}

	public DetailsOfGiftResponse(String result) {
		this.result = result;
	}




}
