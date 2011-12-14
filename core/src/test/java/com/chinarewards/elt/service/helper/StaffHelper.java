package com.chinarewards.elt.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.StaffVo;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.tx.service.TransactionService;
import com.google.inject.Injector;

/**
 * Help us to get a list of useful {@link Staff}
 * 
 * @author yanxin
 * @since 1.0
 */
public class StaffHelper {

	private static List<Staff> staffList;

	/**
	 * Get a default list of {@link Staff}
	 * 
	 * @return
	 */
	public static List<Staff> getDefaultStaffList(Injector injector) {
		if (staffList != null)
			return staffList;
		staffList = new ArrayList<Staff>();
		// need some services
		StaffLogic staffLogic = injector.getInstance(StaffLogic.class);
		TransactionService transactionService = injector
				.getInstance(TransactionService.class);

		SysUser caller = UserHelper.getDefaultUser(injector);
		Corporation corp = CorporationHelper.getDefaultCorporation(injector);
		Department dept = DepartmentHelper.getDefaultDept(injector);

		StaffVo one = new StaffVo();
		one.setCorpId(corp.getId());
		one.setDeptId(dept.getId());
		one.setName("mervyn");
		// create a tx account
		String accountId = transactionService.createNewAccount();
		one.setTxAccountId(accountId);
		Staff sone = staffLogic.saveStaff(caller, one);
		staffList.add(sone);

		StaffVo two = new StaffVo();
		two.setCorpId(corp.getId());
		two.setDeptId(dept.getId());
		two.setName("damon");
		// create a tx account
		accountId = transactionService.createNewAccount();
		two.setTxAccountId(accountId);
		Staff stwo = staffLogic.saveStaff(caller, two);
		staffList.add(stwo);

		StaffVo three = new StaffVo();
		three.setCorpId(corp.getId());
		three.setDeptId(dept.getId());
		three.setName("rock");
		// create a tx account
		accountId = transactionService.createNewAccount();
		three.setTxAccountId(accountId);
		Staff sthree = staffLogic.saveStaff(caller, two);
		staffList.add(sthree);

		return staffList;
	}
}
