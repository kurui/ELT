package com.chinarewards.elt.service.staff.impl;

import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.dao.user.RoleDao;
import com.chinarewards.elt.dao.user.UserRoleDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.domain.user.SysUserRole;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.StaffVo;
import com.chinarewards.elt.model.user.UserVo;
import com.chinarewards.elt.model.vo.WinnersRecordQueryResult;
import com.chinarewards.elt.model.vo.WinnersRecordQueryVo;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.tx.service.TransactionService;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class StaffServiceImpl implements IStaffService {

	StaffDao staffDao;
	StaffLogic staffLogic;
	UserLogic userLogic;
	TransactionService transactionService;
	UserRoleDao userRoleDao;
	RoleDao roleDao;

	@Inject
	public StaffServiceImpl(StaffDao staffDao, StaffLogic staffLogic,
			UserLogic userLogic, TransactionService transactionService,
			UserRoleDao userRoleDao, RoleDao roleDao) {
		this.staffDao = staffDao;
		this.staffLogic = staffLogic;
		this.userLogic = userLogic;
		this.transactionService = transactionService;
		this.userRoleDao = userRoleDao;
		this.roleDao = roleDao;
	}

	@Override
	public String createStaff(StaffUserProcess staffProcess) {

		SysUser caller = userLogic.findUserById(staffProcess.getCreateUserId());
		// if (!em.getTransaction().isActive()) {
		// em.getTransaction().begin();
		// }
		StaffVo staffVo = new StaffVo();
		staffVo.setName(staffProcess.getName());
		staffVo.setPhone(staffProcess.getTell());
		staffVo.setEmail(staffProcess.getEmail());
		String accountId = transactionService.createNewAccount();
		staffVo.setTxAccountId(accountId);
		staffVo.setCorpId(caller.getCorporation().getId());
		staffVo.setDeptId(staffProcess.getDeptId());
		Staff staff = staffLogic.saveStaff(caller, staffVo);

		UserVo userVo = new UserVo();
		userVo.setCorporationId(caller.getCorporation().getId());
		userVo.setPassword(staffProcess.getPassword());
		userVo.setUsername(staffProcess.getUsername());
		userVo.setStaffId(staff.getId());
		SysUser user = userLogic.createUser(caller, userVo);

		// 添加角色...2012年2月3日 15:54:59..更新.加入角色权限---by nicho
	for (int i = 0; i < staffProcess.getRoles().size(); i++) {
		SysUserRole userRole = new SysUserRole();
		userRole.setRole(roleDao.findRoleByRoleName(staffProcess.getRoles().get(i)));
		userRole.setCreatedBy(user);
		userRole.setCreatedAt(DateUtil.getTime());
		userRole.setLastModifiedAt(DateUtil.getTime());
		userRole.setLastModifiedBy(user);
		userRole.setUser(user);
		userRoleDao.createUserRole(userRole);
	}
		

		// em.getTransaction().commit();
		return user.getId();
	}

	public PageStore<WinnersRecordQueryResult> queryWinnerRecords(
			WinnersRecordQueryVo queryVo, String corporationId,
			boolean filterByAcl) {
		return staffLogic.queryWinnerRecords(queryVo, corporationId,
				filterByAcl);
	}

	@Override
	public double getBalance(String staffId) {
		return staffLogic.getBalance(staffId);
	}

}
