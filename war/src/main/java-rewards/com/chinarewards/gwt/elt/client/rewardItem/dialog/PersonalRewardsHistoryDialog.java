package com.chinarewards.gwt.elt.client.rewardItem.dialog;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractDialog;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.PersonalRewardsHistoryPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class PersonalRewardsHistoryDialog extends AbstractDialog {

	final Provider<PersonalRewardsHistoryPresenter> presenterProvider;

	PersonalRewardsHistoryPresenter presenter;

	@Inject
	public PersonalRewardsHistoryDialog(
			Provider<PersonalRewardsHistoryPresenter> presenterProvider) {
		super("personalRewardsHistory", "personalRewardsHistory");
		this.presenterProvider = presenterProvider;
		presenter = presenterProvider.get();
		presenter.setDialog(this);
		init();
	}

	public void init() {
		setTitle("个人奖励历史");
		presenter.bind();
	}

	@Override
	public Widget asWidget() {
		return presenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		presenter.unbind();
		return true;
	}

	public void setStaffData(String id, String name) {
		presenter.doSearch(id, name);
	}
}
