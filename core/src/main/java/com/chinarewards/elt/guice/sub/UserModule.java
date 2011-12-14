package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.dao.user.SysUserDao;
import com.chinarewards.elt.service.user.IUserService;
import com.chinarewards.elt.service.user.impl.UserServiceImpl;
import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SysUserDao.class);
		bind(IUserService.class).to(UserServiceImpl.class);
	}

}