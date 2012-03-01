package com.chinarewards.elt.service.reward.acl;

import com.chinarewards.elt.dao.reward.WinnerDao;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.base.RewardItemStore;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.reward.vo.WinerRewardItemVo;
import com.chinarewards.elt.model.user.UserContext;
import com.google.inject.Inject;

public class RewardAclProcessorStaff extends AbstractRewardAclProcessor {
//	private final RewardDao rewardsDao;
//	private final UserDao userDao;
//	private final RewardItemDao rewardsItemDao;
//	private final DepartmentManagerDao deptMgrDao;
//	private final DepartmentLogic departmentLogic;
//	private final RewardItemStoreDao rewardsItemStoreDao;
	private final WinnerDao winnerDao;

	@Inject
	public RewardAclProcessorStaff(/*RewardDao rewardsDao, UserDao userDao,
			RewardItemDao rewardsItemDao, DepartmentManagerDao deptMgrDao,
			DepartmentLogic departmentLogic,
			RewardItemStoreDao rewardsItemStoreDao,*/ WinnerDao winnerDao) {
		// this.rewardsDao = rewardsDao;
		// this.userDao = userDao;
		// this.rewardsItemDao = rewardsItemDao;
		// this.deptMgrDao = deptMgrDao;
		// this.departmentLogic = departmentLogic;
		// this.rewardsItemStoreDao = rewardsItemStoreDao;
		this.winnerDao = winnerDao;
	}

	@Override
	public PageStore<Winner> fetchWinRewards(UserContext context,
			RewardSearchVo criteria) {
		logger.debug(" Process in StaffRoleProcessor fetchRewards method,UserId:"
				+ context.getUserId());
		PageStore<Winner> pageStore = new PageStore<Winner>();

		pageStore = winnerDao.searchRewards_staff_winner(criteria);

		return pageStore;
	}

	/**
	 * 员工 我参与的奖项
	 * */
	@Override
	public PageStore<WinerRewardItemVo> fetchWinRewardItems(UserContext context,
			RewardItemSearchVo criteria) {

		PageStore<WinerRewardItemVo> pageStore = new PageStore<WinerRewardItemVo>();
		pageStore = winnerDao.searchRewardItem_staff(criteria);

		return pageStore;
	}
	
	public PageStore<RewardItem> fetchRewardItems_companyOther(UserContext context,
			RewardItemSearchVo criteria) {

		PageStore<RewardItem> pageStore = new PageStore<RewardItem>();
		
		pageStore = winnerDao.searchRewardItem_companyOther(criteria);

		return pageStore;
	}

	@Override
	public PageStore<RewardItemStore> fetchRewardItemsStore(
			UserContext context, RewardItemSearchVo criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageStore<Reward> fetchRewards(UserContext context,
			RewardSearchVo criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageStore<RewardItem> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
