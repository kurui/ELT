package com.chinarewards.elt.service.broadcast.impl;

import java.util.List;

import com.chinarewards.elt.dao.broadcast.BroadcastDao;
import com.chinarewards.elt.dao.broadcast.BroadcastingReceivingDao;
import com.chinarewards.elt.dao.broadcast.ReceivingObjectDao;
import com.chinarewards.elt.domain.information.Broadcasting;
import com.chinarewards.elt.domain.information.BroadcastingReceiving;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;
import com.chinarewards.elt.service.broadcast.BroadcastLogic;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class BroadcastLogicImpl implements BroadcastLogic {
	private final BroadcastDao broadcastDao;
	private final BroadcastingReceivingDao broadcastingReceivingDao;
	private final ReceivingObjectDao receivingObjectDao;
	@Inject
	public BroadcastLogicImpl(BroadcastDao broadcastDao,BroadcastingReceivingDao broadcastingReceivingDao,ReceivingObjectDao receivingObjectDao) {
		this.broadcastDao = broadcastDao;
		this.broadcastingReceivingDao=broadcastingReceivingDao;
		this.receivingObjectDao=receivingObjectDao;
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

	@Override
	public void deleteBroadcastReceiving(String broadcastingId) {
		List<BroadcastingReceiving> broadcastingList=broadcastingReceivingDao.findBroadcastingReceivingList(broadcastingId);
		if(broadcastingList.size()>0)
		{
			for (BroadcastingReceiving cast:broadcastingList) {
				broadcastingReceivingDao.delete(cast);
				receivingObjectDao.delete(cast.getReceiving());
			}
		}
		
	}

	@Override
	public String getMaxNumber() {
		return (Integer.parseInt(broadcastDao.getMaxNumber())+1)+"";
	}




}
