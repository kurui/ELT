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
import com.google.inject.Inject;

/**
 * DAO of {@link PreWinnerLot}
 * 
 * @author yanxin
 * @since 1.0
 */
public class PreWinnerLotDao extends BaseDao<PreWinnerLot> {

	PreWinnerDao preWinnerDao;
	
	@Inject
	public PreWinnerLotDao(PreWinnerDao preWinnerDao) {
		this.preWinnerDao = preWinnerDao;
	}

	@SuppressWarnings("unchecked")
	public List<PreWinnerLot> getPreWinnerLotsByRewardId(String rewardId) {
		return getEm()
				.createQuery(
						" FROM PreWinnerLot lot WHERE lot.reward.id=:rewardId ")
				.setParameter("rewardId", rewardId).getResultList();
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
		if (SEARCH.equals(type)) {
			hql.append(" SELECT win FROM PreWinnerLot win WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(win) FROM PreWinnerLot win WHERE 1=1 ");
		}

		if (!StringUtil.isEmptyString(searchVo.getStaffId())) {
			List<PreWinner> preWinners = preWinnerDao
					.findPreWinnerByStaffId(searchVo.getStaffId());
			if (preWinners != null) {
				hql.append(" AND win.preWinners in preWinners ");
				param.put("preWinners", preWinners);
			}
		}

		if (!StringUtil.isEmptyString(searchVo.getStaffName())) {
			List<PreWinner> preWinners = preWinnerDao
					.findPreWinnerByStaffName(searchVo.getStaffName());
			if (preWinners != null) {
				hql.append(" AND win.preWinners in :preWinners ");
				param.put("preWinners", preWinners);
			}
		}

		// 奖项ID
		if (!StringUtil.isEmptyString(searchVo.getRewardItemId())) {
			hql.append(" AND win.reward.rewardItem.id = :rewardsItemId ");
			param.put("rewardsItemId", searchVo.getRewardItemId());
		}

		// 获奖时间
		if (null != searchVo.getRewardsDate()
				&& !searchVo.getRewardsDate().equals("")) {
			Date rewardsTimeBegin = DateUtil.getEarlierTimeOfThisDay(searchVo
					.getRewardsDate());
			Date rewardsTimeEnd = DateUtil.getLastTimeOfThisDay(searchVo
					.getRewardsDate());

			hql.append(" and ( win.lastModifiedAt  between :rewardsTimeBegin and :rewardsTimeEnd)");
			param.put("rewardsTimeBegin", rewardsTimeBegin);
			param.put("rewardsTimeEnd", rewardsTimeEnd);
		}

		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null
					&& searchVo.getSortingDetail().getSort() != null
					&& searchVo.getSortingDetail().getDirection() != null) {
				hql.append(" ORDER BY win."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY win.lastModifiedAt DESC ");
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
