/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewardItem.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;

/**
 * @author yanxin
 * @since 0.1.0 2010-12-20
 */
public class CreateRewardsItemRequest implements
		Action<CreateRewardsItemResponse> {

	private RewardsItemClient rewardsItem;

	public CreateRewardsItemRequest() {

	}

	public CreateRewardsItemRequest(RewardsItemClient rewardsItem) {
		this.rewardsItem = rewardsItem;
	}

	public RewardsItemClient getRewardsItem() {
		return rewardsItem;
	}

}
