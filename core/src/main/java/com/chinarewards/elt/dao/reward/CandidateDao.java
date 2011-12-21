package com.chinarewards.elt.dao.reward;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.reward.person.Candidate;

/**
 * Dao of {@link Candidate}
 * 
 * @author yanxin
 * @since 1.0
 */
public class CandidateDao extends BaseDao<Candidate> {

	@SuppressWarnings("unchecked")
	public List<Candidate> findCandidatesByRewardId(String rewardId) {
		return getEm()
				.createQuery("FROM Candidate c WHERE c.reward.id = :rewardId")
				.setParameter("rewardId", rewardId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Candidate> findCandidatesByRewardIdandStaffIds(String rewardId,String staffIds) {
		return getEm()
				.createQuery("FROM Candidate c WHERE c.reward.id = :rewardId and c.staff.id in (:staffids)")
				.setParameter("rewardId", rewardId).setParameter("staffids", staffIds).getResultList();
	}
}
