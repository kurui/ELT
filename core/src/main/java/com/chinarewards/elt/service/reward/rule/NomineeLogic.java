package com.chinarewards.elt.service.reward.rule;

import java.util.List;

import com.chinarewards.elt.domain.reward.person.Nominee;
import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.reward.exception.JudgeException;

/**
 * Provides some methods to deal with {@link Nominee} and {@link NomineeLot}.
 * 
 * @author yanxin
 * @since 1.0
 */
public interface NomineeLogic {

	/**
	 * Generate a new {@link NomineeLot} to the specified Reward.
	 * 
	 * @param caller
	 * @param rewardId
	 * @param staffIds
	 * @return
	 */
	public NomineeLot addNomineeLotToReward(SysUser caller, String rewardId,
			List<String> staffIds) throws JudgeException;

	/**
	 * Get list of nominee lot from the specified reward.
	 * 
	 * @param rewardId
	 * @return
	 */
	public List<NomineeLot> getNomineeLotsFromReward(String rewardId);

	/**
	 * 获取提名的次数
	 * 
	 * @param rewardId
	 * @param staffIds
	 * @return
	 */
	public int getNomineeCount(String rewardId, String staffId);

	/**
	 * 提名人是否已经提名
	 * 
	 * @param rewardId
	 * @param staffIds
	 * @return
	 */
	public boolean isNomineeByJudge(String rewardId, String judgeId);
}
