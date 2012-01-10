package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class AddGiftResponse implements Result {

	String nomineeLotId;

	public String getNomineeLotId() {
		return nomineeLotId;
	}

	public void setNomineeLotId(String nomineeLotId) {
		this.nomineeLotId = nomineeLotId;
	}

	public AddGiftResponse() {

	}




}
