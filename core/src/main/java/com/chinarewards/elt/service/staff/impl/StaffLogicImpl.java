package com.chinarewards.elt.service.staff.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.StaffVo;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.tx.model.Unit;
import com.chinarewards.elt.tx.service.TransactionService;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class StaffLogicImpl implements StaffLogic {

	Logger logger = LoggerFactory.getLogger(getClass());

	private final StaffDao staffDao;
	private final DepartmentLogic deptLogic;
	private final CorporationLogic corporationLogic;
	private final TransactionService transactionService;

	@Inject
	public StaffLogicImpl(StaffDao staffDao, DepartmentLogic deptLogic,
			CorporationLogic corporationLogic,
			TransactionService transactionService) {
		this.staffDao = staffDao;
		this.deptLogic = deptLogic;
		this.corporationLogic = corporationLogic;
		this.transactionService = transactionService;
	}

	@Override
	public List<Staff> getStaffsFromDeptId(String deptId,
			boolean includeChildren) {
		logger.debug(
				"Invoking method getStaffsFromDeptId, param[deptId={}, includeChildren={}]",
				new Object[] { deptId, includeChildren });
		if (includeChildren) {
			Department dept = deptLogic.findDepartmentById(deptId);
			String corpId = dept.getCorporation().getId();
			List<Staff> result = staffDao.findStaffsByDepartmentLftRgt(corpId,
					dept.getLft(), dept.getRgt());
			logger.debug("The gotten staff size={}", result.size());
			return result;
		} else {
			return staffDao.findStaffsByDepartmentId(deptId);
		}
	}

	@Override
	public Staff saveStaff(SysUser caller, StaffVo staff) {
		// FIXME implement it, just partly code here.
		if (StringUtil.isEmptyString(staff.getId())) {
			// Create a new staff
			Corporation corp = corporationLogic.findCorporationById(staff
					.getCorpId());
			Department dept = deptLogic.findDepartmentById(staff.getDeptId());
			Staff ff = new Staff();
			ff.setCorporation(corp);
			ff.setDepartment(dept);
			ff.setName(staff.getName());
			ff.setDescription(staff.getDescription());
			ff.setTxAccountId(staff.getTxAccountId());

			staffDao.save(ff);
			return ff;
		} else {
			// Edit
		}
		return null;
	}

	@Override
	public double getBalance(String staffId) {
		Staff staff = staffDao.findById(Staff.class, staffId);
		Unit defaultUnit = corporationLogic.getDefaultUnit(staff
				.getCorporation().getId());
		return transactionService.getBalance(staff.getTxAccountId(),
				defaultUnit.getCode());
	}
}
