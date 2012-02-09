package com.chinarewards.elt.dao.budget;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.budget.CorpBudget;

public class CorpBudgetDao extends BaseDao<CorpBudget> {

	/**
	 * @param class1
	 * @param corpid
	 * @return
	 */
	public CorpBudget findByCorpId(String corporationId) {
		String sql = "FROM CorpBudget c WHERE c.corporationId = :corporationId";
		logger.debug(" findByCorpId==" + corporationId + "--" + sql);

		List<CorpBudget> resultList = getEm().createQuery(sql)
				.setParameter("corporationId", corporationId).getResultList();
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return null;
		}

	}

}
