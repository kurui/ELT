/**
 * 
 */
package com.chinarewards.elt.dao.gift;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.gift.ImportGiftCode;

/**
 * 
 * 
 * @author sunhongliang
 * @since 0.0.1
 */
public class ImportGiftCodeDao extends BaseDao<ImportGiftCode> {


	/**
	 * find by code
	 * @param code
	 * @return
	 */
	public ImportGiftCode findByCode(Long code) {
		Query q = getEm().createQuery(
				"FROM ImportGiftCode WHERE code = :code");
		q.setParameter("code", code);
		try {
			ImportGiftCode config = (ImportGiftCode) q.getSingleResult();
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
	public List<ImportGiftCode> getAll() {
		return getEm()
		.createQuery(
				" FROM ImportGiftCode ic").getResultList();
	}
	
}
