package com.chinarewards.gwt.elt.client.rewardItem.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemCompanyOtherCriteria;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * @author yanrui
 */
public class SearchCompanyOtherRewardsItemRequest implements
		Action<SearchCompanyOtherRewardsItemResponse> {

	private RewardsItemCompanyOtherCriteria rewardsItem;
	private UserSession userSession;

	public SearchCompanyOtherRewardsItemRequest() {
	}

	public SearchCompanyOtherRewardsItemRequest(RewardsItemCompanyOtherCriteria rewardsItem,
			UserSession userSession) {
		this.rewardsItem = rewardsItem;
		this.userSession = userSession;
	}

	@Override
	public String toString() {
		return "SearchRewardsItemRequest [rewardsItem=" + rewardsItem + "]";
	}

	// ---- getter ----
	public RewardsItemCompanyOtherCriteria getRewardsItemCompanyOtherCriteria() {
		return rewardsItem;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
