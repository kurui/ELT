package com.chinarewards.elt.service.broadcast;

import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;

public interface BroadcastService {
	/**
	 * 广播列表
	 * 
	 * @param broadcast
	 * @return
	 */
	public BroadcastQueryListVo queryBroadcastList(
			BroadcastQueryListCriteria criteria);
}
