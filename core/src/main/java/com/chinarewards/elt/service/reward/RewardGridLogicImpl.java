package com.chinarewards.elt.service.reward;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.dao.reward.CandidateDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.reward.WinnerDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.person.Candidate;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.domain.reward.rule.CandidateRule;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardGridSearchVo;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardGridVo;
import com.chinarewards.elt.model.reward.vo.RewardVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.reward.rule.CandidateLogic;
import com.chinarewards.elt.service.reward.rule.CandidateRuleLogic;
import com.chinarewards.elt.service.reward.rule.JudgeLogic;
import com.chinarewards.elt.service.reward.rule.NomineeLogic;
import com.chinarewards.elt.service.reward.rule.WinnerLogic;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * The implementation of {@link RewardLogic}
 * 
 * @author yanrui
 * @since 1.5
 */
@Transactional
public class RewardGridLogicImpl implements RewardGridLogic {

	private WinnerDao winnerDao;
	private RewardDao rewardDao;
	private RewardItemDao rewardItemDao;
	private CandidateDao candidateDao;
	private CandidateRuleLogic candidateRuleLogic;
	private CandidateLogic candidateLogic;
	private JudgeLogic judgeLogic;
	private NomineeLogic nomineeLogic;
	private WinnerLogic winnerLogic;

	@Inject
	public RewardGridLogicImpl(WinnerDao winnerDao, RewardDao rewardDao,
			RewardItemDao rewardItemDao,CandidateDao candidateDao, CandidateRuleLogic candidateRuleLogic,
			CandidateLogic candidateLogic, JudgeLogic judgeLogic,
			NomineeLogic nomineeLogic, WinnerLogic winnerLogic) {
		this.winnerDao = winnerDao;
		this.rewardDao = rewardDao;
		this.rewardItemDao = rewardItemDao;
		this.candidateDao=candidateDao;
		this.candidateRuleLogic = candidateRuleLogic;
		this.candidateLogic = candidateLogic;
		this.judgeLogic = judgeLogic;
		this.nomineeLogic = nomineeLogic;
		this.winnerLogic = winnerLogic;
	}

	@Override
	public PageStore<RewardGridVo> fetchRewards_STAFF(UserContext context,
			RewardGridSearchVo criteria) {
		PageStore<RewardGridVo> pageStore = new PageStore<RewardGridVo>();

		RewardSearchVo rewardSearchVo = new RewardSearchVo();
		String staffId = context.getCorporationId();
		rewardSearchVo.setWinnerStaffName(criteria.getStaffName());
		rewardSearchVo.setRewardItemId(criteria.getRewardItemId());
		rewardSearchVo.setRewardsTime(criteria.getRewardsDate());

		// List<Reward> rewardList = rewardDao.searchRewardsData_staff(staffId,
		// rewardSearchVo);

		List<Winner> winnerlist = winnerDao
				.queryCurrentStaffWinRewardData(rewardSearchVo);

		int resultCount = winnerlist.size();

		pageStore.setResultList(convertToGridVoListFromWinner(winnerlist));
		pageStore.setResultCount(resultCount);

		return pageStore;
	}

	@Override
	public PageStore<RewardGridVo> fetchRewards_ALL(UserContext context,
			RewardGridSearchVo criteria) {
		PageStore<RewardGridVo> pageStore = new PageStore<RewardGridVo>();

		RewardSearchVo rewardSearchVo = new RewardSearchVo();

		List<Winner> winnerlist = winnerDao
				.queryCurrentStaffWinRewardData(rewardSearchVo);

		int resultCount = winnerlist.size();

		pageStore.setResultList(convertToGridVoListFromWinner(winnerlist));
		pageStore.setResultCount(resultCount);


		return pageStore;
	}

