/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewards.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author nicho
 * @since 2012年1月4日 15:37:56
 */
public class UpdateRewardsAwardUserResponse implements Result {

	private String rewardId;
	public UpdateRewardsAwardUserResponse() {

	}
	public UpdateRewardsAwardUserResponse(String rewardId) {
		this.rewardId = rewardId;
	}

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

}
