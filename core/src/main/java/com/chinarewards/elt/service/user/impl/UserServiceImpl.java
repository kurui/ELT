package com.chinarewards.elt.service.user.impl;

import java.util.List;

import com.chinarewards.elt.dao.user.UserDao;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.user.UserStatus;
import com.chinarewards.elt.service.user.IUserService;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;
import com.google.inject.Inject;

public class UserServiceImpl implements IUserService {

	UserDao userDao;

	@Inject
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public SysUser authenticate(String userName, String password) {
		// validate

		// check
		List<SysUser> userList = userDao.findUserByUserName(userName);
		if (userList != null && userList.size() == 1) {
			SysUser u = userList.get(0);
			if (password.equals(u.getPassword())) {
				return u;
			}
		}

		return null;
	}
	@Override
	public String createUser(StaffUserProcess staffProcess) {
		// validate
	
		SysUser user=new SysUser();
		user.setPassword(staffProcess.getPassword());
		user.setUserName(staffProcess.getUsername());

		// check

		String userid = userDao.createUser(user);
		return userid;
	}

}
