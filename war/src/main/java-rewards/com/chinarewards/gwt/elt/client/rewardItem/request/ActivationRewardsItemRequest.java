/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewardItem.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author nicho
 * @since 2011年12月26日 10:37:22
 */
public class ActivationRewardsItemRequest implements
		Action<ActivationRewardsItemResponse> {

	private String rewardsItemId;
	private String nowUserId;
	

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	public String getRewardsItemId() {
		return rewardsItemId;
	}

	public void setRewardsItemId(String rewardsItemId) {
		this.rewardsItemId = rewardsItemId;
	}

	public ActivationRewardsItemRequest(String rewardsItemId,String nowUserId) {
		this.rewardsItemId=rewardsItemId;
		this.nowUserId=nowUserId;

	}
	public ActivationRewardsItemRequest() {


	}
}
