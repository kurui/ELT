package com.chinarewards.elt.service.broadcast.impl;

import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;
import com.chinarewards.elt.service.broadcast.BroadcastLogic;
import com.chinarewards.elt.service.broadcast.BroadcastService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
@Transactional
public class BroadcastServiceImpl implements BroadcastService{

	private final BroadcastLogic broadcastLogic;
	@Inject
	public BroadcastServiceImpl(BroadcastLogic broadcastLogic)
	{
		this.broadcastLogic=broadcastLogic;
	}
	@Override
	public BroadcastQueryListVo queryBroadcastList(
			BroadcastQueryListCriteria criteria) {
		return broadcastLogic.queryBroadcastList(criteria);
	}

}
