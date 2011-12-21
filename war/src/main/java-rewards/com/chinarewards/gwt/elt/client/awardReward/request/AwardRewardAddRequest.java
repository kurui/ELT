/**
 * 
 */
package com.chinarewards.gwt.elt.client.awardReward.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

/**
 * An action which perform request to search user.
 * 
 * @author nicho
 * @since 2011年12月12日 
 */
public class AwardRewardAddRequest implements Action<AwardRewardAddResponse> {

	List<String> staffIds;

	String rewardId;
	
	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	/**
	 * For serialization
	 */
	public AwardRewardAddRequest() {
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

	public AwardRewardAddRequest(List<String> staffIds,String rewardId) {
		this.staffIds=staffIds;
	
		this.rewardId=rewardId;
	}

}
