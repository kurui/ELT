package com.chinarewards.elt.service.staff;

import java.util.List;

import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.StaffVo;

/**
 * Provides some useful methods to manipulate Staff.
 * 
 * @author yanxin
 * @since 1.0
 */
public interface StaffLogic {

	/**
	 * Create or update a staff.Maybe here should create a user to the created
	 * staff.
	 * 
	 * @param caller
	 * @param staff
	 * @return
	 */
	public Staff saveStaff(SysUser caller, StaffVo staff);

	/**
	 * Get list of Staff by Department id.
	 * 
	 * @param deptId
	 * @param includeChildren
	 *            true - means it will return all the staffs containing the sub
	 *            department's. <br>
	 *            false - means just return the immediacy staffs not containing
	 *            any other sub department's.
	 * @return
	 */
	public List<Staff> getStaffsFromDeptId(String deptId,
			boolean includeChildren);

	public double getBalance(String staffId);
}
