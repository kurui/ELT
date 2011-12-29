/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewards.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;

/**
 * @author Cream
 * @since 0.2.0 2010-12-27
 */
public class SearchRewardsRequest implements Action<SearchRewardsResponse> {

	private RewardsCriteria rewards;
	private String corporationId;
	private UserRoleVo[] userRoles;


	public SearchRewardsRequest() {
	}

	public SearchRewardsRequest(RewardsCriteria criteria,String corporationId,UserRoleVo[] userRoles) {
		this.rewards = criteria;
		this.corporationId=corporationId;
		this.userRoles=userRoles;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public UserRoleVo[] getUserRoles() {
		return userRoles;
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
