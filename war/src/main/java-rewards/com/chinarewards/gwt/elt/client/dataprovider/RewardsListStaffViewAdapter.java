package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.rewards.presenter.RewardsListStaffPresenter.RewardsListStaffDisplay;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsRequest;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RewardsListStaffViewAdapter extends
		BaseDataProvider<RewardsClient> {

	final DispatchAsync dispatch;
	final RewardsListStaffDisplay display;
	RewardsCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public RewardsListStaffViewAdapter(DispatchAsync dispatch,
			RewardsCriteria criteria, ErrorHandler errorHandler,
			SessionManager sessionManager, RewardsListStaffDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display = display;
	}

	public void fetchData(final int start, final int length) {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(start);
		pagination.setLimit(length);
		getCriteria().setPagination(pagination);
		if (getSorting() != null) {
			getCriteria().setSorting(getSorting());
		}
		dispatch.execute(
				new SearchRewardsRequest(getCriteria(), sessionManager
						.getSession()),
				new AsyncCallback<SearchRewardsResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchRewardsResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
					}

				});
	}

	public void setCriteria(RewardsCriteria criteria) {
		this.criteria = criteria;
	}

	private RewardsCriteria getCriteria() {
		if (criteria == null) {
			criteria = new RewardsCriteria();
		}

		return criteria;
	}
}
