/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.model.DepBudgetVo;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * @author lw
 * @since 2012年1月20日 19:00:40
 */
public class SearchAskBudgetRequest implements Action<SearchAskBudgetResponse> {

	public AskBudgetClientVo budgetVo;
	private UserSession userSession;
	

	public SearchAskBudgetRequest() {
	}

	public SearchAskBudgetRequest(AskBudgetClientVo budgetVo,UserSession userSession) {
		this.budgetVo = budgetVo;
		this.userSession = userSession;
		
	}


	

	public AskBudgetClientVo getBudgetVo() {
		return budgetVo;
	}

	public void setBudgetVo(AskBudgetClientVo budgetVo) {
		this.budgetVo = budgetVo;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	

}
