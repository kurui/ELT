package com.chinarewards.elt.dao.org;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.staff.Staff;

/**
 * Dao of {@link Staff}
 * 
 * @author yanxin
 * @since 1.0
 */
public class StaffDao extends BaseDao<Staff> {

	/**
	 * Find list of staff by id of department.
	 * 
	 * @param deptId
	 */
	@SuppressWarnings("unchecked")
	public List<Staff> findStaffsByDepartmentId(String deptId) {
		return getEm()
				.createQuery("FROM Staff s WHERE s.department.id =:deptId")
				.setParameter("deptId", deptId).getResultList();
	}

	/**
	 * Find list of staff by corporation id and index about lft/rgt.
	 * 
	 * @param corpId
	 * @param lft
	 * @param rgt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Staff> findStaffsByDepartmentLftRgt(String corpId, int lft,
			int rgt) {
		return getEm()
				.createQuery(
						"FROM Staff s WHERE s.corporation.id =:corpId AND s.department.lft > :lft AND s.department < :rgt")
				.setParameter("corpId", corpId).setParameter("lft", lft)
				.setParameter("rgt", rgt).getResultList();
	}
}
