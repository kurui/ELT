/**
 * 
 */
package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.dao.staff.StaffDao;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.staff.impl.StaffLogicImpl;
import com.chinarewards.elt.service.staff.impl.StaffServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Staff module.
 * 
 * @author yanxin
 * @since 0.0.1
 */
public class StaffModule extends AbstractModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {

		// staff bind
		bind(StaffDao.class).in(Singleton.class);

		bind(StaffLogic.class).to(StaffLogicImpl.class).in(Singleton.class);

		bind(IStaffService.class).to(StaffServiceImpl.class)
				.in(Singleton.class);
	}

}
