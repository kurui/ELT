package com.chinarewards.elt.dao.reward;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.reward.rule.CandidateRule;

/**
 * Dao of {@link CandidateRule}
 * 
 * @author yanxin
 * @since 1.0
 */
public class CandidateRuleDao extends BaseDao<CandidateRule> {

	public CandidateRule findCandidateRuleByRIID(String rewardItemId) {
		return (CandidateRule) getEm()
				.createQuery(
						"SELECT ri.candidateRule FROM RewardItem ri WHERE ri.id=:rewardItemId ")
				.setParameter("rewardItemId", rewardItemId).getSingleResult();
	}
}
