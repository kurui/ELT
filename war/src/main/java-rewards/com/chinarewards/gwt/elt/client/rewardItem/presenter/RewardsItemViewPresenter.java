package com.chinarewards.gwt.elt.client.rewardItem.presenter;


import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsBaseInfo;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;

public interface RewardsItemViewPresenter extends Presenter<RewardsItemViewPresenter.RewardsItemViewDisplay> {
	  void initInstanceId(String instanceId,RewardsItemClient item);
	
	public static interface RewardsItemViewDisplay extends Display {

				
		public HasValue<Boolean> getAutoCbx();
		public FrequencyClient getFrequencyObj();
		public HasValue<Boolean> getSpecialCbx();
		public HasValue<Boolean> getBirthRadio();
		public HasClickHandlers getBackClick() ;	
		// 显示奖项详细内容
		public void showRewardsItem(RewardsItemClient rewardsItem);
	
		// 显示频率
		public void showFrequencyInfo(FrequencyClient frequency);

		// 显示提名人
		public void showJudgeInfo(RewardsItemClient info);
		//显示候选人
		public void showParticipateInfo(RewardsBaseInfo info) ;
		
		
			
		
	}
}
