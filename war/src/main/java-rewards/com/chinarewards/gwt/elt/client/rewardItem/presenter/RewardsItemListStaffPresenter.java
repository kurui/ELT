package com.chinarewards.gwt.elt.client.rewardItem.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface RewardsItemListStaffPresenter extends
		Presenter<RewardsItemListStaffPresenter.RewardsItemListStaffDisplay> {

	public void initRewardsItemList(RewardPageType pageType);

	public static interface RewardsItemListStaffDisplay extends Display {

		public HasClickHandlers getSearchBtnClickHandlers();

		public HasValue<String> getName();

		public HasValue<String> getDefinition();

		public Panel getResultPanel();

		public Panel getResultpage();

		public void setDataCount(String text);

		public void setBreadCrumbs(Widget breadCrumbs);

	}
}
