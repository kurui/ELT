package com.chinarewards.elt.service.reward;

import java.util.Date;
import java.util.List;

import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.base.RewardItemParam;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardItemVo;
import com.chinarewards.elt.model.user.UserContext;

/**
 * The implementation of {@link RewardItemService}
 * 
 * @author yanxin
 * @since 1.0
 */
public class RewardItemServiceImpl implements RewardItemService {

	@Override
	public RewardItem saveRewardItem(UserContext context, RewardItemParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRewardItem(String rewardItemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RewardItemVo fetchEntireRewardItemById(String rewardItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageStore<RewardItemVo> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageStore<RewardItemVo> fetchRewardItemsNoAcl(
			RewardItemSearchVo criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RewardItem> fetchAutoGenerateRewardItem(Date currTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RewardItem enableRewardItem(UserContext context, String rewardItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RewardItem disableRewardItem(UserContext context, String rewardItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void runAutoRewardGeneratorBatch(Date flagTime) {
		// TODO Auto-generated method stub
		
	}

}
