/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewardItem.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author nicho
 * @since 2011年12月26日 10:37:22
 */
public class DeleteRewardsItemRequest implements
		Action<DeleteRewardsItemResponse> {

	private String rewardsItemId;
	private String nowUserId;
	private boolean isItemStore ;

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

	public DeleteRewardsItemRequest(String rewardsItemId,String nowUserId,boolean isItemStore) {
		this.rewardsItemId=rewardsItemId;
		this.nowUserId=nowUserId;
		this.isItemStore = isItemStore;

	}
	public boolean isItemStore() {
		return isItemStore;
	}

	public void setItemStore(boolean isItemStore) {
		this.isItemStore = isItemStore;
	}

	public DeleteRewardsItemRequest() {


	}
}
