package com.chinarewards.elt.service.reward;

import java.util.List;

import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.reward.exception.ApproveRewardException;
import com.chinarewards.elt.model.reward.exception.DenyRewardException;
import com.chinarewards.elt.model.reward.exception.NominateRewardException;
import com.chinarewards.elt.model.reward.search.RewardQueryVo;
import com.google.inject.Inject;

/**
 * The implementation of {@link RewardService}
 * 
 * @author yanxin
 * @since 1.0
 */
public class RewardServiceImpl implements RewardService {
	private final RewardLogic rewardLogic;
	
	@Inject
	public RewardServiceImpl(RewardLogic rewardLogic)
	{
		this.rewardLogic=rewardLogic;
	}
	@Override
	public Reward awardFromRewardItem(SysUser caller, String rewardItemId) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NomineeLot nominateReward(SysUser caller, String rewardId,
			List<String> staffIds) throws NominateRewardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void awardReward(SysUser caller, String rewardId,
			List<String> staffIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reward approveReward(SysUser caller, String rewardId, String reason)
			throws ApproveRewardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reward denyReward(SysUser caller, String rewardId, String reason)
			throws DenyRewardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RewardQueryVo fetchEntireRewardQueryVoById(String rewardId) {
		return rewardLogic.fetchEntireRewardQueryVoById(rewardId);
	}

}
