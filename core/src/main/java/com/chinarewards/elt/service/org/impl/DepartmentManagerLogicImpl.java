/**
 * 
 */
package com.chinarewards.elt.service.org.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.common.Principal;
import com.chinarewards.elt.common.UserContextProvider;
import com.chinarewards.elt.dao.org.DepartmentManagerDao;
import com.chinarewards.elt.dao.user.UserDao;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.service.org.DepartmentManagerLogic;
import com.google.inject.Inject;

/**
 * @author Cream
 * @since 0.2.0
 */
public class DepartmentManagerLogicImpl implements DepartmentManagerLogic {

	Logger logger = LoggerFactory.getLogger(getClass());

	private final UserDao userDao;
	private final DepartmentManagerDao deptMgrDao;

	@Inject
	public DepartmentManagerLogicImpl(UserDao userDao,
			DepartmentManagerDao deptMgrDao) {
		this.userDao = userDao;
		this.deptMgrDao = deptMgrDao;
	}

	@Override
	public List<String> findDepartmentIdsManagedByLoginUser() {

		logger.debug(" Process in method findDepartmentIdsManagedByLoginUser.");

		Principal principal = UserContextProvider.get().getPrincipal();
		logger.debug(" principal:{}", principal.printTheProperty());

		String userId = principal.getUserId();
		SysUser user = userDao.findUserById(userId);

		// FIXME
		if (user.getStaff() == null) {
			return new ArrayList<String>();
		}
		List<String> result = deptMgrDao.findDepartmentIdsManagedByStaffId(user.getStaff().getId());

		logger.debug(" result list:{}", result);

		return result;
	}

}