package com.chinarewards.elt.dao.budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.budget.AskBudget;
import com.chinarewards.elt.model.budget.search.AskBudgetVo;
import com.chinarewards.elt.util.StringUtil;

public class AskBudgetDao extends BaseDao<AskBudget> {
	@SuppressWarnings("unchecked")
	public List<AskBudget> AskBudgetList(
			AskBudgetVo askBudgetVo) {
		List<AskBudget> result = new ArrayList<AskBudget>();

		Query query = getFetchOrderQuery(SEARCH, askBudgetVo);

		result = query.getResultList();

		return result;
	}

	public int countAskBudget(AskBudgetVo askBudgetVo) {

		int count = 0;
		Query query = getFetchOrderQuery(COUNT, askBudgetVo);
		if (query.getSingleResult() != null)
			count = Integer.parseInt(query.getSingleResult().toString());
		
		return count;
	}

	private Query getFetchOrderQuery(String type, AskBudgetVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer eql = new StringBuffer();

		if (SEARCH.equals(type)) {
			eql.append(" SELECT d FROM AskBudget d where  d.deleted= :deleted ");
			param.put("deleted", vo.getDeleted());

		} else if (COUNT.equals(type)) {
			eql.append(" SELECT COUNT(d) FROM AskBudget d where  d.deleted= :deleted ");
			param.put("deleted", vo.getDeleted());

		}
		//是部门管理员得到本部门及子部门的数据
		if (vo.getDeptIds()!=null&& ! vo.getDeptIds().isEmpty()) {
			eql.append(" AND d.departmentId in ( :deptIds )");
			param.put("deptIds", vo.getDeptIds());
		}
		//按部门查询
		if (vo.getDepartmentId()!=null && ! vo.getDepartmentId().isEmpty()) {
			eql.append(" AND d.departmentId = :departmentId");
			param.put("departmentId", vo.getDepartmentId());
		}
		if (!StringUtil.isEmptyString(vo.getCorpBudgetId())) {
			eql.append(" AND d.corpBudgetId = :corpBudgetId ");
			param.put("corpBudgetId", vo.getCorpBudgetId());
		}
		
		if (SEARCH.equals(type)) {
			if (vo.getSortingDetail() != null) {
				eql.append(" ORDER BY d." + vo.getSortingDetail().getSort()
						+ " " + vo.getSortingDetail().getDirection());
			}
		}
		System.out.println("EQL : " + eql);
		Query query = getEm().createQuery(eql.toString());
		if (SEARCH.equals(type)) {
			if (vo.getPaginationDetail() != null) {
				int start = vo.getPaginationDetail().getStart();
				int limit = vo.getPaginationDetail().getLimit();

				query.setFirstResult(start);
				query.setMaxResults(limit);
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
