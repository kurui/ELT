package com.chinarewards.elt.service.org.impl;

import java.util.List;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.exception.DepartmentDeleteException;
import com.chinarewards.elt.model.org.search.DepartmentListVo;
import com.chinarewards.elt.model.org.search.DepartmentManageVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.org.DepartmentManagerLogic;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	private final DepartmentLogic departmentLogic;
	private final DepartmentManagerLogic departmentManagerLogic;
	private final UserLogic userLogic;
	private final StaffLogic staffLogic;

	@Inject
	public DepartmentServiceImpl(DepartmentLogic departmentLogic,DepartmentManagerLogic departmentManagerLogic,
			UserLogic userLogic,StaffLogic staffLogic) {
		this.userLogic = userLogic;
		this.staffLogic=staffLogic;
		this.departmentLogic = departmentLogic;
		this.departmentManagerLogic=departmentManagerLogic;

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
	public Department saveDepartment(UserContext uc, Department department,List<String> leaderIds,List<String> preLeaderIds) {
		SysUser caller = userLogic.findUserById(uc.getUserId());
		department= departmentLogic.saveDepartment(caller, department);
		
		departmentManagerLogic.deleteManager(department.getId(), preLeaderIds);
		
		if (leaderIds!=null) {
			for (int i = 0; i < leaderIds.size(); i++) {
				String staffId=leaderIds.get(i);
				if (!StringUtil.isEmptyString(staffId)) {
					SysUser sysUser=userLogic.findUserByStaffId(staffId);
					if (sysUser==null) {
						Staff staff =staffLogic.findStaffById(staffId);
						
						throw new IllegalStateException(staff.getName()+" 没有生成账户，不能指定为部门经理");
					}
				}
				
			}
		
			
			departmentManagerLogic.createManager(department.getId(), leaderIds);
		}
	
		
		
		updateUserRoleAsEditDepartment(leaderIds, preLeaderIds);
		
		
		
		return department;
	}
	
	/**
	 * 更新部门管理员角色
	 * */
	private void updateUserRoleAsEditDepartment(List<String> leaderIds,List<String> preLeaderIds){
		if (preLeaderIds!=null) {
			for (int i = 0; i < preLeaderIds.size(); i++) {
				String staffId=preLeaderIds.get(i);
				if (!StringUtil.isEmptyString(staffId)) {
					List<Department> departmentList=departmentManagerLogic.findDepartmentsManagedByStaffId(staffId);
					if (departmentList!=null&&departmentList.size()>0) {
						//如果该staff还是其它部门的Leader，则不删其部门管理角色
					}else{
						userLogic.deleteUserRole(UserRole.DEPT_MGR.toString(),staffId);
					}
			
				}
			}			
			
		}
		
			userLogic.createUserRole(UserRole.DEPT_MGR.toString(),leaderIds);
	}

	@Override
	public Department getRootDepartmentOfCorporation(String corpId) {
		return departmentLogic.getRootDepartmentOfCorporation(corpId);
	}

	@Override
	public String mergeDepartment(UserContext uc, String departmentIds,String departmentName,String leaderId) {
		return departmentLogic.mergeDepartment(uc, departmentIds,departmentName,leaderId);
	}

	@Override
	public List<DepartmentManageVo> getDepartmentLeaderList(String leaderId,String corporcationId) {
		return departmentLogic.getDepartmentLeaderList(leaderId,corporcationId);
	}


	@Override
	public List<Department> getWholeChildren(String deptId,
			boolean containItSelf){
		return departmentLogic.getWholeChildren(deptId, containItSelf);
	}
	
	@Override
	public List<Department> getWholeDepartmentsOfCorporation(
			String corporationId){
		return departmentLogic.getWholeDepartmentsOfCorporation(corporationId);
	}
	
	@Override
	public List<String> getWholeChildrenNames(String deptId, boolean containItSelf) {
		return departmentLogic.getWholeChildrenNames(deptId, containItSelf);
	}

	@Override
	public List<Department> getDepartmentsOfCorporationAndKey(String corporationId,String key)
	{
		return departmentLogic.getDepartmentsOfCorporationAndKey(corporationId, key);
	}
	
	@Override
	public List<Staff> findManagersByDepartmentId(String deptId) {
		return departmentManagerLogic.findManagersByDepartmentId(deptId);
	}

	@Override
	public List<Department> findDepartmentsManagedByStaffId(String staffId) {
		return departmentManagerLogic.findDepartmentsManagedByStaffId(staffId);
	}

	@Override
	public List<Department> getImmediacyDepartmentsOfCorporation(
			String corporationId) {
		// TODO Auto-generated method stub
		return departmentLogic.getImmediacyDepartmentsOfCorporation(corporationId);
	}

	@Override
	public List<Department> getImmediacyChildren(String deptId) {
		// TODO Auto-generated method stub
		return departmentLogic.getImmediacyChildren(deptId);
	}
}
