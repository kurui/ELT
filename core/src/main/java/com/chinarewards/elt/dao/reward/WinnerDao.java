package com.chinarewards.elt.dao.reward;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.base.WinnerProcessFlag;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.staff.StaffWinSearchCriteria;
import com.chinarewards.elt.model.staff.StaffWinVo;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * DAO of {@link Winner}
 * 
 * @author yanxin
 * @since 1.0
 */
public class WinnerDao extends BaseDao<Winner> {
	StaffDao staffDao;

	@Inject
	public WinnerDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	@SuppressWarnings("unchecked")
	public List<Winner> findUntreatedWinnersByRewardId(String rewardId) {
		List<WinnerProcessFlag> processFlags = new ArrayList<WinnerProcessFlag>();
		processFlags.add(WinnerProcessFlag.UNPROCESS);
		processFlags.add(WinnerProcessFlag.PROCESS_FAIL);
		return getEm()
				.createQuery(
						" FROM Winner win WHERE win.reward.id=:rewardId AND win.processFlag IN (:processFlags) ")
				.setParameter("processFlags", processFlags)
				.setParameter("rewardId", rewardId).getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Winner> findWinnersByRewardId(String rewardId) {
		return getEm()
				.createQuery(" FROM Winner win WHERE win.reward.id=:rewardId ")
				.setParameter("rewardId", rewardId).getResultList();

	}

	// 查出某个员工在某年获得的生日奖记录
	// 如果 2010-12-23 --- 2011-01-22(以哪一年为准呢).看具体的生日日期
	@SuppressWarnings("unchecked")
	public List<Winner> findWinnersByStaffAndDateRangeOfDobReward(
			String staffId, String rewardItemId, Date from, Date to) {

		Calendar c = Calendar.getInstance();
		c.setTime(from);
		int yearFrom = c.get(Calendar.YEAR);
		int monthFrom = c.get(Calendar.MONTH);
		int dayFrom = c.get(Calendar.DAY_OF_MONTH);
		c.setTime(to);
		int yearTo = c.get(Calendar.YEAR);
		int monthTo = c.get(Calendar.MONTH) + 1;
		int dayTo = c.get(Calendar.DAY_OF_MONTH);

		logger.debug(
				"yearFrom:{}, monthFrom{},dayFrom{}, yearTo:{},monthTo:{},dayTo:{}",
				new Object[] { yearFrom, monthFrom, dayFrom, yearTo, monthTo,
						dayTo });

		StringBuffer eql = new StringBuffer(
				" FROM Winner win WHERE win.organization.id = :staffId AND win.reward.rewardItem.id = :rewardItemId ");
		eql.append(" AND YEAR(win.reward.awardDate) = :year ");

		Query q = getEm().createQuery(eql.toString());
		q.setParameter("staffId", staffId);
		q.setParameter("rewardItemId", rewardItemId);
		if (yearFrom == yearTo) {
			q.setParameter("year", yearFrom);
		} else if (yearFrom < yearTo) {
			// Get which year the staff
			Staff staff = staffDao.findById(Staff.class, staffId);
			Date dob = staff.getDob();
			c.setTime(dob);
			if ((c.get(Calendar.MONTH) == monthFrom && c
					.get(Calendar.DAY_OF_MONTH) > dayFrom)
					|| c.get(Calendar.MONTH) > monthFrom) {
				q.setParameter("year", yearFrom);
			} else {
				q.setParameter("year", yearTo);
			}
		}
		return q.getResultList();
	}
	
	
	public StaffWinVo queryStaffWinRewardPageAction(StaffWinSearchCriteria criteria) {

		StaffWinVo result = new StaffWinVo();

		result.setResultList(queryStaffWinRewardPageActionData(criteria));
		result.setTotal(queryStaffWinRewardPageActionCount(criteria));

		return result;
	}
   
	@SuppressWarnings("unchecked")
	private List<Winner> queryStaffWinRewardPageActionData(StaffWinSearchCriteria searchVo) {
		return getStaffQuery(searchVo, SEARCH).getResultList();
	}

	public int queryStaffWinRewardPageActionCount(StaffWinSearchCriteria searchVo) {
		return Integer.parseInt(getStaffQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getStaffQuery(StaffWinSearchCriteria searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT win FROM Winner win WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(win) FROM Winner win WHERE 1=1 ");
		}
		
		if (!StringUtil.isEmptyString(searchVo.getStaffId())) {
			hql.append(" AND win.staff.id = :staffId ");
			param.put("staffId", searchVo.getStaffId());
		}
			
		
		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null && searchVo.getSortingDetail().getSort()!=null && searchVo.getSortingDetail().getDirection()!=null) {
				hql.append(" ORDER BY win."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY win.winTime DESC ");
			}
		}
		
		logger.debug(" HQL:{} ", hql);
		Query query = getEm().createQuery(hql.toString());
		if (SEARCH.equals(type)) {
			if (searchVo.getPaginationDetail() != null) {
				int limit = searchVo.getPaginationDetail().getLimit();
				int start = searchVo.getPaginationDetail().getStart();

				logger.debug("pagination - start{}, limit:{}", new Object[] {
						start, limit });

				query.setMaxResults(limit);
				query.setFirstResult(start);
			}
		}
		if (param.size() > 0) {
			Set<String> key = param.keySet();
			for (String s : key) {
				query.setParameter(s, param.get(s));
			}
		}
		return query;
	}
	
	//=============员工
	public PageStore<Reward> searchRewards_staff(RewardSearchVo criteria) {
		PageStore<Reward> res = new PageStore<Reward>();
		List<Reward> list = queryCurrentStaffWinRewardData(criteria);
		int count = searchCurrentStaffWinRewardsCount(criteria);
		res.setResultList(list);
		res.setResultCount(count);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private List<Reward> queryCurrentStaffWinRewardData(RewardSearchVo searchVo) {
		return getCurrentStaffQuery(searchVo, SEARCH).getResultList();
	}

	public int searchCurrentStaffWinRewardsCount(RewardSearchVo searchVo) {
		return Integer.parseInt(getCurrentStaffQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getCurrentStaffQuery(RewardSearchVo searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT win.reward FROM Winner win WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(win) FROM Winner win WHERE 1=1 ");
		}
		
		if (!StringUtil.isEmptyString(searchVo.getWinnerStaffId())) {
			hql.append(" AND win.staff.id = :staffId ");
			param.put("staffId", searchVo.getWinnerStaffId());
		}			
		
		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null && searchVo.getSortingDetail().getSort()!=null && searchVo.getSortingDetail().getDirection()!=null) {
				hql.append(" ORDER BY win."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY win.winTime DESC ");
			}
		}
		
		logger.debug(" HQL:{} ", hql);
		Query query = getEm().createQuery(hql.toString());
		if (SEARCH.equals(type)) {
			if (searchVo.getPaginationDetail() != null) {
				int limit = searchVo.getPaginationDetail().getLimit();
				int start = searchVo.getPaginationDetail().getStart();

				logger.debug("pagination - start{}, limit:{}", new Object[] {
						start, limit });

				query.setMaxResults(limit);
				query.setFirstResult(start);
			}
		}
		if (param.size() > 0) {
			Set<String> key = param.keySet();
			for (String s : key) {
				query.setParameter(s, param.get(s));
			}
		}
		return query;
	}
	
	public PageStore<RewardItem> searchRewardItem_staff(RewardItemSearchVo criteria) {
		PageStore<RewardItem> res = new PageStore<RewardItem>();
		List<RewardItem> list = queryCurrentStaffWinRewardItemData(criteria);
		int count = searchCurrentStaffWinRewardItemCount(criteria);
		res.setResultList(list);
		res.setResultCount(count);
		return res;
	}
	
	public PageStore<RewardItem> searchRewardItem_companyOther(RewardItemSearchVo criteria) {
		PageStore<RewardItem> res = new PageStore<RewardItem>();
		List<RewardItem> list = queryCompanyOtherRewardItemData(criteria);
		int count = searchCompanyOtherRewardItemCount(criteria);
		res.setResultList(list);
		res.setResultCount(count);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private List<RewardItem> queryCurrentStaffWinRewardItemData(RewardItemSearchVo searchVo) {
		return getCurrentStaffRewardItemQuery(searchVo, SEARCH).getResultList();
	}

	public int searchCurrentStaffWinRewardItemCount(RewardItemSearchVo searchVo) {
		return Integer.parseInt(getCurrentStaffRewardItemQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getCurrentStaffRewardItemQuery(RewardItemSearchVo searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT win.reward.rewardItem FROM Winner win WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(win.reward.rewardItem) FROM Winner win WHERE 1=1 ");
		}
		
		if (!StringUtil.isEmptyString(searchVo.getStaffId())) {
			hql.append(" AND win.staff.id = :staffId ");
			param.put("staffId", searchVo.getStaffId());
		}			
		
		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null && searchVo.getSortingDetail().getSort()!=null && searchVo.getSortingDetail().getDirection()!=null) {
				hql.append(" ORDER BY win."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY win.winTime DESC ");
			}
		}
		
		logger.debug(" HQL:{} ", hql);
		Query query = getEm().createQuery(hql.toString());
		if (SEARCH.equals(type)) {
			if (searchVo.getPaginationDetail() != null) {
				int limit = searchVo.getPaginationDetail().getLimit();
				int start = searchVo.getPaginationDetail().getStart();

				logger.debug("pagination - start{}, limit:{}", new Object[] {
						start, limit });

				query.setMaxResults(limit);
				query.setFirstResult(start);
			}
		}
		if (param.size() > 0) {
			Set<String> key = param.keySet();
			for (String s : key) {
				query.setParameter(s, param.get(s));
			}
		}
		return query;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<RewardItem> queryCompanyOtherRewardItemData(RewardItemSearchVo searchVo) {
		return getCompanyOtherRewardItemQuery(searchVo, SEARCH).getResultList();
	}

	public int searchCompanyOtherRewardItemCount(RewardItemSearchVo searchVo) {
		return Integer.parseInt(getCompanyOtherRewardItemQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getCompanyOtherRewardItemQuery(RewardItemSearchVo searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT win.reward.rewardItem FROM Winner win WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(win.reward.rewardItem) FROM Winner win WHERE 1=1 ");
		}
		
		if (!StringUtil.isEmptyString(searchVo.getCorporationId())) {
			hql.append(" AND win.reward.corporation.id = :corporationId ");
			param.put("corporationId", searchVo.getCorporationId());
		}	
		
		if (!StringUtil.isEmptyString(searchVo.getStaffId())) {
			hql.append(" AND win.staff.id != :staffId ");
			param.put("staffId", searchVo.getStaffId());
		}			
		
		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null && searchVo.getSortingDetail().getSort()!=null && searchVo.getSortingDetail().getDirection()!=null) {
				hql.append(" ORDER BY win."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY win.winTime DESC ");
			}
		}
		
		logger.debug(" HQL:{} ", hql);
		Query query = getEm().createQuery(hql.toString());
		if (SEARCH.equals(type)) {
			if (searchVo.getPaginationDetail() != null) {
				int limit = searchVo.getPaginationDetail().getLimit();
				int start = searchVo.getPaginationDetail().getStart();

				logger.debug("pagination - start{}, limit:{}", new Object[] {
						start, limit });

				query.setMaxResults(limit);
				query.setFirstResult(start);
			}
		}
		if (param.size() > 0) {
			Set<String> key = param.keySet();
			for (String s : key) {
				query.setParameter(s, param.get(s));
			}
		}
		return query;
	}
}
