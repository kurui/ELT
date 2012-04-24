/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewards.request;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author nicho
 * @since 2012年1月4日 15:36:46
 */
public class UpdateRewardsAwardUserRequest implements Action<UpdateRewardsAwardUserResponse> {

	private String rewardId;
	private String nowUserId;
	private String updateStaffId;

	public String getUpdateStaffId() {
		return updateStaffId;
	}

	public void setUpdateStaffId(String updateStaffId) {
		this.updateStaffId = updateStaffId;
	}

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	public UpdateRewardsAwardUserRequest() {
	}

	public UpdateRewardsAwardUserRequest(String rewardId,String nowUserId,String updateStaffId) {
		this.rewardId = rewardId;
		this.nowUserId=nowUserId;
		this.updateStaffId=updateStaffId;

	}

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

}
