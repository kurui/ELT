package com.chinarewards.elt.service.reward.rule;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.chinarewards.elt.domain.reward.rule.CandidateRule;
import com.chinarewards.elt.domain.reward.rule.DirectCandidateData;
import com.chinarewards.elt.domain.reward.rule.DirectCandidateRule;
import com.chinarewards.elt.domain.reward.rule.DobRule;
import com.chinarewards.elt.domain.staff.Staff;
import com.chinarewards.elt.domain.user.SysUser;

/**
 * This class provides some useful methods to manipulate {@link CandidateRule}
 * 
 * @author yanxin
 * @since 1.0
 */
public interface CandidateRuleLogic {

	/**
	 * Bind the given {@link DirectCandidateRule} with
	 * {@link DirectCandidateData} to the specified RewardItem.
	 * 
	 * @param rewardItemId
	 * @param candidateList
	 * @return
	 */
	public DirectCandidateRule bindDirectCandidateRuleToRewardItem(
			SysUser caller, String rewardItemId, List<String> candidateList);

	/**
	 * Bind a DobRule to the specified RewardItem.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	public DobRule bindDobRuleToRewardItem(SysUser caller, String rewardItemId);

	/**
	 * Remove {@link CandidateRule} from the specified RewardItem.
	 * 
	 * @param rewardItemId
	 */
	public void removeCandidateRuleFromRewardItem(String rewardItemId);

	/**
	 * Get {@link CandidateRule} list from the specified RewardItem.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	public CandidateRule findCandidateRuleFromRewardItem(String rewardItemId);

	/**
	 * Clone {@link CandidateRule} list from the specified RewardItem to Reward.
	 * 
	 * @param caller
	 * @param fromRewardItemId
	 * @param toRewardId
	 * @param currTime
	 */
	public CandidateRule cloneCandidateRuleFromRewardItemToReward(
			SysUser caller, String fromRewardItemId, String toRewardId,
			Date currTime);

	/**
	 * Get qualified staffs from the specified RewardItem.
	 * 
	 * @param candidateRuleId
	 * @return
	 */
	public Set<Staff> getQualifiedStaffsFromCandidateRuleId(
			String candidateRuleId);
}
