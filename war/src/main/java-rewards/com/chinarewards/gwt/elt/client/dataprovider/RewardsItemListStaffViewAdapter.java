package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListStaffPresenter.RewardsItemListStaffDisplay;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchStaffRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchStaffRewardsItemResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemStaffClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemStaffCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author yanrui
 */
public class RewardsItemListStaffViewAdapter extends
		BaseDataProvider<RewardsItemStaffClient> {

	final DispatchAsync dispatch;
	RewardsItemStaffCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final RewardsItemListStaffDisplay display;

	public RewardsItemListStaffViewAdapter(DispatchAsync dispatch,RewardsItemStaffCriteria criteria,
			ErrorHandler errorHandler, SessionManager sessionManager,
			RewardsItemListStaffDisplay display) {
		this.dispatch = dispatch;
		this.criteria=criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display = display;
	}

	@Override
	public void fetchData(final int start, final int length) {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(start);
		pagination.setLimit(length);
		getCriteria().setPagination(pagination);
		if (getSorting() != null) {
			getCriteria().setSorting(getSorting());
		}
		dispatch.execute(new SearchStaffRewardsItemRequest(getCriteria(),
				sessionManager.getSession()),
				new AsyncCallback<SearchStaffRewardsItemResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchStaffRewardsItemResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
						display.setDataCount(response.getTotal() + "");
					}
				});
	}

	public void setCriteria(RewardsItemStaffCriteria criteria) {
		this.criteria = criteria;
	}

	public RewardsItemStaffCriteria getCriteria() {
		if (criteria == null) {
			criteria = new RewardsItemStaffCriteria();
		}
		return criteria;
	}
}
