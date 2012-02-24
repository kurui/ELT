package com.chinarewards.gwt.elt.client.rewards.presenter;


import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface RewardsListStaffPresenter extends Presenter<RewardsListStaffPresenter.RewardsListStaffDisplay> {

	public void initRewardsList(RewardPageType pageType);
	public static interface RewardsListStaffDisplay extends Display {

		public HasClickHandlers getSearchBtnClickHandlers();
		HasValue<String> getName();
		HasValue<String> getDefinition();
		void setDataCount(String text);
		
		Panel getResultPanel();
		Panel getResultpage();
		HasValue<Boolean> getNowJudge();
		
		void setBreadCrumbs(Widget breadCrumbs);

	}
}
