package com.chinarewards.elt.service.broadcast;

import java.util.List;

import com.chinarewards.elt.domain.information.BroadcastingReceiving;
import com.chinarewards.elt.model.broadcast.BroadcastAndReplyQueryListVo;
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
	
	
	/**
	 * 查询广播详细(包括回复)
	 * @param broadcastId
	 * @return
	 */
	public BroadcastAndReplyQueryListVo findBroadcastById(String broadcastId);
	
	
	/**
	 * 获取 发送对象数据
	 * @param broadcastingId
	 */
	public List<BroadcastingReceiving> findBroadcastReceiving(String broadcastingId);
}
