package com.chinarewards.elt.service.reward;

import java.util.List;

import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.domain.reward.person.PreWinner;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.reward.exception.ApproveRewardException;
import com.chinarewards.elt.model.reward.exception.DenyRewardException;
import com.chinarewards.elt.model.reward.exception.NominateRewardException;
import com.chinarewards.elt.model.reward.search.RewardQueryVo;

/**
 * The interface is to provide Reward services. Some useful methods should be
 * listed here.
 * 
 * @author yanxin
 * @since 1.0
 */
public interface RewardService {

	/**
	 * Generate Reward from the specified RewardItem.
	 * 
	 * @param caller
	 * @param rewardItemId
	 * @return
	 */
	public Reward awardFromRewardItem(SysUser caller, String rewardItemId);

	/**
	 * The caller nominate someone to be nominees. The result is result a record
	 * of {@link NomineeLot}.
	 * 
	 * @param caller
	 * @param rewardId
	 * @param staffIds
	 * @return
	 * @throws NominateRewardException
	 */
	public NomineeLot nominateReward(SysUser caller, String rewardId,
			List<String> staffIds) throws NominateRewardException;

	public void awardReward(SysUser caller, String rewardId,
			List<String> staffIds);

	/**
	 * Approve a Reward means it would award really. The result is generate
	 * {@link Winner} from {@link PreWinner}.
	 * 
	 * @param caller
	 * @param rewardId
	 * @return
	 * @throws ApproveRewardException
	 */
	public Reward approveReward(SysUser caller, String rewardId, String reason)
			throws ApproveRewardException;

	/**
	 * Deny a Reward means it should prepare {@link PreWinner} again.
	 * 
	 * @param caller
	 * @param rewardId
	 * @return
	 * @throws DenyRewardException
	 */
	public Reward denyReward(SysUser caller, String rewardId, String reason)
			throws DenyRewardException;

	/**
	 * Fetch the entire information about the specified Reward. It contains all
	 * the external informations.
	 * 
	 * @param rewardId
	 * @return
	 */
	public RewardQueryVo fetchEntireRewardQueryVoById(String rewardId);

}
