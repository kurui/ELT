package com.chinarewards.elt.service.reward.acl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chinarewards.elt.dao.org.DepartmentManagerDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.reward.RewardItemStoreDao;
import com.chinarewards.elt.dao.reward.WinnerDao;
import com.chinarewards.elt.dao.user.UserDao;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.base.RewardItemStore;
import com.chinarewards.elt.domain.user.SysUser;
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
	private final DepartmentManagerDao deptMgrDao;
	private final UserDao userDao;
	
	@Inject
	public RewardAclProcessorHr(RewardDao rewardsDao,RewardItemStoreDao rewardsItemStoreDao,UserDao userDao,
			RewardItemDao rewardsItemDao,DepartmentManagerDao deptMgrDao,DepartmentLogic departmentLogic) {
		this.rewardsDao = rewardsDao;
		this.rewardsItemDao = rewardsItemDao;
		this.departmentLogic = departmentLogic;
		this.rewardsItemStoreDao = rewardsItemStoreDao;
		this.deptMgrDao=deptMgrDao;
		this.userDao = userDao;
	}
	
	@Override
	public PageStore<RewardItem> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		   
 		if (!StringUtil.isEmptyString(criteria.getDepartmentId())) {
				List<String> deptIds = null;
				logger.debug("criteria.isSubDepartmentChoose: {}",
						criteria.isSubDepartmentChosen());
				if (criteria.isSubDepartmentChosen()) {
					deptIds = departmentLogic.getWholeChildrenIds(
							criteria.getDepartmentId(), true);
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
				deptIds = departmentLogic.getWholeChildrenIds(
						criteria.getDepartmentId(), true);
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
