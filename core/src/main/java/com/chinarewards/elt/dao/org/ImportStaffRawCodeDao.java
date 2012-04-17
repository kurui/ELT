package com.chinarewards.elt.dao.org;

import java.util.List;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.org.ImportStaffRawCode;

public class ImportStaffRawCodeDao extends BaseDao<ImportStaffRawCode> {

	@SuppressWarnings("unchecked")
	public List<ImportStaffRawCode> getImportStaffRawCodeByRawId(String rawId) {
		Query q = this.getEm().createQuery("SELECT isrc FROM ImportStaffRawCode isrc WHERE isrc.importStaffRaw.id = :id");
		q.setParameter("id", rawId);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ImportStaffRawCode> getImportStaffRawCodeByBatchId(String batchId) {
		Query q = this.getEm().createQuery("SELECT isrc FROM ImportStaffRawCode isrc WHERE isrc.importStaffRaw.importStaffBatch.id = :id");
		q.setParameter("id", batchId);
		return q.getResultList();
	}
	
}
