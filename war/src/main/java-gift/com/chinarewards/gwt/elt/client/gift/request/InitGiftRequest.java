/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 */
public class InitGiftRequest implements Action<InitGiftResponse> {

	private String awardsId;//奖项ID

	public String getAwardsId() {
		return awardsId;
	}

	public void setAwardsId(String awardsId) {
		this.awardsId = awardsId;
	}

	
	/**
	 * For serialization
	 */
	public InitGiftRequest() {
	}

	public InitGiftRequest(String awardsId) {
		this.awardsId=awardsId;
	}
}
