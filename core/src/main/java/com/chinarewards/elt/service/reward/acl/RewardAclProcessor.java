package com.chinarewards.elt.service.reward.acl;

import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.base.RewardItemStore;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.user.UserContext;

/**
 * The interface defines the methods which need to implement by different role
 * processors.
 * 
 * @author yanxin
 * @since 1.0
 */
public interface RewardAclProcessor {

	/**
	 * Obtain the list of {@link Reward} managed by the login user.
	 * 
	 * @param context
	 * @param criteria
	 * @return
	 */
	public PageStore<Reward> fetchRewards(UserContext context,
			RewardSearchVo criteria);

	/**
	 * Obtain the list of {@link RewardItem} managed by the login user.
	 * 
	 * @param context
	 * @param criteria
	 * @return
	 */
	public PageStore<RewardItem> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria);
	
	public PageStore<RewardItemStore> fetchRewardItemsStore(UserContext context,
			RewardItemSearchVo criteria);
}
