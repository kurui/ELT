package com.chinarewards.gwt.elt.client.budget.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * 
 * @author harry
 *@since 0.2.0
 */
public class AskBudgetAddRequest implements Action<AskBudgetAddResponse> {

	
	
	public  AskBudgetClientVo budgetVo;
	private UserSession userSession;
	public AskBudgetAddRequest() {

	}

	public AskBudgetAddRequest(AskBudgetClientVo budgetVo,UserSession userSession) {
		this.budgetVo = budgetVo;
		this.userSession = userSession;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public AskBudgetClientVo getBudgetVo() {
		return budgetVo;
	}

	public void setBudgetVo(AskBudgetClientVo budgetVo) {
		this.budgetVo = budgetVo;
	}

	
	
}
