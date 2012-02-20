package com.chinarewards.elt.service.broadcast.impl;

import com.chinarewards.elt.dao.broadcast.BroadcastDao;
import com.chinarewards.elt.domain.information.Broadcasting;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;
import com.chinarewards.elt.service.broadcast.BroadcastLogic;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class BroadcastLogicImpl implements BroadcastLogic {
	private final BroadcastDao broadcastDao;

	@Inject
	public BroadcastLogicImpl(BroadcastDao broadcastDao) {
		this.broadcastDao = broadcastDao;
	}

	@Override
	public Broadcasting createOrUpdateBroadcast(Broadcasting broadcast) {
		if (StringUtil.isEmptyString(broadcast.getId())) {
			return broadcastDao.save(broadcast);
		} else {
			return broadcastDao.update(broadcast);
		}
	}

	@Override
	public BroadcastQueryListVo queryBroadcastList(
			BroadcastQueryListCriteria criteria) {
		return broadcastDao.queryBroadcastPageAction(criteria);
	}

	@Override
	public void addReplyNumber(Broadcasting broadcasting) {
		broadcasting.setReplyNumber(broadcasting.getReplyNumber()+1);
		broadcastDao.update(broadcasting);
		
	}

	@Override
	public void minusReplyNumber(Broadcasting broadcasting) {
		broadcasting.setReplyNumber(broadcasting.getReplyNumber()-1);
		broadcastDao.update(broadcasting);	
	}

	@Override
	public Broadcasting findbroadcastingById(String broadcastingId) {
		return broadcastDao.findById(Broadcasting.class, broadcastingId);

	}

	@Override
	public void deletebroadcasting(Broadcasting broadcasting) {
		broadcastDao.delete(broadcasting);
	}




}
