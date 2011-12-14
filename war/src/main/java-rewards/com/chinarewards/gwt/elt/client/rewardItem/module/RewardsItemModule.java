package com.chinarewards.gwt.elt.client.rewardItem.module;

import com.chinarewards.gwt.elt.client.rewardItem.presenter.ChooseStaffBlockPresenter;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.ChooseStaffBlockPresenter.ChooseStaffBlockDisplay;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.ChooseStaffBlockPresenterImpl;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.ChooseStaffWinPresenter;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.ChooseStaffWinPresenterImpl;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemCreatePresenter;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemCreatePresenter.RewardsItemDisplay;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemCreatePresenterImpl;
import com.chinarewards.gwt.elt.client.rewardItem.view.ChooseStaffBlockWidget;
import com.chinarewards.gwt.elt.client.rewardItem.view.RewardsItemWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class RewardsItemModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(RewardsItemCreatePresenter.class).to(
				RewardsItemCreatePresenterImpl.class);
		bind(RewardsItemDisplay.class).to(RewardsItemWidget.class);
		bind(ChooseStaffBlockPresenter.class).to(
				ChooseStaffBlockPresenterImpl.class);
		bind(ChooseStaffBlockDisplay.class).to(ChooseStaffBlockWidget.class);

		bind(ChooseStaffWinPresenter.class).to(
				ChooseStaffWinPresenterImpl.class);
	}

}
