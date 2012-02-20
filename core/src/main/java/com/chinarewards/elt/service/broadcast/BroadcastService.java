package com.chinarewards.elt.service.broadcast;

import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;
import com.chinarewards.elt.model.broadcast.BroadcastingVo;
import com.chinarewards.elt.model.user.UserContext;

public interface BroadcastService {
	/**
	 * 广播列表
	 * 
	 * @param broadcast
	 * @return
	 */
	public BroadcastQueryListVo queryBroadcastList(
			BroadcastQueryListCriteria criteria);
	
	/**
	 * 创建 and 修改..广播
	 * 
	 * @param staffProcess
	 * @return
	 */
	public String createOrUpdateBroadcast(BroadcastingVo broadcast,UserContext context);
}
