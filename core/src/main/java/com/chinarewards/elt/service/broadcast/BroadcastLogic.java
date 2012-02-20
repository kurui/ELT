package com.chinarewards.elt.service.broadcast;

import com.chinarewards.elt.domain.information.Broadcasting;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListCriteria;
import com.chinarewards.elt.model.broadcast.BroadcastQueryListVo;

public interface BroadcastLogic {
	/**
	 * 创建 and 修改..广播
	 * 
	 * @param staffProcess
	 * @return
	 */
	public String createOrUpdateBroadcast(Broadcasting broadcast);

	/**
	 * 广播列表
	 * 
	 * @param broadcast
	 * @return
	 */
	public BroadcastQueryListVo queryBroadcastList(
			BroadcastQueryListCriteria criteria);

	/**
	 * 回复数+1
	 * 
	 * @return
	 */
	public void addReplyNumber(Broadcasting broadcasting);

	/**
	 * 回复数-1
	 */
	public void minusReplyNumber(Broadcasting broadcasting);

	/**
	 * 查询广播.根据ID
	 * 
	 * @param broadcastingId
	 */
	public Broadcasting findbroadcastingById(String broadcastingId);
	
	/**
	 * 删除广播
	 * 
	 * @param broadcastingId
	 */
	public void deletebroadcasting(Broadcasting broadcasting);

}
