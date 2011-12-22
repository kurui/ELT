package com.chinarewards.elt.service.staff;

import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.vo.WinnersRecordQueryResult;
import com.chinarewards.elt.model.vo.WinnersRecordQueryVo;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;


public interface IStaffService {

	public String createStaff(StaffUserProcess staffProcess);
	
	/**
	 * 查询员工得获奖统计信息
	 * 
	 * @param queryVo
	 * @param filterByAcl
	 *            set <code>true</code> to limit data visibility by the 
	 *            caller. 
	 * @return
	 * @author roger
	 * @since 2010-12-27
	 */
	public PageStore<WinnersRecordQueryResult> queryWinnerRecords(WinnersRecordQueryVo queryVo,String corporationId, boolean filterByAcl);
}
