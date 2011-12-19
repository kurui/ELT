package com.chinarewards.elt.service.user.impl;

import java.util.Date;
import java.util.List;

import com.chinarewards.elt.dao.org.CorporationDao;
import com.chinarewards.elt.dao.user.UserDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.user.UserStatus;
import com.chinarewards.elt.model.user.UserVo;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.util.DateUtil;
import com.google.inject.Inject;

/**
 * The implementation of {@link UserLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
public class UserLogicImpl implements UserLogic {

	/**
	 * You should ensure only one user use this name.
	 */
	public static final String DEFAULT_NAME = "_damon_4076124377";

	UserDao userDao;
	CorporationDao corporationDao;

	@Inject
	public UserLogicImpl(UserDao userDao, CorporationDao corporationDao) {
		this.userDao = userDao;
		this.corporationDao = corporationDao;
	}

	@Override
	public SysUser getDefaultUser() {
		List<SysUser> users = userDao.findUserByUserName(DEFAULT_NAME);
		SysUser user;
		if (users.isEmpty()) {
			// Create a new user.
			Date now = DateUtil.getTime();
			user = new SysUser();
			user.setUserName(DEFAULT_NAME);
			user.setStatus(UserStatus.Inactive);
			user.setCreatedAt(now);
			user.setLastModifiedAt(now);
			userDao.save(user);
		} else {
			user = users.get(0);
		}

		return user;
	}
	
	@Override
	public SysUser getDefaultUserByStaff(Staff staff) {
		List<SysUser> users = userDao.findUserByUserName(DEFAULT_NAME);
		SysUser user;
		if (users.isEmpty()) {
			// Create a new user.
			Date now = DateUtil.getTime();
			user = new SysUser();
			user.setUserName(DEFAULT_NAME);
			user.setStatus(UserStatus.Inactive);
			user.setCreatedAt(now);
			user.setLastModifiedAt(now);
			user.setStaff(staff);
			userDao.save(user);
		} else {
			user = users.get(0);
		}

		return user;
	}

	@Override
	public SysUser createUser(SysUser caller, UserVo user) {
		Date now = DateUtil.getTime();
		SysUser u = new SysUser();
		// FIXME here should check duplicate username.
		u.setUserName(user.getUsername());
		u.setPassword(user.getPassword());
		Corporation corp = corporationDao.findById(Corporation.class,
				user.getCorporationId());
		u.setCorporation(corp);
		u.setCreatedAt(now);
		u.setCreatedBy(caller);
		u.setLastModifiedAt(now);
		u.setLastModifiedBy(caller);
		userDao.save(u);
		return u;
	}
}
