package com.chinarewards.elt.dao.reward;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.reward.person.PreWinnerLot;
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

}
