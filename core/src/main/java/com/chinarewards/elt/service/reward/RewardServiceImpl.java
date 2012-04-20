package com.chinarewards.elt.service.reward;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.domain.reward.person.PreWinner;
import com.chinarewards.elt.domain.reward.person.PreWinnerLot;
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
import com.chinarewards.elt.service.reward.rule.PreWinnerLogic;
import com.chinarewards.elt.service.reward.rule.WinnerLogic;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * The implementation of {@link RewardService}
 * 
 * @author yanxin
 * @since 1.0
 */
@Transactional
public class RewardServiceImpl implements RewardService {
	private final RewardLogic rewardLogic;
	private final UserLogic userLogic;
	private final WinnerLogic winnerLogic;
	private final StaffLogic staffLogic;
	private final JudgeLogic judgeLogic;
	private final PreWinnerLogic preWinnerLogic;

	@Inject
	public RewardServiceImpl(RewardLogic rewardLogic, UserLogic userLogic,
			JudgeLogic judgeLogic, WinnerLogic winnerLogic,
			StaffLogic staffLogic,PreWinnerLogic preWinnerLogic) {
		this.rewardLogic = rewardLogic;
		this.userLogic = userLogic;
		this.winnerLogic = winnerLogic;
		this.staffLogic = staffLogic;
		this.judgeLogic=judgeLogic;
		this.preWinnerLogic=preWinnerLogic;

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
	public String awardReward(String nowUserId, String rewardId,
			List<String> staffIds) {
		SysUser caller = userLogic.findUserById(nowUserId);

		String awardLogId = rewardLogic.awardReward(caller, rewardId, staffIds);

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
		RewardQueryVo rewardVo=rewardLogic.fetchEntireRewardQueryVoById(rewardId);
		// 查询获奖人
		List<PreWinnerLot> lot=preWinnerLogic.getPreWinnerLotsFromReward(rewardId);
		if(lot!=null && lot.size()>0)
		{
			List<PreWinner> winners = preWinnerLogic.getPreWinnerListFromLot(lot.get(0).getId());
			if(winners!=null && winners.size()>0)
			{
				List<WinnerParam> winnerList = new ArrayList<WinnerParam>();

				for (PreWinner wn : winners) {
					WinnerParam winnerparam = new WinnerParam();
					winnerparam.setId(wn.getId());
					winnerparam.setName(wn.getStaff().getName());
					winnerList.add(winnerparam);
				}
				rewardVo.setWinnerList(winnerList);
			}
		}

		return rewardVo;
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

	@Override
	public List<String> getIsDeleteStaff(List<String> staffIds) {
		List<Staff> staffList = staffLogic.findStaffsByStaffIds(staffIds);
		List<String> deleteStaffNames = new ArrayList<String>();
		for (Staff sf : staffList) {
			if (sf.isDeleted() == 1) {
				deleteStaffNames.add(sf.getName());
			}
		}
		return deleteStaffNames;
	}

	@Override
	public String deleteReward(String rewardId,UserContext context) {
		return rewardLogic.deleteReward(rewardId,context);
	}

	@Override
	public int getNominatorByStaffId(String staffId) {
		return rewardLogic.getNominatorByStaffId(staffId);
	}

	@Override
	public int getRewardsByStaffId(String staffId) {
		// TODO Auto-generated method stub
		return rewardLogic.getRewardsByStaffId(staffId);
	}
	@Override
	public List<RewardVo> getRewardsByHrBox(UserContext context,RewardSearchVo criteria){
		return rewardLogic.getRewardsByHrBox(context, criteria);
	}

	@Override
	public void toMessageForReward() {
		rewardLogic.toMessageForReward();
		
	}

	@Override
	public void getNominatorToMessage() {
		judgeLogic.getNominatorToMessage();
		
	}

	@Override
	public String determineWinner(String nowUserId, String rewardId,
			List<String> staffIds) {
		return rewardLogic.determineWinner(nowUserId, rewardId, staffIds);
	}

	@Override
	public String awardRewardWinner(String nowUserId, String rewardId) {
		return rewardLogic.awardRewardWinner(nowUserId, rewardId);
	}
}
