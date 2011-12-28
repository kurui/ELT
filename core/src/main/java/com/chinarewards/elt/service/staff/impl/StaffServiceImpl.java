package com.chinarewards.elt.service.staff.impl;

import javax.persistence.EntityManager;

import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.StaffVo;
import com.chinarewards.elt.model.user.UserVo;
import com.chinarewards.elt.model.vo.WinnersRecordQueryResult;
import com.chinarewards.elt.model.vo.WinnersRecordQueryVo;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.tx.service.TransactionService;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;
import com.google.inject.Inject;

public class StaffServiceImpl implements IStaffService {

	StaffDao staffDao;
	StaffLogic  staffLogic;
	UserLogic userLogic;
	TransactionService transactionService;
	private final EntityManager em;
	@Inject
	public StaffServiceImpl(StaffDao staffDao,StaffLogic  staffLogic,UserLogic userLogic,TransactionService transactionService,EntityManager em) {
		this.staffDao = staffDao;
		this.staffLogic = staffLogic;
		this.userLogic=userLogic;
		this.transactionService=transactionService;
		this.em=em;
	}

	@Override
	public String createStaff(StaffUserProcess staffProcess) {
		
		SysUser caller =userLogic.findUserById(staffProcess.getCreateUserId());
		if(!em.getTransaction().isActive())
		{
			em.getTransaction().begin();
		}
		StaffVo staffVo = new StaffVo();
		staffVo.setName(staffProcess.getName());
		staffVo.setPhone(staffProcess.getTell());
		staffVo.setEmail(staffProcess.getEmail());
		String accountId = transactionService.createNewAccount();
		staffVo.setTxAccountId(accountId);
		staffVo.setCorpId(caller.getCorporation().getId());
		staffVo.setDeptId(staffProcess.getDeptId());
		Staff staff=staffLogic.saveStaff(caller, staffVo);
		
		UserVo userVo=new UserVo();
		userVo.setCorporationId(caller.getCorporation().getId());
		userVo.setPassword(staffProcess.getPassword());
		userVo.setUsername(staffProcess.getUsername());
		userVo.setStaffId(staff.getId());
		SysUser user=userLogic.createUser(caller, userVo);
		em.getTransaction().commit();
		return user.getId();
	}
	public PageStore<WinnersRecordQueryResult> queryWinnerRecords(WinnersRecordQueryVo queryVo,String corporationId, boolean filterByAcl) {
		return staffLogic.queryWinnerRecords(queryVo, corporationId, filterByAcl);
	}

}
