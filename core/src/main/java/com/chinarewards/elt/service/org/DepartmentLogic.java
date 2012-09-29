package com.chinarewards.elt.service.org;

import java.util.List;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.DepartmentVo;
import com.chinarewards.elt.model.org.RewardsApprovalPolicyEnum;
import com.chinarewards.elt.model.org.exception.DepartmentDeleteException;
import com.chinarewards.elt.model.org.search.DepartmentListVo;
import com.chinarewards.elt.model.org.search.DepartmentManageVo;
import com.chinarewards.elt.model.user.UserContext;

/**
 * The logic about {@link Department}
 * 
 * @author yanxin
 * @since 1.0
 */
public interface DepartmentLogic {

	/**
	 * Find department by id.
	 * 
	 * @return
	 */
	public Department findDepartmentById(String id);

	/**
	 * Add a new department.
	 * 
	 * @param caller
	 * @param department
	 * @return
	 */
	public Department saveDepartment(SysUser caller, Department department);

	/**
	 * Only supported edit the name and description.
	 * 
	 * @param caller
	 * @param id
	 * @param department
	 * @return
	 */
	public Department editDepartment(SysUser caller, String id,
			Department department);

	/**
	 * Delete the specified Department. Notice: you are only permitted to delete
	 * the leaf Department node.
	 * 
	 * @param deptId
	 * @return 
	 * @throws DepartmentDeleteException
	 */
	public String deleteDepartment(String deptId) throws DepartmentDeleteException;

	/**
	 * Check whether is a leaf Department.If use index maintain, only check rgt
	 * - lft = 1. If 1 means it is a leaf node, return true. Otherwise return
	 * false.
	 * 
	 * @param department
	 * @return
	 */
	public boolean isLeaf(Department department);





	/**
	 * Get the whole children id.
	 * 
	 * More detail see {@link #getWholeChildren(String, boolean)}
	 * 
	 * @param deptId
	 * @param containItSelf
	 * @return
	 */

	/**
	 * Get immediacy departments of the specified corporation. e.g. a
	 * corporation contains IT department and Sales department and IT department
	 * divides to Development team and test team. According to this method, you
	 * just get a list containing IT and Sales. If you want to get the whole
	 * departments, you should use
	 * {@link #getWholeDepartmentsOfCorporation(String)}
	 * 
	 * @param corporationId
	 * @return
	 */
	public List<Department> getImmediacyDepartmentsOfCorporation(
			String corporationId);

	/**
	 * Get all of the departments of the specified corporation.
	 * 
	 * @param corporationId
	 * @return
	 */
	public List<Department> getWholeDepartmentsOfCorporation(
			String corporationId);

	/**
	 * Returns the calculated rewards policy for the department. This method
	 * never returns RewardsApprovalPolicyEnum.SYSTEM_DEFAULT. By default, this
	 * method will return REQUIRED if no special configuration is placed.
	 * 
	 * @param departmentId
	 * @return
	 */
	public RewardsApprovalPolicyEnum getDepartmentRewardApprovalPolicy(
			String departmentId);

	/**
	 * Get the root department of the specified corporation.If not exist, it may
	 * create a new.
	 * 
	 * @param corpId
	 * @return
	 */
	public Department getRootDepartmentOfCorporation(String corpId);

	/**
	 * @param caller
	 * @param department
	 * @return
	 */
	public Department save(SysUser caller, Department department);

	/**
	 * @param caller
	 * @param departmentVo
	 * @return
	 */
	public PageStore<DepartmentListVo> departmentList(SysUser caller,
			DepartmentListVo departmentVo);

	/**
	 * @param corpId
	 * @return
	 */
	public List<DepartmentManageVo> getDepartmentManageList(String corpId);

	public List<DepartmentManageVo> getDepartmentLeaderList(String leaderId,String corporcationId);
	/**
	 * @param caller
	 * @param department
	 * @return
	 */
	public Department addDepartment(SysUser caller, DepartmentVo department);

	/**
	 * @param uc
	 * @param departmentIds
	 * @return
	 */
	public String mergeDepartment(UserContext uc, String departmentIds,String departmentName,String leaderId);



	
	/**
	 *获取部门.查询.key(name).去掉根部门
	 * 
	 * @param corporationId
	 * @return
	 */
	public List<Department> getDepartmentsOfCorporationAndKey(String corporationId,String key);
	/**
	 * 根据部门名称查询部门
	 * @param name
	 * @return
	 */
	public Department findDepartmentByName(String name);
	
//	public List<String> getWholeChildrenNames(String deptId, boolean containItSelf);
//	public List<Department> getWholeChildren(String deptId,
//	boolean containItSelf);
//public List<String> getWholeChildrenIds(String deptId, boolean containItSelf);
	public List<Department> getImmediacyChildren(String deptId,boolean containItSelf);
	public List<String> getImmediacyChildrenNames(String deptId,boolean containItSelf);
	public List<String> getImmediacyChildrenIds(String deptId,boolean containItSelf);

	public List<Department> getAllChildren(String deptId, boolean containItSelf);

	public List<String> getAllChildrenIds(String deptId, boolean containItSelf);
	public List<String> getAllChildrenNames(String deptId, boolean containItSelf);
	
}
