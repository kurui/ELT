/**
 * 
 */
package com.chinarewards.gwt.elt.client.orderSubmit.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author nicho
 * @since 2012年1月31日 18:52:22
 */
public class OrderSubmitRequest implements Action<OrderSubmitResponse> {


	private String giftId;
	private String staffId;



	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public OrderSubmitRequest() {
	}

	public OrderSubmitRequest(String giftId,String staffId) {
		this.giftId = giftId;
		this.staffId=staffId;

	}

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	



}