	@Override
	public PageStore<RewardGridVo> fetchRewardsItem_STAFF(UserContext context,
			RewardGridSearchVo criteria) {
		PageStore<RewardGridVo> pageStore = new PageStore<RewardGridVo>();
				
		RewardItemSearchVo rewardItemSearchVo=new RewardItemSearchVo();
		List<Winner> list = winnerDao.queryCurrentStaffWinRewardItemData(rewardItemSearchVo);
				
		List<RewardGridVo> rewardGridVoList=new ArrayList<RewardGridVo>();
		for (int i = 0; i < list.size(); i++) {
			Winner winner=list.get(i);
			if(winner!=null){
				
				RewardGridVo rewardGridVo=new RewardGridVo();
				Reward reward=winner.getReward();
				rewardGridVo.setReward(reward);
				rewardGridVo.setRewardId(reward.getId());
				rewardGridVo.setRewardName(reward.getName());
				rewardGridVo.setRewardsDate(reward.getAwardDate());
				rewardGridVo.setAwardAmt(reward.getAwardAmt());
				rewardGridVo.setAwardName(reward.getCreatedBy().getStaff().getName());// 颁奖人
				
				RewardItem rewardItem=reward.getRewardItem();
				rewardGridVo.setRewardItem(rewardItem);
				rewardGridVo.setRewardItemId(rewardItem.getId());
				rewardGridVo.setRewardItemName(rewardItem.getName());		
				rewardGridVo.setRewardsItemCreateBy(rewardItem.getCreatedBy().getStaff().getName());//奖项创建人
				
				Staff staff=winner.getStaff();
				if(staff!=null){
					Candidate candiate=candidateDao.findCandidateByStaffRewardId(reward.getId(),staff.getId());
					if(candiate!=null){
						int nominateCount=candiate.getNominatecount();
						rewardGridVo.setNominateCount(nominateCount);
					}				
				}
				rewardGridVoList.add(rewardGridVo);
			}
			
		}
		
		pageStore.setResultList(rewardGridVoList);
		pageStore.setResultCount(rewardGridVoList.size());
		
		return pageStore;
	}

	@Override
	public PageStore<RewardGridVo> fetchRewardsItem_ALL(UserContext context,
			RewardGridSearchVo criteria) {
		PageStore<RewardGridVo> pageStore = new PageStore<RewardGridVo>();

		RewardItemSearchVo itemSearchVo = new RewardItemSearchVo();
		List<RewardItem> rewardItemList = rewardItemDao
				.fetchRewardsItems(itemSearchVo);

		int resultCount = rewardItemList.size();

		pageStore.setResultList(convertToGridVoListFromItem(rewardItemList));
		pageStore.setResultCount(resultCount);

		return pageStore;
	}

	private List<RewardGridVo> convertToGridVoListFromWinner(
			List<Winner> winnerList) {
		List<RewardGridVo> gridVoList = new ArrayList<RewardGridVo>();

		if (winnerList != null) {
			for (int i = 0; i < winnerList.size(); i++) {
				Winner winner = winnerList.get(i);
				Reward reward = winner.getReward();
				if (reward != null) {
					String rewardId = reward.getId();

					RewardGridVo rewardGridVo = new RewardGridVo();
					rewardGridVo.setRewardId(rewardId);
					rewardGridVo.setRewardName(reward.getName());
					rewardGridVo.setRewardsDate(winner.getCreatedAt());
					rewardGridVo.setAwardName(winner.getCreatedBy().getStaff().getName());
					
					rewardGridVo.setAwardAmt(reward.getAwardAmt());
					
					RewardItem rewardItem=reward.getRewardItem();
					rewardGridVo.setRewardItem(rewardItem);
					rewardGridVo.setRewardItemId(rewardItem.getId());
					rewardGridVo.setRewardItemName(rewardItem.getName());		
					rewardGridVo.setRewardsItemCreateBy(rewardItem.getCreatedBy().getStaff().getName());//奖项创建人

					// candidate rule
					CandidateRule candidateRule = candidateRuleLogic
							.findCandidateRuleFromReward(rewardId);
					// candidate list
					List<Candidate> candidates = candidateLogic
							.getCandidatesFromReward(rewardId);
					// Judge list
					List<Judge> judges = judgeLogic
							.findJudgesFromReward(rewardId);
					// nominee lot
					List<NomineeLot> nomineeLots = nomineeLogic
							.getNomineeLotsFromReward(rewardId);
					// winner
					List<Winner> winners = winnerLogic
							.getWinnersOfReward(rewardId);

					rewardGridVo.setReward(reward);
					rewardGridVo.setCandidateRule(candidateRule);
					rewardGridVo.setCandidateList(candidates);
					rewardGridVo.setJudgeList(judges);
					rewardGridVo.setNomineeLotList(nomineeLots);
					rewardGridVo.setWinnerList(winners);
					
					gridVoList.add(rewardGridVo);
				}

			}
		}

		return gridVoList;
	}

