/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewards.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;

/**
 * @author Cream
 * @since 0.2.0 2010-12-27
 */
public class SearchRewardsRequest implements Action<SearchRewardsResponse> {

	private RewardsCriteria rewards;

	public SearchRewardsRequest() {
	}

	public SearchRewardsRequest(RewardsCriteria criteria) {
		this.rewards = criteria;
	}

	@Override
	public String toString() {
		return "SearchRewardsRequest [rewards=" + rewards + "]";
	}

	// ---- getter ----
	/**
	 * @return the rewards
	 */
	public RewardsCriteria getRewards() {
		return rewards;
	}

}
