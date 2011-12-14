package com.chinarewards.elt.guice;

import javax.persistence.EntityManager;

import com.chinarewards.elt.common.BaseDao;
import com.google.inject.AbstractModule;

public class CommonModule extends AbstractModule {

	EntityManager em;

	public CommonModule(EntityManager em) {
		this.em = em;
	}

	public CommonModule() {
		
	}

	@Override
	protected void configure() {
		// install(new JpaPersistModule("elt"));
		bind(EntityManager.class).toInstance(em);
		bind(BaseDao.class);
	}

}
