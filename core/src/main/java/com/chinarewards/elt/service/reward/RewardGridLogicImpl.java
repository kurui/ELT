package com.chinarewards.elt.service.reward;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.reward.WinnerDao;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardGridSearchVo;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardGridVo;
import com.chinarewards.elt.model.user.UserContext;
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

	@Inject
	public RewardGridLogicImpl(WinnerDao winnerDao, RewardDao rewardDao,
			RewardItemDao rewardItemDao) {
		this.winnerDao = winnerDao;
		this.rewardDao = rewardDao;
		this.rewardItemDao = rewardItemDao;
	}

	@Override
	public PageStore<RewardGridVo> fetchRewards_STAFF(UserContext context,
			RewardGridSearchVo criteria) {
		PageStore<RewardGridVo> pageStore = new PageStore<RewardGridVo>();
		
//		winnerDao.
		
		return pageStore;
	}

	@Override
	public PageStore<RewardGridVo> fetchRewards_ALL(UserContext context,
			RewardGridSearchVo criteria) {
		PageStore<RewardGridVo> pageStore = new PageStore<RewardGridVo>();

		RewardSearchVo rewardSearchVo = new RewardSearchVo();
		String corporationId=context.getCorporationId();
		List<Reward> rewardList = rewardDao.searchRewardsData_hrManager(corporationId, rewardSearchVo);

		int resultCount = rewardList.size();

		pageStore.setResultList(convertToGridVoListFromReward(rewardList));
		pageStore.setResultCount(resultCount);
		
		return pageStore;
	}

	@Override
	public PageStore<RewardGridVo> fetchRewardsItem_STAFF(UserContext context,
			RewardGridSearchVo criteria) {
		PageStore<RewardGridVo> pageStore = new PageStore<RewardGridVo>();
		// pageStore = winnerDao.searchRewardItem_staff(criteria);
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

	private List<RewardGridVo> convertToGridVoListFromReward(
			List<Reward> rewardList) {
		List<RewardGridVo> gridVoList = new ArrayList<RewardGridVo>();
		
		if (rewardList!=null) {
			for (int i = 0; i < rewardList.size(); i++) {
				Reward reward=rewardList.get(i);
				if (reward!=null) {
					RewardGridVo rewardGridVo=new RewardGridVo();	
					rewardGridVo.setRewardId(reward.getId());
					rewardGridVo.setRewardName(reward.getName());
					gridVoList.add(rewardGridVo);					
				} 
				
			}
		} 	
		
		return gridVoList;
	}

	private List<RewardGridVo> convertToGridVoListFromItem(
			List<RewardItem> itemList) {
		List<RewardGridVo> gridVoList = new ArrayList<RewardGridVo>();
		
		if (itemList!=null) {
			for (int i = 0; i < itemList.size(); i++) {
				RewardItem rewardItem=itemList.get(i);
				if (rewardItem!=null) {
					RewardGridVo rewardGridVo=new RewardGridVo();	
					rewardGridVo.setRewardItemId(rewardItem.getId());
					rewardGridVo.setRewardItemName(rewardItem.getName());
					rewardGridVo.setAwardAmt(rewardItem.getAwardAmt());
//					rewardGridVo.setRewardItemPhoto(rewardItem.get)
					gridVoList.add(rewardGridVo);
					
				} 
				
			}
		} 	
		
		return gridVoList;
	}
	
	
//	private RewardItemVo convertFromRewardItemToVo(WinerRewardItemVo winVo,
//			boolean isEntire) {
//		RewardItem item=winVo.getReward().getRewardItem();
//		RewardItemVo itemVo = new RewardItemVo();
//		
//		itemVo.setNominateCount(winVo.getNominateCount());
//		
//		if (isEntire) {
//			String rewardItemId = item.getId();
//			// Get frequency info,判断是否周期
//			if (item.getAutoGenerate() == RequireAutoGenerate.requireCyclic) {
//				Frequency frequencie = frequencyLogic
//						.getFrequencyOfRewardItem(rewardItemId);
//				itemVo.setFrequency(frequencie);
//			}
//			// Get candidate list rule
//			CandidateRule candidateRule = candidateRuleLogic
//					.findCandidateRuleFromRewardItem(rewardItemId);
//			// Get judge list
//			List<Judge> judges = judgeLogic
//					.findJudgesFromRewardItem(rewardItemId);
//
//			itemVo.setCandidateRule(candidateRule);
//			itemVo.setJudgeList(judges);
//			
//			itemVo.setAwardAmt(item.getAwardAmt());
//		
//		}
//		itemVo.setItem(item);
//
//		return itemVo;
//	}

	
//	private RewardVo convertFromWinnerRewardToVo(Winner win,Reward reward, boolean isEntire) {
//		RewardVo rewardVo = new RewardVo();
//		if (isEntire) {
//			String rewardId = reward.getId();
//			// candidate rule
//			CandidateRule candidateRule = candidateRuleLogic
//					.findCandidateRuleFromReward(rewardId);
//			// candidate list
//			List<Candidate> candidates = candidateLogic
//					.getCandidatesFromReward(rewardId);
//			// Judge list
//			List<Judge> judges = judgeLogic.findJudgesFromReward(rewardId);
//			// nominee lot
//			List<NomineeLot> nomineeLots = nomineeLogic
//					.getNomineeLotsFromReward(rewardId);
//			// pre-winner
//			List<PreWinnerLot> preWinnerLots = preWinnerLogic
//					.getPreWinnerLotsFromReward(rewardId);
//			// winner
//			List<Winner> winners = winnerLogic.getWinnersOfReward(rewardId);
//
//			rewardVo.setReward(reward);
//			rewardVo.setCandidateRule(candidateRule);
//			rewardVo.setCandidates(candidates);
//			rewardVo.setJudges(judges);
//			rewardVo.setNomineeLots(nomineeLots);
//			rewardVo.setPreWinnerLots(preWinnerLots);
//			rewardVo.setWinners(winners);
//			rewardVo.setAwardDate(win.getCreatedAt());
//		}
//		rewardVo.setReward(reward);
//
//		return rewardVo;
//	}
}
