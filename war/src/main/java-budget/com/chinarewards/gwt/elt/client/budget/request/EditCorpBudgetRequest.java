/**
 * 
 */
package com.chinarewards.gwt.elt.client.budget.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 */
public class EditCorpBudgetRequest implements Action<EditCorpBudgetResponse> {

	String giftId;
	String nowUserId;
	private CorpBudgetVo giftVo;
	private UserSession userSession;

	List<String> staffIds;

	public EditCorpBudgetRequest(CorpBudgetVo giftVo, UserSession userSession) {
		this.giftVo = giftVo;
		this.userSession = userSession;
	}

	/**
	 * For serialization
	 */
	public EditCorpBudgetRequest() {
	}


	public String getCorpBudgetId() {
		return giftId;
	}

	public void setCorpBudgetId(String giftId) {
		this.giftId = giftId;
	}

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	public CorpBudgetVo getCorpBudgetVo() {
		return giftVo;
	}

	public void setCorpBudgetVo(CorpBudgetVo giftVo) {
		this.giftVo = giftVo;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

}
