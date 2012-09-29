package com.chinarewards.elt.dao.gift;

import java.util.List;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.domain.gift.ImportGiftBatch;
import com.chinarewards.elt.domain.gift.ImportGiftRaw;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftResultType;

public class ImportGiftBatchDao extends BaseDao<ImportGiftBatch> {

	@SuppressWarnings("unchecked")
	public Long getLastImportBatch(String corporationId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT max(isb.importBatchNo) FROM ImportGiftBatch isb WHERE isb.corporation.id=:corporationId")
				.setParameter("corporationId", corporationId);
		List<Long> l = q.getResultList();
		if (l == null || l.isEmpty() || l.get(0) == null) {
			return new Long(0);
		}
		return l.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Gift> getAllGiftInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT Gift FROM Gift Gift WHERE Gift.importGiftBatch.id = :id");
		q.setParameter("id", batchId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ImportGiftRaw> getAllGiftRawInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT isr FROM ImportGiftRaw isr WHERE isr.importGiftBatch.id = :id AND (isr.importfal=0 OR isr.importfal is null) ORDER BY isr.rowPos");
		q.setParameter("id", batchId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ImportGiftRaw> getOutstandingGiftRawInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT isr FROM ImportGiftRaw isr WHERE isr.importGiftBatch.id = :id and isr.result = :resultType AND (isr.importfal=0 OR isr.importfal is null) ORDER BY isr.rowPos");
		q.setParameter("id", batchId);
		q.setParameter("resultType", ImportGiftResultType.PENDING);
		return q.getResultList();
	}

	public Long getProcessedGiftRawInSameBatch(String batchId) {
		Query q = this
				.getEm()
				.createQuery(
						"SELECT count(*) FROM ImportGiftRaw isr WHERE isr.importGiftBatch.id = :id and isr.result != :resultType ORDER BY isr.rowPos");
		q.setParameter("id", batchId);
		q.setParameter("resultType", ImportGiftResultType.PENDING);
		return (Long) q.getSingleResult();
	}
	/**
	 * 物理删除
	 * @param GiftId
	 * @return
	 */
	public int deleteImportGiftRawByUserId(String userId) {
		return getEmNoFlush()
				.createQuery("DELETE FROM ImportGiftBatch b WHERE b.createBy.id=:userId or b.lastUpdateBy.id=:userId ")
				.setParameter("userId", userId).executeUpdate();
	}
}
