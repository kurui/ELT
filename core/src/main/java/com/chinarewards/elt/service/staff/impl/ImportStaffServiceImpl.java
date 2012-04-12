package com.chinarewards.elt.service.staff.impl;

import java.lang.reflect.InvocationTargetException;

import com.chinarewards.elt.domain.org.ImportStaffBatch;
import com.chinarewards.elt.model.staff.ImportStaffRequest;
import com.chinarewards.elt.model.staff.ImportStaffResponse;
import com.chinarewards.elt.service.exception.ImportStaffNotFoundException;
import com.chinarewards.elt.service.staff.ImportStaffLogic;
import com.chinarewards.elt.service.staff.ImportStaffService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class ImportStaffServiceImpl   implements ImportStaffService {


	ImportStaffLogic importStaffLogic;
	

	@Inject
	public ImportStaffServiceImpl(ImportStaffLogic importStaffLogic) {
		this.importStaffLogic = importStaffLogic;

	}
	
	@Override
	public ImportStaffBatch createImportStaffBatch(ImportStaffRequest request) throws ImportStaffNotFoundException {
		return importStaffLogic.createImportStaffBatch(request);
	}

	@Override
	public ImportStaffResponse finalImportOneStaff(ImportStaffRequest request) {
		return importStaffLogic.finalImportOneStaff(request);
	}

	@Override
	public ImportStaffResponse pretreatmentImportStaff(
			ImportStaffRequest request) throws ImportStaffNotFoundException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		return importStaffLogic.pretreatmentImportStaff(request);
	}

	@Override
	public ImportStaffResponse getImportStaffReport(String batchId) {
		return importStaffLogic.getImportStaffReport(batchId);
	}

	@Override
	public Long getCurrentImportedRecord(String batchId) {
		return importStaffLogic.getCurrentImportedRecord(batchId);
	}

	@Override
	public boolean removeAllInBatchById(String batchId) {
		return importStaffLogic.removeAllInBatchById(batchId);
	}

}
