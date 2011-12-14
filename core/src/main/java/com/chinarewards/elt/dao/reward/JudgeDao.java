package com.chinarewards.elt.dao.reward;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.reward.person.Judge;

public class JudgeDao extends BaseDao<Judge> {

	/**
	 * Find list of judge by id of a RewardItem.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Judge> findJudgesFromRewardItem(String rewardItemId) {
		return getEm()
				.createQuery(
						"FROM Judge j WHERE j.rewardItem.id =:rewardItemId")
				.setParameter("rewardItemId", rewardItemId).getResultList();
	}

	/**
	 * Find Judge by staffId and rewardId.
	 * 
	 * @param staffId
	 * @param rewardId
	 * @return
	 */
	public Judge findJudgeByStaffIdAndRewardId(String staffId, String rewardId) {
		return (Judge) getEm()
				.createQuery(
						"FROM Judge j WHERE j.reward.id =:rewardId AND j.staff.id =:staffId")
				.setParameter("rewardId", rewardId)
				.setParameter("staffId", staffId).getSingleResult();
	}
}
