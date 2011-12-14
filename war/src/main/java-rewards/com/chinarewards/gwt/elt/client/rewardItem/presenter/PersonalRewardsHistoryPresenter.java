package com.chinarewards.gwt.elt.client.rewardItem.presenter;

import com.chinarewards.gwt.elt.client.dataprovider.BaseDataProvider;
import com.chinarewards.gwt.elt.client.mvp.DialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;

public interface PersonalRewardsHistoryPresenter
		extends
		DialogPresenter<PersonalRewardsHistoryPresenter.PersonalRewardsHistoryDisplay> {
	void doSearch(String staffId, String staffName);

	public static interface PersonalRewardsHistoryDisplay extends Display {

		void setSearchListViewAdpter(
				BaseDataProvider<RewardsClient> listViewAdapter);

		void init(String staffName);

	}

}
