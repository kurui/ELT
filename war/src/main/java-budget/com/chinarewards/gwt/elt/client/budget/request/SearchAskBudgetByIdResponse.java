/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;

/**
 * @author lw
 * @since
 */
public class SearchAskBudgetByIdResponse implements Result {

	private AskBudgetClientVo budgetVoClient;

	public SearchAskBudgetByIdResponse() {

	}

	public SearchAskBudgetByIdResponse(AskBudgetClientVo budgetVoClient) {
		this.budgetVoClient = budgetVoClient;
	}

	public AskBudgetClientVo getAskBudgetVoClient() {
		return budgetVoClient;
	}

	public void setbudgetVoClient(AskBudgetClientVo budgetVoClient) {
		this.budgetVoClient = budgetVoClient;
	}

	
}
