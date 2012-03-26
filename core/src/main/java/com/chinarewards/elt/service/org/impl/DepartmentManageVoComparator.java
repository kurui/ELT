package com.chinarewards.elt.service.org.impl;

import java.util.Comparator;

import com.chinarewards.elt.model.org.search.DepartmentManageVo;

public class DepartmentManageVoComparator implements Comparator<Object> {

	public int compare(Object arg0, Object arg1) {
		DepartmentManageVo user0 = (DepartmentManageVo) arg0;
		DepartmentManageVo user1 = (DepartmentManageVo) arg1;

		Integer id0 = Integer.valueOf(user0.getLft());
		Integer id1 = Integer.valueOf(user1.getRgt());

		int flag = id0.compareTo(id1);
		return flag;
	}

}
