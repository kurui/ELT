package com.chinarewards.elt.dao.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.org.ImportStaffRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.vo.ImportStaffSearchVo;

public class ImportStaffRawDao extends BaseDao<ImportStaffRaw> {

	public PageStore<ImportStaffRaw> queryImportStaffPageAction(
			ImportStaffSearchVo searchVo) {

		logger.debug(" Process in queryStaffPageAction, searchVo:{}", searchVo);

		PageStore<ImportStaffRaw> result = new PageStore<ImportStaffRaw>();

		result.setResultList(queryStaffPageActionData(searchVo));
		result.setResultCount(queryStaffPageActionCount(searchVo));

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<ImportStaffRaw> queryStaffPageActionData(
			ImportStaffSearchVo searchVo) {
		return getStaffQuery(searchVo, SEARCH).getResultList();
	}

	public int queryStaffPageActionCount(ImportStaffSearchVo searchVo) {
		return Integer.parseInt(getStaffQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getStaffQuery(ImportStaffSearchVo searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT s FROM ImportStaffRaw s WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(s) FROM ImportStaffRaw s WHERE 1=1 ");
		}

		hql.append(" AND s.importStaffBatch.id = :bacthId ");
		param.put("bacthId", searchVo.getBatchId());

		if (searchVo.isTitlefal()) {
			hql.append(" AND s.rowPos != 1 ");
		}
		if (searchVo.isImportfal()) {
			hql.append(" AND (s.importfal=0 or s.importfal is null) ");
		}
		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null
					&& searchVo.getSortingDetail().getSort() != null
					&& searchVo.getSortingDetail().getDirection() != null) {
				hql.append(" ORDER BY s."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY s.rowPos ");
			}
		}
		logger.debug(" HQL:{} ", hql);
		Query query = getEm().createQuery(hql.toString());
		if (SEARCH.equals(type)) {
			if (searchVo.getPaginationDetail() != null
					&& searchVo.getPaginationDetail().getLimit() != 0) {
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
	

	public int getAllStaffRawInSameBatchCount(ImportStaffSearchVo searchVo) {
		String whereString="";
		if (searchVo.isTitlefal()) {
			whereString=" AND isr.rowPos != 1 ";
		}
		Query q = this
				.getEm()
				.createQuery(
						"SELECT count(isr) FROM ImportStaffRaw isr WHERE isr.importStaffBatch.id = :id AND (isr.importfal=0 OR isr.importfal is null) "+whereString  );
		q.setParameter("id", searchVo.getBatchId());
		

		return Integer.parseInt(q.getSingleResult().toString()) ;
	}
}
