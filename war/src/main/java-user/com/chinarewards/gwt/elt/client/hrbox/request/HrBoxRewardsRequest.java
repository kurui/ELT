/**
 * 
 */
package com.chinarewards.gwt.elt.client.hrbox.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * @author nicho
 * @since 
 */
public class HrBoxRewardsRequest implements Action<HrBoxRewardsResponse> {

	private RewardsCriteria rewards;
	private UserSession session;
   
	
	public void setRewards(RewardsCriteria rewards) {
		this.rewards = rewards;
	}

	public HrBoxRewardsRequest() {
	}

	public HrBoxRewardsRequest(RewardsCriteria criteria,UserSession session) {
		this.rewards = criteria;
		this.session = session;
		
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	public RewardsCriteria getRewards() {
		return rewards;
	}

	@Override
	public String toString() {
		return "SearchRewardsRequest [rewards=" + rewards + "]";
	}

}
