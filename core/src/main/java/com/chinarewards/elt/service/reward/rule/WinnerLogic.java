package com.chinarewards.elt.service.reward.rule;

import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.domain.user.SysUser;

/**
 * Provides some methods to deal with {@link Winner}.
 * 
 * @author yanxin
 * @since 1.0
 */
public interface WinnerLogic {

	/**
	 * Generate {@link Winner}.
	 * 
	 * @param caller
	 * @param lotId
	 */
	public void approveWinnerFromEffectivePreWinnerLotOfReward(SysUser caller,
			String lotId, String reason);

	/**
	 * Process award by outer tx system.
	 * 
	 * @param rewardId
	 */
	public void processWinnerAward(String rewardId);

}
