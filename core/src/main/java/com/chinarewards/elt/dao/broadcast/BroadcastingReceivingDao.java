package com.chinarewards.elt.dao.broadcast;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.information.BroadcastingReceiving;

public class BroadcastingReceivingDao  extends BaseDao<BroadcastingReceiving>{
	@SuppressWarnings("unchecked")
	public List<BroadcastingReceiving> findBroadcastingReceivingList(String broadcastId) {
		return getEm()
				.createQuery("FROM BroadcastingReceiving d WHERE d.broadcast.id =:broadcastId")
				.setParameter("broadcastId", broadcastId).getResultList();
	}
}
