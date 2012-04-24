package com.chinarewards.elt.dao.gift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.gift.ImportGiftRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.vo.ImportGiftSearchVo;

public class ImportGiftRawDao extends BaseDao<ImportGiftRaw> {

	public PageStore<ImportGiftRaw> queryImportGiftPageAction(
			ImportGiftSearchVo searchVo) {

		logger.debug(" Process in queryGiftPageAction, searchVo:{}", searchVo);

		PageStore<ImportGiftRaw> result = new PageStore<ImportGiftRaw>();

		result.setResultList(queryGiftPageActionData(searchVo));
		result.setResultCount(queryGiftPageActionCount(searchVo));

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<ImportGiftRaw> queryGiftPageActionData(
			ImportGiftSearchVo searchVo) {
		return getGiftQuery(searchVo, SEARCH).getResultList();
	}

	public int queryGiftPageActionCount(ImportGiftSearchVo searchVo) {
		return Integer.parseInt(getGiftQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getGiftQuery(ImportGiftSearchVo searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT s FROM ImportGiftRaw s WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(s) FROM ImportGiftRaw s WHERE 1=1 ");
		}

		hql.append(" AND s.importGiftBatch.id = :bacthId ");
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
	

	public int getAllGiftRawInSameBatchCount(ImportGiftSearchVo searchVo) {
		String whereString="";
		if (searchVo.isTitlefal()) {
			whereString=" AND isr.rowPos != 1 ";
		}
		Query q = this
				.getEm()
				.createQuery(
						"SELECT count(isr) FROM ImportGiftRaw isr WHERE isr.importGiftBatch.id = :id AND (isr.importfal=0 OR isr.importfal is null) "+whereString  );
		q.setParameter("id", searchVo.getBatchId());
		

		return Integer.parseInt(q.getSingleResult().toString()) ;
	}
}