	private List<RewardGridVo> convertToGridVoListFromReward(
			List<Reward> rewardList) {
		List<RewardGridVo> gridVoList = new ArrayList<RewardGridVo>();

		if (rewardList != null) {
			for (int i = 0; i < rewardList.size(); i++) {
				Reward reward = rewardList.get(i);
				if (reward != null) {
					String rewardId = reward.getId();

					RewardGridVo rewardGridVo = new RewardGridVo();
					rewardGridVo.setRewardId(rewardId);
					rewardGridVo.setRewardName(reward.getName());

					// candidate rule
					CandidateRule candidateRule = candidateRuleLogic
							.findCandidateRuleFromReward(rewardId);
					// candidate list
					List<Candidate> candidates = candidateLogic
							.getCandidatesFromReward(rewardId);
					// Judge list
					List<Judge> judges = judgeLogic
							.findJudgesFromReward(rewardId);
					// nominee lot
					List<NomineeLot> nomineeLots = nomineeLogic
							.getNomineeLotsFromReward(rewardId);
					// winner
					List<Winner> winners = winnerLogic
							.getWinnersOfReward(rewardId);

					rewardGridVo.setReward(reward);
					rewardGridVo.setCandidateRule(candidateRule);
					rewardGridVo.setCandidateList(candidates);
					rewardGridVo.setJudgeList(judges);
					rewardGridVo.setNomineeLotList(nomineeLots);
					rewardGridVo.setWinnerList(winners);
//					 rewardGridVo.setRewardsDate(win.getCreatedAt());

					gridVoList.add(rewardGridVo);
				}

			}
		}

		return gridVoList;
	}

	private List<RewardGridVo> convertToGridVoListFromItem(
			List<RewardItem> itemList) {
		List<RewardGridVo> gridVoList = new ArrayList<RewardGridVo>();

		if (itemList != null) {
			for (int i = 0; i < itemList.size(); i++) {
				RewardItem rewardItem = itemList.get(i);
				if (rewardItem != null) {
					RewardGridVo rewardGridVo = new RewardGridVo();
					rewardGridVo.setRewardItemId(rewardItem.getId());
					rewardGridVo.setRewardItemName(rewardItem.getName());
					rewardGridVo.setAwardAmt(rewardItem.getAwardAmt());
//					 rewardGridVo.setRewardItemPhoto(rewardItem.getPhoto());
					rewardGridVo.setRewardsItemCreateBy(rewardItem.getCreatedBy().getStaff().getName());//奖项创建人
					gridVoList.add(rewardGridVo);

				}

			}
		}

		return gridVoList;
	}

	// private RewardItemVo convertFromRewardItemToVo(WinerRewardItemVo winVo,
	// boolean isEntire) {
	// RewardItem item=winVo.getReward().getRewardItem();
	// RewardItemVo itemVo = new RewardItemVo();
	//
	// itemVo.setNominateCount(winVo.getNominateCount());
	//
	// if (isEntire) {
	// String rewardItemId = item.getId();
	// // Get frequency info,判断是否周期
	// if (item.getAutoGenerate() == RequireAutoGenerate.requireCyclic) {
	// Frequency frequencie = frequencyLogic
	// .getFrequencyOfRewardItem(rewardItemId);
	// itemVo.setFrequency(frequencie);
	// }
	// // Get candidate list rule
	// CandidateRule candidateRule = candidateRuleLogic
	// .findCandidateRuleFromRewardItem(rewardItemId);
	// // Get judge list
	// List<Judge> judges = judgeLogic
	// .findJudgesFromRewardItem(rewardItemId);
	//
	// itemVo.setCandidateRule(candidateRule);
	// itemVo.setJudgeList(judges);
	//
	// itemVo.setAwardAmt(item.getAwardAmt());
	//
	// }
	// itemVo.setItem(item);
	//
	// return itemVo;
	// }

	private RewardVo convertFromRewardToVo(Winner win, Reward reward,
			boolean isEntire) {
		RewardVo rewardVo = new RewardVo();
		if (isEntire) {
			String rewardId = reward.getId();

		}
		rewardVo.setReward(reward);

		return rewardVo;
	}
}
