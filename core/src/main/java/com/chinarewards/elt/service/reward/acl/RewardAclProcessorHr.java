package com.chinarewards.elt.service.reward.acl;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.reward.RewardItemStoreDao;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.base.RewardItemStore;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class RewardAclProcessorHr extends AbstractRewardAclProcessor {

	private final RewardDao rewardsDao;
	private final RewardItemDao rewardsItemDao;
	private final DepartmentLogic departmentLogic;
	private final RewardItemStoreDao rewardsItemStoreDao;

	
	@Inject
	public RewardAclProcessorHr(RewardDao rewardsDao,RewardItemStoreDao rewardsItemStoreDao,
			RewardItemDao rewardsItemDao,DepartmentLogic departmentLogic) {
		this.rewardsDao = rewardsDao;
		this.rewardsItemDao = rewardsItemDao;
		this.departmentLogic = departmentLogic;
		this.rewardsItemStoreDao = rewardsItemStoreDao;
		
	}
	
	@Override
	public PageStore<RewardItem> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		   
 		if (!StringUtil.isEmptyString(criteria.getDepartmentId())) {
				List<String> deptIds = null;
				logger.debug("criteria.isSubDepartmentChoose: {}",
						criteria.isSubDepartmentChosen());
				if (criteria.isSubDepartmentChosen()) {
//					deptIds = departmentLogic.getWholeChildrenIds(
//							criteria.getDepartmentId(), true);
					deptIds = departmentLogic.getAllChildrenIds(
							criteria.getDepartmentId(),true);
					
					logger.debug("Siblings dept IDs of {}: {}",
							criteria.getDepartmentId(), deptIds);
				} else {
					deptIds = new ArrayList<String>();
					deptIds.add(criteria.getDepartmentId());
				}
				criteria.setDeptIds(new ArrayList<String>(deptIds));
				criteria.setSubDepartmentChosen(false);
			}

				
			PageStore<RewardItem> pageStore = new PageStore<RewardItem>();
				

		
		pageStore.setResultCount(rewardsItemDao.countRewardsItems(criteria));
		List<RewardItem> itemList = rewardsItemDao.fetchRewardsItems(criteria);
		pageStore.setResultList(itemList);
		return pageStore;
	}
	

	@Override
	public PageStore<Reward> fetchRewards(UserContext context,
			RewardSearchVo criteria) {
		logger.debug(
				" Process in fetchRewards method, CorporationId:{}, criteria:{}",
				new Object[] { context.getCorporationId(), criteria.toString() });
		String corporationId = context.getCorporationId();
		PageStore<Reward> res = new PageStore<Reward>();
		res = rewardsDao.searchRewards_hrManager(corporationId, criteria);
		return res;
	}
	

	@Override
	public PageStore<RewardItemStore> fetchRewardItemsStore(
			UserContext context, RewardItemSearchVo criteria) {
		if (!StringUtil.isEmptyString(criteria.getDepartmentId())) {
			List<String> deptIds = null;
			logger.debug("criteria.isSubDepartmentChoose: {}",
					criteria.isSubDepartmentChosen());
			if (criteria.isSubDepartmentChosen()) {
//				deptIds = departmentLogic.getWholeChildrenIds(
//						criteria.getDepartmentId(), true);
				deptIds = departmentLogic.getAllChildrenIds(
						criteria.getDepartmentId(),true);
				
				logger.debug("Siblings dept IDs of {}: {}",
						criteria.getDepartmentId(), deptIds);
			} else {
				deptIds = new ArrayList<String>();
				deptIds.add(criteria.getDepartmentId());
			}
			criteria.setDeptIds(new ArrayList<String>(deptIds));
			criteria.setSubDepartmentChosen(false);
		}
		PageStore<RewardItemStore> pageStore = new PageStore<RewardItemStore>();
		pageStore.setResultCount(rewardsItemStoreDao.countRewardsItemsStore(criteria));
		List<RewardItemStore> itemList = rewardsItemStoreDao.fetchRewardsItemsStore(criteria);
		pageStore.setResultList(itemList);
		return pageStore;
	}

}
