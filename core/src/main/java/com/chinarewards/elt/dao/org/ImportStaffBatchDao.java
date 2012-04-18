package com.chinarewards.elt.dao.org;

import java.util.List;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.org.ImportStaffBatch;
import com.chinarewards.elt.domain.org.ImportStaffRaw;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.model.staff.ImportStaffResultType;

public class ImportStaffBatchDao extends BaseDao<ImportStaffBatch> {

	@SuppressWarnings("unchecked")
	public Long getLastImportBatch(String corporationId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT max(isb.importBatchNo) FROM ImportStaffBatch isb WHERE isb.corporation.id=:corporationId")
				.setParameter("corporationId", corporationId);
		List<Long> l = q.getResultList();
		if (l == null || l.isEmpty() || l.get(0) == null) {
			return new Long(0);
		}
		return l.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Staff> getAllStaffInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT staff FROM Staff staff WHERE staff.importStaffBatch.id = :id");
		q.setParameter("id", batchId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ImportStaffRaw> getAllStaffRawInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT isr FROM ImportStaffRaw isr WHERE isr.importStaffBatch.id = :id AND (isr.importfal=0 OR isr.importfal is null) ORDER BY isr.rowPos");
		q.setParameter("id", batchId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ImportStaffRaw> getOutstandingStaffRawInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT isr FROM ImportStaffRaw isr WHERE isr.importStaffBatch.id = :id and isr.result = :resultType AND (isr.importfal=0 OR isr.importfal is null) ORDER BY isr.rowPos");
		q.setParameter("id", batchId);
		q.setParameter("resultType", ImportStaffResultType.PENDING);
		return q.getResultList();
	}

	public Long getProcessedStaffRawInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT count(*) FROM ImportStaffRaw isr WHERE isr.importStaffBatch.id = :id and isr.result != :resultType ORDER BY isr.rowPos");
		q.setParameter("id", batchId);
		q.setParameter("resultType", ImportStaffResultType.PENDING);
		return (Long) q.getSingleResult();
	}
}
