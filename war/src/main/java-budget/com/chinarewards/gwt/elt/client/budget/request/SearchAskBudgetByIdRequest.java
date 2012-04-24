/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author lw
 */
public class SearchAskBudgetByIdRequest implements
		Action<SearchAskBudgetByIdResponse> {

	private String id;
   
	public SearchAskBudgetByIdRequest() {
	}

	public SearchAskBudgetByIdRequest(String id) {
		this.id = id;
		
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
