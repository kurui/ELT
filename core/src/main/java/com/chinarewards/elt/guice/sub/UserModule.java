package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.dao.user.UserDao;
import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserDao.class);
	}

}