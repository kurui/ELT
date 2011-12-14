package com.chinarewards.elt.service.reward.acl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.chinarewards.elt.dao.org.DepartmentManagerDao;
import com.chinarewards.elt.dao.org.DeptIdResolverDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.user.SysUserDao;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class RewardAclProcessorDept extends AbstractRewardAclProcessor {
	private final RewardDao rewardsDao;
	private final SysUserDao userDao;
	private final RewardItemDao rewardsItemDao;
	private final DepartmentManagerDao deptMgrDao;
	private final DeptIdResolverDao deptIdDao;

	@Inject
	public RewardAclProcessorDept(RewardDao rewardsDao, SysUserDao userDao,
			RewardItemDao rewardsItemDao, DepartmentManagerDao deptMgrDao,
			DeptIdResolverDao deptIdDao) {
		this.rewardsDao = rewardsDao;
		this.userDao = userDao;
		this.rewardsItemDao = rewardsItemDao;
		this.deptMgrDao = deptMgrDao;
		this.deptIdDao = deptIdDao;
	}

	private List<String> getSupportedDeptIds(String staffId) {
		List<String> departmentIds = deptMgrDao
				.findDepartmentIdsManagedByStaffId(staffId);
		// resolve siblings as well since the API does not return siblings
		// XXX it is slow!
		Set<String> allDepartmentIds = new HashSet<String>();
		for (String id : departmentIds) {
			allDepartmentIds.addAll(deptIdDao.findSiblingIds(id, true));
		}
		departmentIds.clear();
		departmentIds.addAll(allDepartmentIds);
		logger.debug(
				" Department IDs (include siblings) managed by this user: {}",
				departmentIds);

		return departmentIds;
	}

	@Override
	public PageStore<RewardItem> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		logger.debug(
				" Process in fetchRewardsItems method, UserId:{}, criteria:{}",
				new Object[] { context.getUserId(), criteria });
		SysUser sysUser = userDao.findById(SysUser.class, context.getUserId());

		List<UserRole> roles = new ArrayList<UserRole>(Arrays.asList(context
				.getUserRoles()));

		logger.debug("Is department manager:{}",
				roles.contains(UserRole.DEPT_MGR));

		logger.debug("criteria.getDeptIds() = {}", criteria.getDeptIds());

		// serialize to a list of department IDs.
		if (null != criteria.getDeptIds() && !criteria.getDeptIds().isEmpty()) {
			// The depIds have priority. If it exist, do not need to observe
			// departmentId again.
		} else if (!StringUtil.isEmptyString(criteria.getDepartmentId())) {
			Set<String> deptIds = null;
			logger.debug("criteria.isSubDepartmentChoose: {}",
					criteria.isSubDepartmentChosen());
			if (criteria.isSubDepartmentChosen()) {
				deptIds = deptIdDao.findSiblingIds(criteria.getDepartmentId(),
						true);
				logger.debug("Siblings dept IDs of {}: {}",
						criteria.getDepartmentId(), deptIds);
			} else {
				deptIds = new TreeSet<String>();
				deptIds.add(criteria.getDepartmentId());
			}
			criteria.setDeptIds(new ArrayList<String>(deptIds));
			criteria.setSubDepartmentChosen(false);
		}

		logger.debug(
				"criteria.getDeptIds() after resolving child departments = {}",
				criteria.getDeptIds());

		// Strip out any invisible departments

		List<String> expectedDeptIds = getSupportedDeptIds(sysUser.getStaff()
				.getId());

		// FIXME this is rude!!
		// strip out any unsupported departments which should not be
		// visible.
		// FIXME it seems that this will not resolve the sub-departments,
		// as well as the DAO.
		if (criteria.getDeptIds() != null && !criteria.getDeptIds().isEmpty()) {
			List<String> ids = new ArrayList<String>();
			ids.addAll(criteria.getDeptIds());
			ids.retainAll(expectedDeptIds);
			criteria.setDeptIds(ids);
			logger.debug("After stripping, criteria.getDeptIds()={}",
					criteria.getDeptIds());
			// if we made it empty, no hope
			if (ids.isEmpty()) {
				PageStore<RewardItem> pageStore = new PageStore<RewardItem>();
				pageStore.setResultCount(0);
				pageStore.setResultList(new ArrayList<RewardItem>());
				return pageStore;
			}
		} else {
			criteria.setDeptIds(expectedDeptIds);
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
		logger.debug(" Process in StaffRoleProcessor fetchRewards method,UserId:"
				+ context.getUserId());
		PageStore<Reward> res = new PageStore<Reward>();

		SysUser hrUser = userDao.findById(SysUser.class, context.getUserId());

		List<UserRole> roles = new ArrayList<UserRole>(Arrays.asList(context
				.getUserRoles()));

		logger.debug("Is department manager:{}",
				roles.contains(UserRole.DEPT_MGR));

		logger.debug(" This HrUser is department manager Staff ");
		List<String> departmentIds = deptMgrDao
				.findDepartmentIdsManagedByStaffId(hrUser.getStaff().getId());
		Set<String> allDepartmentIds = new HashSet<String>();
		for (String id : departmentIds) {
			allDepartmentIds.addAll(deptIdDao.findSiblingIds(id, true));
		}
		departmentIds.clear();
		departmentIds.addAll(allDepartmentIds);

		logger.debug(" This Hruser manager department.id:" + departmentIds);
		res = rewardsDao.searchRewards_departmentId(departmentIds, criteria);
		return res;
	}
}
