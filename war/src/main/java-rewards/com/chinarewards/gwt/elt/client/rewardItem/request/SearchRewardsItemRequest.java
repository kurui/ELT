/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewardItem.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemCriteria;

/**
 * @author lw
 * @since 0.2.0 2011-12-26
 */
public class SearchRewardsItemRequest implements
		Action<SearchRewardsItemResponse> {

	private RewardsItemCriteria rewardsItem;

	public SearchRewardsItemRequest() {
	}

	public SearchRewardsItemRequest(RewardsItemCriteria rewardsItem) {
		this.rewardsItem = rewardsItem;
	}

	@Override
	public String toString() {
		return "SearchRewardsItemRequest [rewardsItem=" + rewardsItem + "]";
	}

	// ---- getter ----
	public RewardsItemCriteria getRewardsItem() {
		return rewardsItem;
	}
}
