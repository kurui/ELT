package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.common.dao.BaseDao;
import com.chinarewards.elt.common.dao.JpaBaseDao;
import com.chinarewards.elt.service.enterprise.IEnterpriseService;
import com.chinarewards.elt.service.enterprise.impl.EnterpriseServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
public class EnterpriseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(BaseDao.class).to(JpaBaseDao.class);
		bind(IEnterpriseService.class).to(EnterpriseServiceImpl.class).in(Singleton.class);
	}

}