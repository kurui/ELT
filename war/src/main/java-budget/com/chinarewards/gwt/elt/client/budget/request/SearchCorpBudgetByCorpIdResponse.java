/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;

/**
 * @author yanrui
 * @since
 */
public class SearchCorpBudgetByCorpIdResponse implements Result {

	private CorpBudgetVo corpBudgetVo;

	public SearchCorpBudgetByCorpIdResponse() {

	}

	public SearchCorpBudgetByCorpIdResponse(CorpBudgetVo corpBudgetVo) {
		this.corpBudgetVo = corpBudgetVo;
	}

	public CorpBudgetVo getCorpBudget() {
		return corpBudgetVo;
	}

	public void setCorpBudget(CorpBudgetVo corpBudgetVo) {
		this.corpBudgetVo = corpBudgetVo;
	}

}
