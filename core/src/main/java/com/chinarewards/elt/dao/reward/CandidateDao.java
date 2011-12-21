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
	public List<Candidate> findCandidatesByRewardIdandStaffIds(String rewardId,
			List<String> staffIds) {
		return getEm()
				.createQuery(
						"FROM Candidate c  WHERE c.reward.id = :rewardId and c.staff.id IN (:staffIds)")
				.setParameter("rewardId", rewardId)
				.setParameter("staffIds", staffIds).getResultList();
	}

	public int updateCandidatesNominateCount(String rewardId,
			List<String> staffIds) {
		return getEm()
				.createQuery(
						"UPDATE  Candidate c  SET c.nominatecount=c.nominatecount+1  WHERE c.reward.id = :rewardId and c.staff.id IN (:staffIds)")
				.setParameter("rewardId", rewardId)
				.setParameter("staffIds", staffIds).executeUpdate();
	}
}
