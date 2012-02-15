package com.chinarewards.elt.service.staff.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.common.LogicContext;
import com.chinarewards.elt.common.UserContextProvider;
import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.dao.org.StaffDao.QueryStaffPageActionResult;
import com.chinarewards.elt.dao.reward.WinnerDao;
import com.chinarewards.elt.dao.user.UserDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.StaffVo;
import com.chinarewards.elt.model.staff.StaffProcess;
import com.chinarewards.elt.model.staff.StaffSearchCriteria;
import com.chinarewards.elt.model.staff.StaffWinSearchCriteria;
import com.chinarewards.elt.model.staff.StaffWinVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.model.vo.StaffSearchVo;
import com.chinarewards.elt.model.vo.WinnersRecordQueryResult;
import com.chinarewards.elt.model.vo.WinnersRecordQueryVo;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.org.DepartmentManagerLogic;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.tx.model.Unit;
import com.chinarewards.elt.tx.service.TransactionService;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class StaffLogicImpl implements StaffLogic {

	Logger logger = LoggerFactory.getLogger(getClass());

	private final StaffDao staffDao;
	private final DepartmentLogic deptLogic;
	private final CorporationLogic corporationLogic;
	private final TransactionService transactionService;
	private final DepartmentDao depDao;
	private final DepartmentManagerLogic departmentManagerLogic;
	private final UserDao userDao;
	private final WinnerDao winnerDao;

	@Inject
	public StaffLogicImpl(StaffDao staffDao, DepartmentLogic deptLogic,
			CorporationLogic corporationLogic, DepartmentDao depDao,
			TransactionService transactionService,
			DepartmentManagerLogic departmentManagerLogic, UserDao userDao,WinnerDao winnerDao) {
		this.staffDao = staffDao;
		this.deptLogic = deptLogic;
		this.corporationLogic = corporationLogic;
		this.transactionService = transactionService;
		this.depDao = depDao;
		this.departmentManagerLogic = departmentManagerLogic;
		this.userDao = userDao;
		this.winnerDao=winnerDao;
	}

	@Override
	public List<Staff> getStaffsFromDeptId(String deptId,
			boolean includeChildren) {
		logger.debug(
				"Invoking method getStaffsFromDeptId, param[deptId={}, includeChildren={}]",
				new Object[] { deptId, includeChildren });
		if (includeChildren) {
			Department dept = deptLogic.findDepartmentById(deptId);
			String corpId = dept.getCorporation().getId();
			List<Staff> result = staffDao.findStaffsByDepartmentLftRgt(corpId,
					dept.getLft(), dept.getRgt());
			logger.debug("The gotten staff size={}", result.size());
			return result;
		} else {
			return staffDao.findStaffsByDepartmentId(deptId);
		}
	}

	@Override
	public Staff saveStaff(SysUser caller, StaffVo staff) {
		// FIXME implement it, just partly code here.
		if (StringUtil.isEmptyString(staff.getId())) {
			// Create a new staff
			Corporation corp = corporationLogic.findCorporationById(staff
					.getCorpId());
			Department dept = deptLogic.findDepartmentById(staff.getDeptId());
			Staff ff = new Staff();
			ff.setCorporation(corp);
			ff.setDepartment(dept);
			ff.setName(staff.getName());
			ff.setDescription(staff.getDescription());
			ff.setTxAccountId(staff.getTxAccountId());
			ff.setCreatedBy(caller);
			ff.setCreatedAt(DateUtil.getTime());
			ff.setDeleted(0);
			staffDao.save(ff);
			return ff;
		} else {
			// Edit
		}
		return null;
	}

	@Override
	public double getBalance(String staffId) {
		Staff staff = staffDao.findById(Staff.class, staffId);
		Unit defaultUnit = corporationLogic.getDefaultUnit(staff
				.getCorporation().getId());
		return transactionService.getBalance(staff.getTxAccountId(),
				defaultUnit.getCode());
	}

	public PageStore<WinnersRecordQueryResult> queryWinnerRecords(
			WinnersRecordQueryVo queryVo, String corporationId,
			boolean filterByAcl) {
		logger.debug(" Process in queryWinnerRecords method , parameter queryVO.toString:"
				+ queryVo.toString() + ", filterByAcl = " + filterByAcl);
		// Principal principal = UserContextProvider.get().getPrincipal();
		// logger.debug(" principal.toString :" + principal.printTheProperty());

		PageStore<WinnersRecordQueryResult> pageStore = new PageStore<WinnersRecordQueryResult>();

		// empty result
		PageStore<WinnersRecordQueryResult> emptyResult = new PageStore<WinnersRecordQueryResult>();
		emptyResult.setResultCount(0);
		emptyResult.setResultList(new ArrayList<WinnersRecordQueryResult>());

		if (queryVo.getSubDeptIds() != null
				&& !queryVo.getSubDeptIds().isEmpty()) {
			if (queryVo.isIncludeSubDepts()) {
				Set<String> resolvedDeptIds = new HashSet<String>();
				for (String deptId : queryVo.getSubDeptIds()) {
					Set<String> deptIdSubset = depDao.findSiblingIds(deptId,
							true);
					resolvedDeptIds.addAll(deptIdSubset);
				}
				queryVo.setSubDeptIds(new ArrayList<String>(resolvedDeptIds));
				queryVo.setIncludeSubDepts(false); // since we have resolved
				// them.
			}
		}
		// finally enforce ACL, if requested
		// cyril: limit data visible according to the logged in user.
		LogicContext ctx = UserContextProvider.get();
		if (filterByAcl) {
			if (ctx.isCallerInRole(UserRole.CORP_ADMIN)
					|| ctx.isCallerInRole(UserRole.SUB_CORP_ADMIN)) {
				// do nothing
				// XXX should be better.
			} else if (ctx.isCallerInRole(UserRole.DEPT_MGR)) {
				// filter on this
				// XXX bad
				// find out all departments (including siblings) managed
				// by the user.
				List<String> onelevelManagedDeptIds = departmentManagerLogic
						.findDepartmentIdsManagedByLoginUser();
				Set<String> managedDeptIds = new HashSet<String>();
				for (String id : onelevelManagedDeptIds) {
					managedDeptIds.addAll(depDao.findSiblingIds(id, true));
				}
				// now, strip off from the children Ids
				if (queryVo.getSubDeptIds() == null
						|| queryVo.getSubDeptIds().isEmpty()) {
					// since it is empty (no criteria given by user), we add
					// this constraint.
					queryVo.setSubDeptIds(new ArrayList<String>(managedDeptIds));
				} else {
					// criteria is given by user. we do a INTERSECT over the
					// two collections. If the result is an empty set, it
					// means what the user wants to access is all filtered
					// out, thus we return an empty result immediately.
					Set<String> userDeptIds = new HashSet<String>(
							queryVo.getSubDeptIds());
					userDeptIds.retainAll(managedDeptIds);
					if (userDeptIds.isEmpty()) {
						logger.debug("All user specified department criteria are filtered out, return empty result");
						return emptyResult;
					}
					// otherwise, update user's input criteria.
					logger.debug("Update user's input with new department IDs criteria");
					queryVo.setSubDeptIds(new ArrayList<String>(userDeptIds));
				}

			}
		}

		pageStore.setResultCount(staffDao.queryWinnerRewardsCount(queryVo,
				corporationId));
		// XXX implicit ACL found on corporation.
		List<WinnersRecordQueryResult> list = staffDao.queryWinnerRewardsData(
				queryVo, corporationId);

		// // grep the list of department IDs
		// Set<String> deptIds = new HashSet<String>();
		// for (WinnersRecordQueryResult r : list) {
		// if (r.getDepartment() != null) {
		// deptIds.add(r.getDepartment().getId());
		// }
		// }
		// // and resolve the full name, and build a map of Dept IDs <-> Dept
		// Tree
		// // Name.
		// Map<String /* dept ID */, Department> deptIdMap = new
		// Hashtable<String, Department>();
		// if (deptIds != null && !deptIds.isEmpty()) {
		// List<Department> depts = departmentLogic.findDepartmentByIds(new
		// ArrayList<String>(deptIds));
		// for (Department dept : depts) {
		// Department i = dept;
		// while (i != null) {
		// i = i.getParent();
		// }
		// deptIdMap.put(dept.getId(), dept);
		// }
		// }
		//
		// // FIXME too dirty to do!
		// for (WinnersRecordQueryResult i : list) {
		// if (i.getDepartment() != null) {
		// Department lookup = deptIdMap.get(i.getDepartment().getId());
		// i.setDepartment(lookup);
		// }
		// }

		pageStore.setResultList(list);
		return pageStore;
	}

	@Override
	public List<Staff> findStaffsByStaffIds(List<String> staffIds) {

		return staffDao.findStaffsByStaffIds(staffIds);

	}

	@Override
	public List<Staff> getStaffsFromCorporationId(String corporationId) {

		List<Staff> result = staffDao.findStaffsByCorporationId(corporationId);
		logger.debug("The gotten staff size={}", result.size());
		return result;

	}

	@Override
	public QueryStaffPageActionResult queryStaffList(
			StaffSearchCriteria criteria, UserContext context) {
		StaffSearchVo searchVo = new StaffSearchVo();
		//待添加查询条件
		if(!StringUtil.isEmptyString(criteria.getStaffNameorNo()))
			searchVo.setKeywords(criteria.getStaffNameorNo());
		if(criteria.getStaffStatus()!=null)
			searchVo.setStatus(criteria.getStaffStatus());
		if (context.getCorporationId() != null)
			searchVo.setEnterpriseId(context.getCorporationId());

		
		//处理部门管理员..进入..只查询本部门数据
		boolean fal=false;
		for (UserRole role:context.getUserRoles()) {
			if(role==UserRole.CORP_ADMIN)
			{
				fal=true;
			}
		}
		if(fal==false)
		{
			SysUser user=userDao.findUserById(context.getUserId());
			if(user!=null && user.getStaff()!=null && user.getStaff().getDepartment()!=null)
				searchVo.setDeptId(user.getStaff().getDepartment().getId());
		}
		return staffDao.queryStaffPageAction(searchVo);
	}

	@Override
	public String createOrUpdateStaff(StaffProcess staff, UserContext context) {
		Staff ff=null;
		if (StringUtil.isEmptyString(staff.getStaffId())) {
		   	 ff = new Staff();
		}
		else
		{
			 ff = staffDao.findById(Staff.class, staff.getStaffId());
		}
		
		if (staff.getDepartmentId() != null) {
			Department dept = deptLogic.findDepartmentById(staff.getDepartmentId());
			ff.setDepartment(dept);
		}


		ff.setStatus(staff.getStatus());
		ff.setJobNo(staff.getStaffNo());
		ff.setPhoto(staff.getPhoto());
		ff.setPhone(staff.getPhone());
		ff.setJobPosition(staff.getJobPosition());
		ff.setLeadership(staff.getLeadership());
		ff.setEmail(staff.getEmail());
		ff.setDob(staff.getDob());
		ff.setName(staff.getStaffName());
		// ff.setTxAccountId(staff.getTxAccountId());---放到激活账户在做
		
		
		if (StringUtil.isEmptyString(staff.getStaffId())) {
			// Create a new staff
			if (context.getCorporationId() != null) {
				Corporation corp = corporationLogic.findCorporationById(context
						.getCorporationId());
				ff.setCorporation(corp);
			}
			if (context.getUserId() != null) {
				ff.setCreatedBy(userDao.findUserById(context.getUserId()));
			}
			ff.setCreatedAt(DateUtil.getTime());
			ff.setDeleted(0);
			staffDao.save(ff);
		} else {
			ff.setId(staff.getStaffId());
			ff.setLastModifiedAt(DateUtil.getTime());
			if (context.getUserId() != null) {
				ff.setLastModifiedBy(userDao.findUserById(context.getUserId()));
			}

			staffDao.update(ff);
		}

		return ff.getId();
	}

	@Override
	public Staff findStaffById(String staffId) {
		return staffDao.findById(Staff.class, staffId);
	}

	@Override
	public StaffWinVo findStaffWinReward(StaffWinSearchCriteria criteria) {
		return winnerDao.queryStaffWinRewardPageAction(criteria);
	}
}
