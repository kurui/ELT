package com.chinarewards.elt.service.staff.impl;

import java.util.List;

import com.chinarewards.elt.dao.org.CorporationDao;
import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.StaffVo;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class StaffLogicImpl implements StaffLogic {

	private final StaffDao staffDao;
	private final DepartmentDao deptDao;
	private final CorporationDao corporationDao;

	@Inject
	public StaffLogicImpl(StaffDao staffDao, DepartmentDao deptDao,
			CorporationDao corporationDao) {
		this.staffDao = staffDao;
		this.deptDao = deptDao;
		this.corporationDao = corporationDao;
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

	@Override
	public Staff saveStaff(SysUser caller, StaffVo staff) {
		// FIXME implement it, just partly code here.
		if (StringUtil.isEmptyString(staff.getId())) {
			// Create a new staff
			Corporation corp = corporationDao.findById(Corporation.class,
					staff.getCorpId());
			Department dept = deptDao.findById(Department.class,
					staff.getDeptId());
			Staff ff = new Staff();
			ff.setCorporation(corp);
			ff.setDepartment(dept);
			ff.setName(staff.getName());
			ff.setDescription(staff.getDescription());
			
			staffDao.save(ff);
			return ff;
		} else {
			// Edit
		}
		return null;
	}
}
