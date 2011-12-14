package com.chinarewards.gwt.elt.client.rewardItem.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.elt.model.rewards.RewardsStatus;
import com.chinarewards.gwt.elt.client.dataprovider.RewardsListViewAdapter;
import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.inject.Inject;

public class PersonalRewardsHistoryPresenterImpl
		extends
		BaseDialogPresenter<PersonalRewardsHistoryPresenter.PersonalRewardsHistoryDisplay>
		implements PersonalRewardsHistoryPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	@Inject
	public PersonalRewardsHistoryPresenterImpl(EventBus eventBus,
			PersonalRewardsHistoryDisplay display, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
	}

	public void bind() {
	}

	@Override
	public void doSearch(String staffId, String staffName) {
		display.init(staffName);
		RewardsCriteria criteriaVo = new RewardsCriteria();
		// 已完成的奖励
		//criteriaVo.setStatus(RewardsStatus.REWARDED);
		criteriaVo.setStaffId(staffId);
		RewardsListViewAdapter listViewAdapter = new RewardsListViewAdapter(
				dispatch, criteriaVo, errorHandler, sessionManager);
		display.setSearchListViewAdpter(listViewAdapter);
	}
}
