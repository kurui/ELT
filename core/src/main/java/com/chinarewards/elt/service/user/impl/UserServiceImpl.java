package com.chinarewards.elt.service.user.impl;

import com.chinarewards.elt.model.user.UserSessionVo;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.service.user.UserService;
import com.google.inject.Inject;

public class UserServiceImpl implements UserService {
	UserLogic userLogic;
	
	@Inject
	public UserServiceImpl(UserLogic userLogic)
	{
		this.userLogic=userLogic;
	}
	@Override
	public UserSessionVo authenticate(String userName, String pwd) {
		return userLogic.findUserByNameAndPwd(userName, pwd);
	}

	@Override
	public UserSessionVo tokenVaild(String token) {
		return userLogic.tokenVaild(token);
	}

}
