package com.chinarewards.elt.service.staff.impl;

import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.vo.WinnersRecordQueryResult;
import com.chinarewards.elt.model.vo.WinnersRecordQueryVo;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;
import com.google.inject.Inject;

public class StaffServiceImpl implements IStaffService {

	StaffDao staffDao;
	StaffLogic  staffLogic;
	@Inject
	public StaffServiceImpl(StaffDao staffDao,StaffLogic  staffLogic) {
		this.staffDao = staffDao;
		this.staffLogic = staffLogic;
	}

	@Override
	public String createStaff(StaffUserProcess staffProcess) {
		// validate
		Staff staff = new Staff();
		staff.setName(staffProcess.getName());
		// staff.setEmail(staffProcess.getEmail());
		// staff.setTell(staffProcess.getTell());

		// check
		// String staffid = staffDao.createStall(staff);

		// return staffid;
		return null;
	}
	public PageStore<WinnersRecordQueryResult> queryWinnerRecords(WinnersRecordQueryVo queryVo,String corporationId, boolean filterByAcl) {
		return staffLogic.queryWinnerRecords(queryVo, corporationId, filterByAcl);
	}

}
