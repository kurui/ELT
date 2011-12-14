package com.chinarewards.elt.service.reward.acl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.chinarewards.elt.dao.org.DeptIdResolverDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class RewardAclProcessorHr extends AbstractRewardAclProcessor {

	private final RewardDao rewardsDao;
	private final RewardItemDao rewardsItemDao;
	private final DeptIdResolverDao deptIdDao;

	@Inject
	public RewardAclProcessorHr(RewardDao rewardsDao,
			RewardItemDao rewardsItemDao, DeptIdResolverDao deptIdDao) {
		this.rewardsDao = rewardsDao;
		this.rewardsItemDao = rewardsItemDao;
		this.deptIdDao = deptIdDao;
	}

	@Override
	public PageStore<RewardItem> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		logger.debug(
				" Process in fetchRewardsItems method, UserId:{}, criteria:{},departmentId:{},chooseSubDept:{}",
				new Object[] { context.getUserId(), criteria,
						criteria.getDepartmentId(),
						criteria.isSubDepartmentChosen() });
		criteria.setCorporationId(context.getCorporationId());

		// Should not pollute the input object! Clone it!
		if (null != criteria.getDeptIds() && !criteria.getDeptIds().isEmpty()) {
			// The depIds have priority. If it exist, do not need to observe
			// departmentId again.
		} else if (!StringUtil.isEmptyString(criteria.getDepartmentId())) {
			Set<String> deptIds = null;
			if (criteria.isSubDepartmentChosen()) {
				deptIds = deptIdDao.findSiblingIds(criteria.getDepartmentId(),
						true);
			} else {
				deptIds = new TreeSet<String>();
				deptIds.add(criteria.getDepartmentId());
			}
			criteria.setDeptIds(new ArrayList<String>(deptIds));
			criteria.setSubDepartmentChosen(false); // since we have converted
													// it.
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

}
