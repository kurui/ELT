package com.chinarewards.elt.service.gift.impl;

import java.lang.reflect.InvocationTargetException;

import com.chinarewards.elt.domain.gift.ImportGiftBatch;
import com.chinarewards.elt.domain.gift.ImportGiftRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftRequest;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftResponse;
import com.chinarewards.elt.model.vo.ImportGiftSearchVo;
import com.chinarewards.elt.service.exception.ImportGiftNotFoundException;
import com.chinarewards.elt.service.gift.ImportGiftLogic;
import com.chinarewards.elt.service.gift.ImportGiftService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class ImportGiftServiceImpl   implements ImportGiftService {


	ImportGiftLogic importGiftLogic;
	

	@Inject
	public ImportGiftServiceImpl(ImportGiftLogic importGiftLogic) {
		this.importGiftLogic = importGiftLogic;

	}
	
	@Override
	public ImportGiftBatch createImportGiftBatch(ImportGiftRequest request) throws ImportGiftNotFoundException {
		return importGiftLogic.createImportGiftBatch(request);
	}

	@Override
	public ImportGiftResponse finalImportOneGift(ImportGiftRequest request) {
		return importGiftLogic.finalImportOneGift(request);
	}

	@Override
	public ImportGiftResponse pretreatmentImportGift(
			ImportGiftRequest request) throws ImportGiftNotFoundException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		return importGiftLogic.pretreatmentImportGift(request);
	}

	@Override
	public ImportGiftResponse getImportGiftReport(String batchId) {
		return importGiftLogic.getImportGiftReport(batchId);
	}

	@Override
	public Long getCurrentImportedRecord(String batchId) {
		return importGiftLogic.getCurrentImportedRecord(batchId);
	}

	@Override
	public boolean removeAllInBatchById(String batchId) {
		return importGiftLogic.removeAllInBatchById(batchId);
	}

	@Override
	public PageStore<ImportGiftRaw> findImportGiftList(ImportGiftSearchVo searchVo) {
		return importGiftLogic.findImportGiftList(searchVo);
	}

	@Override
	public boolean updateImportfal(String rawId, int importfal) {
		return importGiftLogic.updateImportfal(rawId, importfal);
	}

	@Override
	public int findImportGiftCount(ImportGiftSearchVo searchVo) {
		return importGiftLogic.findImportGiftCount(searchVo);
	}

}
