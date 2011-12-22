package com.chinarewards.elt.service.reward;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.exception.ApproveRewardException;
import com.chinarewards.elt.model.reward.exception.DenyRewardException;
import com.chinarewards.elt.model.reward.exception.NominateRewardException;
import com.chinarewards.elt.model.reward.search.RewardQueryVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.reward.search.WinnerParam;
import com.chinarewards.elt.model.reward.vo.RewardVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.reward.rule.JudgeLogic;
import com.chinarewards.elt.service.reward.rule.WinnerLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.google.inject.Inject;

/**
 * The implementation of {@link RewardService}
 * 
 * @author yanxin
 * @since 1.0
 */
public class RewardServiceImpl implements RewardService {
	private final RewardLogic rewardLogic;
	private final EntityManager em;
	private final UserLogic userLogic;
	private final JudgeLogic judgeLogic;
	private final WinnerLogic winnerLogic;

	@Inject
	public RewardServiceImpl(RewardLogic rewardLogic, EntityManager em,
			UserLogic userLogic, JudgeLogic judgeLogic, WinnerLogic winnerLogic) {
		this.rewardLogic = rewardLogic;
		this.em = em;
		this.userLogic = userLogic;
		this.judgeLogic = judgeLogic;
		this.winnerLogic = winnerLogic;
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
	public String awardReward(SysUser caller, String rewardId,
			List<String> staffIds) {

		// 获取当前登录人.登录没实现,先默认当前第一个提名人
		if (em.getTransaction().isActive() != true) {
			em.getTransaction().begin();
		}
		caller = userLogic.getDefaultUser();
		List<Judge> judgeList = judgeLogic.findJudgesFromReward(rewardId);
		caller.setStaff(judgeList.get(0).getStaff());
		// ---------------------------------------

		String awardLogId = rewardLogic.awardReward(caller, rewardId, staffIds);

		em.getTransaction().commit();
		return awardLogId;
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

	@Override
	public PageStore<RewardVo> fetchRewards(UserContext context,
			RewardSearchVo criteria) {
		return rewardLogic.fetchRewards(context, criteria);
	}

	@Override
	public RewardQueryVo fetchWinRewardQueryVoById(String rewardId) {
		RewardQueryVo rewardVo = rewardLogic
				.fetchEntireRewardQueryVoById(rewardId);
		// 查询获奖人
		List<Winner> winners = winnerLogic.getWinnersOfReward(rewardId);
		List<WinnerParam> winnerList = new ArrayList<WinnerParam>();

		for (Winner wn : winners) {
			WinnerParam winnerparam = new WinnerParam();
			winnerparam.setId(wn.getId());
			winnerparam.setName(wn.getStaff().getName());
			winnerList.add(winnerparam);
		}
		rewardVo.setWinnerList(winnerList);
		return rewardVo;
	}

}
