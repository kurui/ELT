package com.chinarewards.elt.service.reward.rule;

import java.util.List;

import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.domain.user.SysUser;

/**
 * This class provides some useful methods to manipulate {@link Judge}
 * 
 * @author yanxin
 * @since 1.0
 */
public interface JudgeLogic {

	/**
	 * Bind judges to the specified RewardItem.
	 * 
	 * @param caller
	 * @param rewardItemId
	 * @param staffIds
	 */
	public void bindJudgesToRewardItem(SysUser caller, String rewardItemId,
			List<String> staffIds);

	/**
	 * Remove judges from the specified RewardItem.
	 * 
	 * @param rewardItemId
	 */
	public void removeJudgesFromRewardItem(String rewardItemId);

	/**
	 * Find list of Judge from the specified RewardItem.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	public List<Judge> findJudgesFromRewardItem(String rewardItemId);

	/**
	 * Clone list of Judge from the specified RewardItem to Reward.
	 * 
	 * @param caller
	 * @param fromRewardItemId
	 * @param toRewardId
	 */
	public void cloneJudgesFromRewardItemToReward(SysUser caller,
			String fromRewardItemId, String toRewardId);
}
