package com.chinarewards.elt.service.user.impl;

import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.user.UserSearchCriteria;
import com.chinarewards.elt.model.user.UserSearchResult;
import com.chinarewards.elt.model.user.UserSessionVo;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.service.user.UserService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class UserServiceImpl implements UserService {
	UserLogic userLogic;

	@Inject
	public UserServiceImpl(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public UserSessionVo authenticate(String userName, String pwd) {
		return userLogic.findUserByNameAndPwd(userName, pwd);
	}

	@Override
	public UserSessionVo tokenVaild(String token) {
		return userLogic.tokenVaild(token);
	}

	@Override
	public UserSearchResult searchHrAdminUserPaging(UserSearchCriteria criteria) {
		return userLogic.searchHrAdminUserPaging(criteria);
	}

	public SysUser findUserById(String id) {
		return userLogic.findUserById(id);
	}

	@Override
	public String deleteUserById(String id) {
		return userLogic.deleteUserById(id);
	}
}
