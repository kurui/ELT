package com.chinarewards.elt.service.staff.impl;

import java.util.List;

import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.staff.Staff;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.google.inject.Inject;

public class StaffLogicImpl implements StaffLogic {

	private final StaffDao staffDao;
	private final DepartmentDao deptDao;

	@Inject
	public StaffLogicImpl(StaffDao staffDao, DepartmentDao deptDao) {
		this.staffDao = staffDao;
		this.deptDao = deptDao;
	}

	@Override
	public List<Staff> getStaffsFromDeptId(String deptId,
			boolean includeChildren) {
		if (includeChildren) {
			Department dept = deptDao.findById(Department.class, deptId);
			String corpId = dept.getCorporation().getId();
			return staffDao.findStaffsByDepartmentLftRgt(corpId, dept.getLft(),
					dept.getRgt());
		} else {
			return staffDao.findStaffsByDepartmentId(deptId);
		}
	}
}
