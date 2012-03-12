package com.chinarewards.elt.dao.reward;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.reward.person.PreWinner;
import com.chinarewards.elt.domain.reward.person.PreWinnerLot;
import com.chinarewards.elt.model.reward.base.PreWinnerLotStatus;
import com.chinarewards.elt.model.reward.search.RewardGridSearchVo;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;

/**
 * DAO of {@link PreWinner}
 * 
 * @author yanxin
 * @since 1.0
 */
public class PreWinnerDao extends BaseDao<PreWinner> {

	@SuppressWarnings("unchecked")
	public Map<String, PreWinner> findPreWinnersByIds(List<String> ids) {
		Map<String, PreWinner> res = new HashMap<String, PreWinner>();
		List<Object[]> temp = getEm()
				.createQuery(
						" SELECT winner.id, winner FROM PreWinner winner WHERE winner.id IN(:ids) ")
				.setParameter("ids", ids).getResultList();
		for (Object[] objs : temp) {
			String orgId = (String) objs[0];
			if (!res.containsKey(orgId)) {
				res.put(orgId, (PreWinner) objs[1]);
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<PreWinner> findPreWinnerByPreWinnerLotId(String preWinnerLotId) {
		return getEm()
				.createQuery(
						" FROM PreWinner winner WHERE winner.preWinnerLot.id=:preWinnerLotId ")
				.setParameter("preWinnerLotId", preWinnerLotId).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PreWinner> findPreWinnerByStaffId(String staffId) {
		return getEm()
				.createQuery(
						" FROM PreWinner winner WHERE winner.staff.id=:staffId ")
				.setParameter("staffId", staffId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<PreWinner> findPreWinnerByStaffName(String staffName) {
		return getEm()
				.createQuery(
						" FROM PreWinner winner WHERE winner.staff.name like :staffName ")
				.setParameter("staffName", staffName).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<PreWinnerLot> getPreWinnerLotsByRewardIdAndStatusList(
			String rewardId, List<PreWinnerLotStatus> statusList) {
		return getEm()
				.createQuery(
						" FROM PreWinnerLot lot WHERE lot.reward.id=:rewardId AND lot.status in (:statusList) ORDER BY lot.createdAt desc")
				.setParameter("rewardId", rewardId)
				.setParameter("statusList", statusList).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PreWinnerLot> queryRewardHistoryData(RewardGridSearchVo searchVo) {
		return getRewardHistoryQuery(searchVo, SEARCH).getResultList();
	}

	public int queryRewardHistoryCount(RewardGridSearchVo searchVo) {
		return Integer.parseInt(getRewardHistoryQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getRewardHistoryQuery(RewardGridSearchVo searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
//		if (SEARCH.equals(type)) {
//			hql.append(" SELECT lot FROM  ( SELECT DISTINCT win.preWinnerLot lot FROM PreWinner win WHERE 1=1 ");
//		} else if (COUNT.equals(type)) {
//			hql.append(" SELECT COUNT(win.preWinnerLot) FROM  (SELECT DISTINCT win.preWinnerLot FROM PreWinner win WHERE 1=1 ");
//		}
		
		if (SEARCH.equals(type)) {
			hql.append(" SELECT  distinct win.preWinnerLot FROM PreWinner win WHERE 1=1 ");
	} else if (COUNT.equals(type)) {
		hql.append(" SELECT count(distinct win.preWinnerLot) FROM PreWinner win WHERE 1=1 ");
	}
		
	

		if (!StringUtil.isEmptyString(searchVo.getStaffId())) {
				hql.append(" AND win.staff.id = :staffId ");
				param.put("staffId", searchVo.getStaffId());
		}

		if (!StringUtil.isEmptyString(searchVo.getStaffName())) {
				hql.append(" AND win.staff.name like :staffName ");
				param.put("staffName", "%"+searchVo.getStaffName()+"%");			
		}

		// 奖项ID
		if (!StringUtil.isEmptyString(searchVo.getRewardItemId())) {
			hql.append(" AND win.preWinnerLot.reward.rewardItem.id = :rewardsItemId ");
			param.put("rewardsItemId", searchVo.getRewardItemId());
		}
		
		// 奖项Name
		if (!StringUtil.isEmptyString(searchVo.getRewardItemName())) {
			hql.append(" AND win.preWinnerLot.reward.rewardItem.name like :rewardsItemName ");
			param.put("rewardsItemName", "%"+searchVo.getRewardItemName()+"%");
		}

		// 获奖时间
		if (null != searchVo.getRewardsDate()
				&& !searchVo.getRewardsDate().equals("")) {
			Date rewardsTimeBegin = DateUtil.getEarlierTimeOfThisDay(searchVo
					.getRewardsDate());
			Date rewardsTimeEnd = DateUtil.getLastTimeOfThisDay(searchVo
					.getRewardsDate());

			hql.append(" and ( win.preWinnerLot.reward.lastModifiedAt  between :rewardsTimeBegin and :rewardsTimeEnd)");
			param.put("rewardsTimeBegin", rewardsTimeBegin);
			param.put("rewardsTimeEnd", rewardsTimeEnd);
		}
		
//		hql.append(" )  ");

		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null
					&& searchVo.getSortingDetail().getSort() != null
					&& searchVo.getSortingDetail().getDirection() != null) {
				hql.append(" ORDER BY win.preWinnerLot."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY win.preWinnerLot.lastModifiedAt DESC ");
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
