package com.chinarewards.elt.dao.budget;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.budget.CorpBudget;

public class CorpBudgetDao extends BaseDao<CorpBudget> {

	/**
	 * @param class1
	 * @param corpid
	 * @return
	 */
	public CorpBudget findByCorpId(String corporationId) {
		return (CorpBudget) getEm()
				.createQuery(
						"FROM CorpBudget c WHERE c.corporationId = :corporationId")
				.setParameter("corporationId", corporationId).getSingleResult();
	}

}
