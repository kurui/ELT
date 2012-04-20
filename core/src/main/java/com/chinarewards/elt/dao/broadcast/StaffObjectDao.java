package com.chinarewards.elt.dao.broadcast;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.information.StaffObject;

public class StaffObjectDao  extends BaseDao<StaffObject>{
	/**
	 * 物理删除
	 * @param staffId
	 * @return
	 */
	public int deleteStaffObjectByStaffId(String staffId) {
		return getEmNoFlush()
				.createQuery("DELETE FROM StaffObject win WHERE win.staff.id=:staffId ")
				.setParameter("staffId", staffId).executeUpdate();
	}
}
