package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListStaffPresenter.RewardsItemListStaffDisplay;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author yanrui
 */
public class RewardsItemListStaffViewAdapter extends
		BaseDataProvider<RewardsItemClient> {

	final DispatchAsync dispatch;
	RewardsItemCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final RewardsItemListStaffDisplay display;

	public RewardsItemListStaffViewAdapter(DispatchAsync dispatch,RewardsItemCriteria criteria,
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
		dispatch.execute(new SearchRewardsItemRequest(getCriteria(),
				sessionManager.getSession()),
				new AsyncCallback<SearchRewardsItemResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchRewardsItemResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
						display.setDataCount(response.getTotal() + "");
					}
				});
	}

	public void setCriteria(RewardsItemCriteria criteria) {
		this.criteria = criteria;
	}

	public RewardsItemCriteria getCriteria() {
		if (criteria == null) {
			criteria = new RewardsItemCriteria();
		}
		return criteria;
	}
}
