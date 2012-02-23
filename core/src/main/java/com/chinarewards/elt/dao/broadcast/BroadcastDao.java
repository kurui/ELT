package com.chinarewards.elt.dao.broadcast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.information.Broadcasting;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;
import com.chinarewards.elt.util.StringUtil;

public class BroadcastDao  extends BaseDao<Broadcasting>{
	public BroadcastQueryListVo queryBroadcastPageAction(BroadcastQueryListCriteria searchVo) {


		BroadcastQueryListVo result = new BroadcastQueryListVo();

		result.setResultList(queryBroadcastPageActionData(searchVo));
		result.setTotal(queryBroadcastPageActionCount(searchVo));

		return result;
	}
   
	@SuppressWarnings("unchecked")
	private List<Broadcasting> queryBroadcastPageActionData(BroadcastQueryListCriteria searchVo) {
		return getBroadcastQuery(searchVo, SEARCH).getResultList();
	}

	private int queryBroadcastPageActionCount(BroadcastQueryListCriteria searchVo) {
		return Integer.parseInt(getBroadcastQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getBroadcastQuery(BroadcastQueryListCriteria searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT broadcast FROM Broadcasting broadcast WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(broadcast) FROM Broadcasting broadcast WHERE 1=1 ");
		}
		
		if (!StringUtil.isEmptyString(searchVo.getCorporationId())) {
			hql.append(" AND broadcast.corporation.id = :corporationId ");
			param.put("corporationId", searchVo.getCorporationId());
		}	
		if(searchVo.getStatus()!=null)
		{
			hql.append(" AND broadcast.status = :status ");
			param.put("status", searchVo.getStatus());
		}
		if(searchVo.getCategory()!=null)
		{
			hql.append(" AND broadcast.category = :category ");
			param.put("category", searchVo.getCategory());
		}
		
		
		if (!StringUtil.isEmptyString(searchVo.getCreatedByUserName())) {
			hql.append(" AND broadcast.createdBy.staff.name LIKE :createdByUserName ");
			param.put("createdByUserName", "%"+searchVo.getCreatedByUserName()+"%");
		}
		if (searchVo.getBroadcastingTimeStart()!=null && searchVo.getBroadcastingTimeEnd()!=null) {
			hql.append(" and ( broadcast.broadcastingTimeStart  between :broadcastingTimeStart and :broadcastingTimeEnd)");
			param.put("broadcastingTimeStart", searchVo.getBroadcastingTimeStart());
			param.put("broadcastingTimeEnd", searchVo.getBroadcastingTimeEnd());

		}
		
	
		hql.append(" AND broadcast.deleted = :deleted ");
		param.put("deleted", false);
	
		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null && searchVo.getSortingDetail().getSort() != null && searchVo.getSortingDetail().getDirection() != null) {
				hql.append(" ORDER BY broadcast."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY broadcast.broadcastingTimeStart DESC ");
			}
		}
		logger.debug(" HQL:{} ", hql);
		Query query = getEm().createQuery(hql.toString());
		if (SEARCH.equals(type)) {
			if (searchVo.getPaginationDetail() != null) {
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
	

	public String getMaxNumber() {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT COUNT(broadcast) FROM Broadcasting broadcast WHERE 1=1 ");
		Query query = getEm().createQuery(hql.toString());
		return query.getSingleResult().toString();
	}
}
