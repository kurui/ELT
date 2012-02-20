package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.dao.broadcast.BroadcastDao;
import com.chinarewards.elt.service.broadcast.BroadcastLogic;
import com.chinarewards.elt.service.broadcast.BroadcastService;
import com.chinarewards.elt.service.broadcast.impl.BroadcastLogicImpl;
import com.chinarewards.elt.service.broadcast.impl.BroadcastServiceImpl;
import com.google.inject.AbstractModule;

public class BroadcastingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(BroadcastDao.class);
		bind(BroadcastLogic.class).to(BroadcastLogicImpl.class);
		bind(BroadcastService.class).to(BroadcastServiceImpl.class);
	}

}