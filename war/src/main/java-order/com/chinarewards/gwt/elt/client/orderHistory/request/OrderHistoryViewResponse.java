package com.chinarewards.gwt.elt.client.orderHistory.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;

/**
 * @author yanrui
 */
public class OrderHistoryViewResponse implements Result {

	private String result;



	  public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}

	public OrderHistoryViewResponse(){
		
	}

	public OrderHistoryViewResponse(String result) {
		this.result = result;
	}


}
