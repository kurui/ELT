package com.chinarewards.elt.service.department;

import java.util.List;

import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.DepartmentVo;
import com.chinarewards.elt.service.common.JPATestCase;
import com.chinarewards.elt.service.helper.CorporationHelper;
import com.chinarewards.elt.service.helper.UserHelper;
import com.chinarewards.elt.service.org.DepartmentLogic;

public class DepartmentServiceTest extends JPATestCase {
	public void testRefactoringTree() {
		// need services
		DepartmentDao departmentDao = injector.getInstance(DepartmentDao.class);
		Corporation corp = CorporationHelper.getDefaultCorporation(injector);

		Department itDept = AddDept("IT部");
		Department gobalDept = AddDept("总经办");
		Department businessDept = AddDept("业务运营部");
		
		Department test11 = AddDept("test11");
		Department test12 = AddDept("test12");
		
		Department test21 = AddDept("test21");
		Department test22 = AddDept("test22");
		
		test11.setParent(itDept);
		test12.setParent(itDept);
		
		test21.setParent(test12);
		test22.setParent(test12);
		
		
		departmentDao.refactorDepartmentTree(corp.getId());

		List<Department> list = departmentDao.getAllChildrenByCorpId(corp
				.getId());
		list.add(departmentDao.getRootDepartmentOfCorp(corp.getId()));

		for (int i = 0; i < list.size(); i++) {
			Department dept = list.get(i);
			System.out.println(dept.getName() + "--" + dept.getLft() + "--"
					+ dept.getRgt());
		}

	}

	private Department AddDept(String name) {
		// require some services
		DepartmentLogic departmentLogic = injector
				.getInstance(DepartmentLogic.class);
		DepartmentVo dept = new DepartmentVo();
		dept.setName(name);
		Corporation corp = CorporationHelper.getDefaultCorporation(injector);
		dept.setCorporationId(corp.getId());
		SysUser caller = UserHelper.getDefaultUser(injector);
		Department defaultDept = departmentLogic.addDepartment(caller, dept);
		// System.out.println("lft=" + defaultDept.getLft() + ", rgt="
		// + defaultDept.getRgt());
		return defaultDept;
	}

}
