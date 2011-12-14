/**
 * 
 */
package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.dao.org.CorporationDao;
import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.dao.org.DepartmentManagerDao;
import com.chinarewards.elt.dao.org.DeptIdResolverDao;
import com.chinarewards.elt.dao.org.IndustryDao;
import com.chinarewards.elt.dao.org.OrganizationDao;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.org.OrganizationLogic;
import com.chinarewards.elt.service.org.impl.CorporationLogicImpl;
import com.chinarewards.elt.service.org.impl.DepartmentLogicImpl;
import com.chinarewards.elt.service.org.impl.OrganizationLogicImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author roger
 * @since 0.2.0 2010-12-20
 */
public class OrgModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DepartmentDao.class).in(Singleton.class);
		bind(DeptIdResolverDao.class).in(Singleton.class);
		bind(OrganizationDao.class).in(Singleton.class);
		bind(CorporationDao.class).in(Singleton.class);
		bind(IndustryDao.class).in(Singleton.class);
		bind(DepartmentManagerDao.class).in(Singleton.class);
		bind(CorporationLogic.class).to(CorporationLogicImpl.class).in(
				Singleton.class);
		bind(OrganizationLogic.class).to(OrganizationLogicImpl.class).in(
				Singleton.class);
		bind(DepartmentLogic.class).to(DepartmentLogicImpl.class);
	}

}
