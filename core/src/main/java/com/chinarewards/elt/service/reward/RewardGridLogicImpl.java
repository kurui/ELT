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
			List<Reward> itemList) {
		List<RewardGridVo> gridVoList = new ArrayList<RewardGridVo>();
		return gridVoList;
	}

	private List<RewardGridVo> convertToGridVoListFromItem(
			List<RewardItem> itemList) {
		List<RewardGridVo> gridVoList = new ArrayList<RewardGridVo>();
		return gridVoList;
	}

}
