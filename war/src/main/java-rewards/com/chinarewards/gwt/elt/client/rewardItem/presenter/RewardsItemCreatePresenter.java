package com.chinarewards.gwt.elt.client.rewardItem.presenter;


import java.util.Date;
import java.util.List;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsTemplateClient;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsTypeClient;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public interface RewardsItemCreatePresenter extends Presenter<RewardsItemCreatePresenter.RewardsItemDisplay> {
	//void initRewardsItemOrRewardsTemplate(Object item);

	//void initInstanceId(String instanceId);
	
	//void checkIsAmountRoleLevel();
	public static interface RewardsItemDisplay extends Display {

		public HasValue<String> getRewardsName();
	
		public HasValue<String> getDefinition();

		public HasValue<String> getStandard();
    
		public Integer getTmdays();

		public HasClickHandlers getFrequencySettingClick();

		public HasValue<Date> getStartTime();
        
		public Integer getTotalJF();
		
		
		
		public String getRewardsType();
		
		public HasValue<Date> getEndTime();
		
		public HasValue<Date> getNextPublishTime();

		public HasValue<Date> getNextRewardsTime();

		public Integer getRewardsFrom();
		
	//	public Integer getRewardsTo() ;
	//	public String getBuildDept();
		public HasValue<Boolean> getEnableCbx();
		
	

		public HasValue<Boolean> getAutoCbx();

		public HasClickHandlers getSaveClick();

		public void clear();
       
		public FrequencyClient getFrequencyObj();

		/**
		 * 选择员工模块
		 * 
		 * @param w
		 */
		public void initStaffBlock(Widget w);

		public String getRewardsUnit();

		public HasValueChangeHandlers<Date> getStartTimeChangeHandler();

		public HasValueChangeHandlers<Date> getRewardsTimeChangeHandler();

		public HasValue<Boolean> getSpecialCbx();

		public HasValue<Boolean> getBirthRadio();

		/**
		 * 选择 设立部门和入账部门
		 * 
		 * @param deptIds
		 *            设置根列表
		 */
		/** 金额规则 **/
		
			/** 基本信息 **/
		
		// 获奖人数
		public HasValue<String> getPeopleSizeLimit();
		
		// 显示奖项
	//	public void showRewardsItem(RewardsItemClient rewardsItem);

		// 显示模板
	//	public void showRewardsTemplate(RewardsTemplateClient tmeplate,boolean initDo);

		// 显示频率
		public void showFrequencyInfo(FrequencyClient frequency);

		
		
		public void setNextRewardsTimeVisible(boolean visible);
		
		/*
		 * 获取金额监听事件源
		 */
		public TextBox getRewardsFromFocus();
		

		HasValueChangeHandlers<Boolean> onetimesClick();
		HasValueChangeHandlers<Boolean> moretimesClick();
		CheckBox getAutoCbxElement();
	}
}
