package com.chinarewards.elt.service.reward;

import java.util.Date;
import java.util.List;

import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.base.RewardItemParam;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardItemVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.vo.StaffAndDeptmentAutoCompile;

/**
 * Some useful methods list here to help us to manipulate {@link RewardItem}
 * 
 * @author yanxin
 * @since 1.0
 */
public interface RewardItemService {

	/**
	 * Add or update a record of RewardItem. See more detail at
	 * {@link RewardItemParam}.
	 * 
	 * @param param
	 * @return
	 */
	public RewardItem saveRewardItem(UserContext context, RewardItemParam param);
         
	/**
	 * Delete the specified RewardItem. Just delete in a logical way.
	 * 
	 * @param rewardItemId
	 */
	public void deleteRewardItem(String rewardItemId);

	/**
	 * Fetch the entire information about the specified RewardItem. It contains
	 * all the external informations.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	public RewardItemVo fetchEntireRewardItemById(String rewardItemId);

	/**
	 * 
	 * Returns a list of RewardsItem matching criteria.
	 * <p>
	 * 
	 * This method respect the caller context and limit the visibility of the
	 * returning data. To return data without data limitation of the caller
	 * context, use {@link #fetchRewardsItemsNoAcl(RewardItemSearchVo)} instead.
	 * 
	 * @param context
	 * @param criteria
	 * @return
	 */
	public PageStore<RewardItemVo> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria);

	/**
	 * Fetch list of RewardItem by RewardItemSearchVo. All roles would get the
	 * same results.
	 * 
	 * @param criteria
	 * @return
	 */
	public PageStore<RewardItemVo> fetchRewardItemsNoAcl(
			RewardItemSearchVo criteria);

	/**
	 * Fetch all auto-generate RewardItem by current time. Which means find out
	 * those qualified RewardItem list.
	 * 
	 * @param currTime
	 * @return
	 */
	public List<RewardItem> fetchAutoGenerateRewardItem(Date currTime);

	/**
	 * Make the specified RewardItem enabled.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	public String enableRewardItem(UserContext context, String rewardItemId);

	/**
	 * Make the specified RewardItem disabled.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	public String disableRewardItem(UserContext context, String rewardItemId);

	/**
	 * Run batch to generate rewards automatic.
	 * 
	 * @param flagTime
	 */
	public void runAutoRewardGeneratorBatch(Date flagTime);
	
	/**
	 * 员工和部门的auto compile 功能的接口
	 * 
	 * @param falg
	 * @param limit
	 * @return
	 */
	public List<StaffAndDeptmentAutoCompile> staffAndDeptmentAutoCompile(String corporationId,String falg, int limit);
	


}
