package com.chinarewards.elt.service.org.impl;

import java.util.Comparator;

import com.chinarewards.elt.domain.org.Department;

public class DepartmentComparator implements Comparator<Object> {

	public int compare(Object arg0, Object arg1) {
		Department user0 = (Department) arg0;
		Department user1 = (Department) arg1;

		Integer lft0 = Integer.valueOf(user0.getLft());
		Integer lft1 = Integer.valueOf(user1.getLft());

		int flag = lft0.compareTo(lft1);
		return flag;
	}

}
