package com.chinarewards.elt.service.staff.impl;

import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;
import com.google.inject.Inject;

public class StaffServiceImpl implements IStaffService {

	StaffDao staffDao;

	@Inject
	public StaffServiceImpl(StaffDao staffDao) {
		this.staffDao = staffDao;
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

}
