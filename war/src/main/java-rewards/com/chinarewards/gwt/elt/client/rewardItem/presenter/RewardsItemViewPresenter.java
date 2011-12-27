package com.chinarewards.gwt.elt.client.rewardItem.presenter;


import java.util.Date;
import java.util.List;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsBaseInfo;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public interface RewardsItemViewPresenter extends Presenter<RewardsItemViewPresenter.RewardsItemViewDisplay> {
	//void initRewardsItemOrRewardsTemplate(Object item);

     void initInstanceId(String instanceId,RewardsItemClient item);
	
	//void checkIsAmountRoleLevel();
	//public ParticipateInfoClient getNominateInfo();
	public static interface RewardsItemViewDisplay extends Display {

		public HasValue<String> getRewardsName();
	
		public HasValue<String> getDefinition();

		public HasValue<String> getStandard();
    
		public Integer getTmdays();//预期提名的天数
		
		public Integer getTmday();//周期性中用一的下次提名的天数

		public HasValue<Date> getStartTime();
        
		public Integer getTotalJF();
		public String getRewardsType();
		public HasValue<Date> getNextPublishTime();
		public HasValue<Date> getNextRewardsTime();
		public HasValue<Date> getExpectTime();
		public Integer getRewardsFrom();
		public HasValue<Boolean> getEnableCbx();
		public HasValue<Boolean> getAutoCbx();
		public FrequencyClient getFrequencyObj();

		/**
		 * 选择员工模块
		 * 
		 * @param w
		 */
		public void initStaffBlock(Widget w);

		public String getRewardsUnit();
	
		public HasValue<Boolean> getSpecialCbx();

		public HasValue<Boolean> getBirthRadio();

			
		// 获奖人数
		public HasValue<String> getPeopleSizeLimit();
		
		// 显示奖项
		public void showRewardsItem(RewardsItemClient rewardsItem);

	
		// 显示频率
		public void showFrequencyInfo(FrequencyClient frequency);

		// 显示提名人
		void showJudgeInfo(RewardsItemClient info);
		
		public void setNextRewardsTimeVisible(boolean visible);
		
		CheckBox getAutoCbxElement();
		SpecialTextArea<OrganicationClient> getSpecialTextArea();
			
		// 得到提名人的id
		List<String> getNominateIds();
	}
}
