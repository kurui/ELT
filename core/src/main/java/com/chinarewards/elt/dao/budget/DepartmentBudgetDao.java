package com.chinarewards.elt.dao.budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.budget.DepartmentBudget;
import com.chinarewards.elt.model.budget.search.DepartmentBudgetVo;

public class DepartmentBudgetDao extends BaseDao<DepartmentBudget> {
	@SuppressWarnings("unchecked")
	public List<DepartmentBudget> departmentBudgetList(
			DepartmentBudgetVo departmentBudgetVo) {
		List<DepartmentBudget> result = new ArrayList<DepartmentBudget>();

		Query query = getFetchOrderQuery(SEARCH, departmentBudgetVo);

		result = query.getResultList();

		return result;
	}

	public int countDepartmentBudget(DepartmentBudgetVo departmentBudgetVo) {

		int count = 0;
		Query query = getFetchOrderQuery(COUNT, departmentBudgetVo);
		if (query.getSingleResult() != null)
			count = Integer.parseInt(query.getSingleResult().toString());
		logger.debug(" finshed by Order method, result count : {}", count);
		return count;
	}

	private Query getFetchOrderQuery(String type, DepartmentBudgetVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer eql = new StringBuffer();

		if (SEARCH.equals(type)) {
			eql.append(" SELECT d FROM DepartmentBudget d where  d.deleted= :deleted ");
			param.put("deleted", vo.getDeleted());

		} else if (COUNT.equals(type)) {
			eql.append(" SELECT COUNT(d) FROM DepartmentBudget d where  d.deleted= :deleted ");
			param.put("deleted", vo.getDeleted());

		}
		if (vo.getDepartmentId() != null) {
			eql.append(" AND UPPER(d.departmentId) = :departmentId ");
			param.put("departmentId", vo.getDepartmentId());
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

	public DepartmentBudget findDepartmentBudgetByDepartmentId(
			String departmentId) {
		StringBuffer eql = new StringBuffer();
		Map<String, Object> param = new HashMap<String, Object>();
		eql.append(" SELECT d FROM DepartmentBudget d where  d.departmentId= :departmentId ");
		
		param.put("departmentId", departmentId);

		Query query = getEm().createQuery(eql.toString());
		if (param.size() > 0) {
			Set<String> key = param.keySet();
			for (String s : key) {
				query.setParameter(s, param.get(s));
			}
		}
		if (query.getResultList().size()>0)
			return (DepartmentBudget) query.getSingleResult();
		else
			return null;

	}
}
