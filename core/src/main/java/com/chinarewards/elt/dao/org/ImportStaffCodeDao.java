/**
 * 
 */
package com.chinarewards.elt.dao.org;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.org.ImportStaffCode;

/**
 * 
 * 
 * @author sunhongliang
 * @since 0.0.1
 */
public class ImportStaffCodeDao extends BaseDao<ImportStaffCode> {


	/**
	 * find by code
	 * @param code
	 * @return
	 */
	public ImportStaffCode findByCode(Long code) {
		Query q = getEm().createQuery(
				"FROM ImportStaffCode WHERE code = :code");
		q.setParameter("code", code);
		try {
			ImportStaffCode config = (ImportStaffCode) q.getSingleResult();
			return config;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * get all import code
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImportStaffCode> getAll() {
		return getEm()
		.createQuery(
				" FROM ImportStaffCode ic").getResultList();
	}
	
}
