package com.chinarewards.elt.service.staff;

import com.chinarewards.elt.dao.org.StaffDao.QueryStaffPageActionResult;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.staff.StaffSearchCriteria;
import com.chinarewards.elt.model.user.UserContext;
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
	
	/**
	 * 获取员工账户积分数值
	 * @param staffId
	 * @return
	 */
	public double getBalance(String staffId);
	/**
	 * 查询员工列表
	 * @param criteria
	 * @param context
	 * @return
	 */
	public QueryStaffPageActionResult queryStaffList(StaffSearchCriteria criteria,UserContext context);
	
}
