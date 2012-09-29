package com.chinarewards.elt.dao.gift;

import java.util.List;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.gift.ImportGiftRawCode;

public class ImportGiftRawCodeDao extends BaseDao<ImportGiftRawCode> {

	@SuppressWarnings("unchecked")
	public List<ImportGiftRawCode> getImportGiftRawCodeByRawId(String rawId) {
		Query q = this.getEm().createQuery("SELECT isrc FROM ImportGiftRawCode isrc WHERE isrc.importGiftRaw.id = :id");
		q.setParameter("id", rawId);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ImportGiftRawCode> getImportGiftRawCodeByBatchId(String batchId) {
		Query q = this.getEm().createQuery("SELECT isrc FROM ImportGiftRawCode isrc WHERE isrc.importGiftRaw.importGiftBatch.id = :id");
		q.setParameter("id", batchId);
		return q.getResultList();
	}
	
}
