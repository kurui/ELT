package com.chinarewards.elt.service.org.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.org.CorporationDao;
import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.dao.org.OrgPolicyDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.OrgPolicy;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.DepartmentPolicyConstants;
import com.chinarewards.elt.model.org.DepartmentVo;
import com.chinarewards.elt.model.org.RewardsApprovalPolicyEnum;
import com.chinarewards.elt.model.org.exception.DepartmentDeleteException;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * The implementation of {@link DepartmentLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
public class DepartmentLogicImpl implements DepartmentLogic {

	Logger logger = LoggerFactory.getLogger(getClass());

	OrgPolicyDao organizationPolicyDao;
	DepartmentDao deptDao;
	CorporationDao corporationDao;

	@Inject
	public DepartmentLogicImpl(OrgPolicyDao organizationPolicyDao,
			DepartmentDao deptDao, CorporationDao corporationDao) {
		this.organizationPolicyDao = organizationPolicyDao;
		this.deptDao = deptDao;
		this.corporationDao = corporationDao;
	}

	@Override
	public Department findDepartmentById(String id) {
		return deptDao.findById(Department.class, id);
	}

	@Override
	public RewardsApprovalPolicyEnum getDepartmentRewardApprovalPolicy(
			String departmentId) {
		String policyKey = DepartmentPolicyConstants.REWARDS_APPROVAL_POLICY;

		// look up the record.
		OrgPolicy policyObj = organizationPolicyDao
				.findByOrganizationIdPolicyKey(departmentId, policyKey);
		logger.debug("policyObj:" + policyObj);
		RewardsApprovalPolicyEnum policy = RewardsApprovalPolicyEnum.SYSTEM_DEFAULT;
		if (policyObj != null) {
			try {
				policy = RewardsApprovalPolicyEnum
						.valueOf(policyObj.getValue());
			} catch (IllegalArgumentException e) {
			} catch (NullPointerException e) {
			}
		}
		logger.debug("policy:" + policy);
		return policy;
	}

	@Override
	public Department addDepartment(SysUser caller, DepartmentVo department) {

		Department parent;
		Corporation corporation;
		if (StringUtil.isEmptyString(department.getParentId())) {
			String corpId = department.getCorporationId();
			corporation = corporationDao.findById(Corporation.class, corpId);
			parent = getRootDepartmentOfCorporation(corpId);
		} else {
			parent = deptDao.findById(Department.class,
					department.getParentId());
			corporation = parent.getCorporation();
		}

		if (parent == null) {
			throw new IllegalArgumentException("Can not find the root parent.");
		}

		int index = parent.getRgt();
		// maintain index
		deptDao.maintainIndexAfterAddNode(index, corporation.getId());

		Date now = DateUtil.getTime();
		Department dept = new Department();
		dept.setName(department.getName());
		dept.setDescription(department.getDescription());
		dept.setParent(parent);
		dept.setCorporation(corporation);
		dept.setLft(parent.getRgt());
		dept.setRgt(parent.getRgt() + 1);
		dept.setCreatedAt(now);
		dept.setCreatedBy(caller);
		dept.setLastModifiedAt(now);
		dept.setLastModifiedBy(caller);
		deptDao.save(dept);

		return dept;
	}

	@Override
	public void deleteDepartment(String deptId)
			throws DepartmentDeleteException {
		Department department = deptDao.findById(Department.class, deptId);
		if (!isLeaf(department)) {
			throw new DepartmentDeleteException(
					"It is not a leaf node, can not delete!");
		}
		int index = department.getLft();
		String corpId = department.getCorporation().getId();
		deptDao.delete(department);

		// maintain index
		deptDao.maintainIndexAfterDeleteNode(index, corpId);
	}

	@Override
	public boolean isLeaf(Department department) {
		return department.getRgt() - department.getLft() == 1 ? true : false;
	}

	@Override
	public Department editDepartment(SysUser caller, String id,
			DepartmentVo department) {
		Date now = DateUtil.getTime();
		Department dept = deptDao.findById(Department.class, id);
		dept.setName(department.getName());
		dept.setDescription(department.getDescription());
		dept.setLastModifiedAt(now);
		dept.setLastModifiedBy(caller);
		deptDao.save(dept);

		return dept;
	}

	@Override
	public List<Department> getImmediacyChildren(String deptId) {

		return deptDao.findDepartmentsByParentId(deptId);
	}

	@Override
	public List<Department> getWholeChildren(String deptId,
			boolean containItSelf) {
		Department dept = deptDao.findById(Department.class, deptId);
		logger.debug("Prepare to search by lft={} and rgt={}", new Object[] {
				dept.getLft(), dept.getRgt() });
		List<Department> depts = deptDao.findDepartmentsByLefRgt(dept.getLft(),
				dept.getRgt());
		if (containItSelf) {
			depts.add(dept);
		}
		return depts;
	}

	@Override
	public List<String> getWholeChildrenIds(String deptId, boolean containItSelf) {
		List<String> list = new ArrayList<String>();
		List<Department> depts = getWholeChildren(deptId, containItSelf);
		for (Department dept : depts) {
			list.add(dept.getId());
		}
		return list;
	}

	@Override
	public List<Department> getImmediacyDepartmentsOfCorporation(
			String corporationId) {
		Department root = getRootDepartmentOfCorporation(corporationId);

		return deptDao.findDepartmentsByParentIdAndCoporationId(root.getId(),
				corporationId);
	}

	@Override
	public List<Department> getWholeDepartmentsOfCorporation(
			String corporationId) {
		return deptDao.findDepartmentsByCoporationId(corporationId);
	}

	@Override
	public Department getRootDepartmentOfCorporation(String corpId) {
		Department dept = deptDao.getRootDepartmentOfCorp(corpId);
		if (dept == null) {
			Corporation corp = corporationDao.findById(Corporation.class,
					corpId);
			dept = deptDao.addRootDepartment(corp);
		}
		return dept;
	}
}
