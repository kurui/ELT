package com.chinarewards.gwt.elt.client.rewardItem.presenter;

import java.util.Date;
import java.util.Map;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;

/**
 * 奖项搜索维护页面
 * 
 * @author yanxin
 * @since 0.2.0 2011-01-03
 */
public interface RewardsItemListPresenter extends
		Presenter<RewardsItemListPresenter.RewardsItemListDisplay> {

	public static interface RewardsItemListDisplay extends Display {
		/**
		 * 展示列表的panel
		 * 
		 * @return
		 */
		public Panel getDataContainer();
		
		public HasValue<String> getSearchName();
		
		public Panel getDataPager() ;
		
		public Panel getDepartmentPanel();
		
		public HasValue<Boolean> getChooseSubDepartment();
		
		public HasClickHandlers getSearchClick();
		
		public HasClickHandlers getAddBut();
		
		public HasValue<Date> getCreateTime();
		public HasValue<Date> getCreateTimeEnd();
          
		public String getStatus();
		
		public void initStatus(Map<String, String> map) ;
		
		void initialize();
		void setDataCount(String text);
		/**
		 * 选择部门
		 * @param deptIds
		 */
	//	public void showDept(List<String> deptIds);

	//	String getBuildDept();
	}
}
