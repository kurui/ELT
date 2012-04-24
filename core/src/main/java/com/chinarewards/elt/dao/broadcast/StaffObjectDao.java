package com.chinarewards.elt.dao.broadcast;

import java.util.List;

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
	@SuppressWarnings("unchecked")
	public List<String> findStaffObjectByStaffId(String staffId) {
		return getEm()
				.createQuery("SELECT win.id FROM StaffObject win WHERE win.staff.id=:staffId ")
				.setParameter("staffId", staffId).getResultList();
	}
}
