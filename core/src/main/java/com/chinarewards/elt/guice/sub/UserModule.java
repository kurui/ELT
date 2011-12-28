package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.dao.user.UserDao;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.service.user.UserService;
import com.chinarewards.elt.service.user.impl.UserLogicImpl;
import com.chinarewards.elt.service.user.impl.UserServiceImpl;
import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserDao.class);
		bind(UserLogic.class).to(UserLogicImpl.class);
		bind(UserService.class).to(UserServiceImpl.class);
	}

}