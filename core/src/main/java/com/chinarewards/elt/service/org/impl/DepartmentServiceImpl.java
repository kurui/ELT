package com.chinarewards.elt.service.org.impl;

import java.util.List;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.exception.DepartmentDeleteException;
import com.chinarewards.elt.model.org.search.DepartmentListVo;
import com.chinarewards.elt.model.org.search.DepartmentManageVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.elt.service.user.UserLogic;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	private final DepartmentLogic departmentLogic;
	private final UserLogic userLogic;

	@Inject
	public DepartmentServiceImpl(DepartmentLogic departmentLogic,
			UserLogic userLogic) {
		this.userLogic = userLogic;
		this.departmentLogic = departmentLogic;

	}

	@Override
	public Department save(UserContext context, Department department) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		Department departments = departmentLogic.save(caller, department);
		return departments;
	}

	@Override
	public Department findDepartmentById(String id) {
		return departmentLogic.findDepartmentById(id);
	}

	@Override
	public String deleteDepartment(String id) throws DepartmentDeleteException {
		return departmentLogic.deleteDepartment(id);
	}

	@Override
	public PageStore<DepartmentListVo> departmentList(UserContext context,
			DepartmentListVo departmentVo) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		return departmentLogic.departmentList(caller, departmentVo);
	}

	@Override
	public List<DepartmentManageVo> getDepartmentManageList(String corporationId) {
		return departmentLogic.getDepartmentManageList(corporationId);
	}

	@Override
	public Department saveDepartment(UserContext uc, Department department) {
		SysUser caller = userLogic.findUserById(uc.getUserId());
		return departmentLogic.saveDepartment(caller, department);
	}

	@Override
	public Department getRootDepartmentOfCorporation(String corpId) {
		return departmentLogic.getRootDepartmentOfCorporation(corpId);
	}


	@Override
	public String mergeDepartment(UserContext uc, String departmentIds) {	
		return departmentLogic.mergeDepartment(uc,departmentIds);
	}

}
