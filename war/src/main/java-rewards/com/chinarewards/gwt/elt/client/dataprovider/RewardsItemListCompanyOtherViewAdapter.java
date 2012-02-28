package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListCompanyOtherPresenter.RewardsItemListCompanyOtherDisplay;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchCompanyOtherRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchCompanyOtherRewardsItemResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemCompanyOtherClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemCompanyOtherCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author yanrui
 */
public class RewardsItemListCompanyOtherViewAdapter extends
		BaseDataProvider<RewardsItemCompanyOtherClient> {

	final DispatchAsync dispatch;
	RewardsItemCompanyOtherCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final RewardsItemListCompanyOtherDisplay display;

	public RewardsItemListCompanyOtherViewAdapter(DispatchAsync dispatch,RewardsItemCompanyOtherCriteria criteria,
			ErrorHandler errorHandler, SessionManager sessionManager,
			RewardsItemListCompanyOtherDisplay display) {
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
		dispatch.execute(new SearchCompanyOtherRewardsItemRequest(getCriteria(),
				sessionManager.getSession()),
				new AsyncCallback<SearchCompanyOtherRewardsItemResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchCompanyOtherRewardsItemResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
						display.setDataCount(response.getTotal() + "");
					}
				});
	}

	public void setCriteria(RewardsItemCompanyOtherCriteria criteria) {
		this.criteria = criteria;
	}

	public RewardsItemCompanyOtherCriteria getCriteria() {
		if (criteria == null) {
			criteria = new RewardsItemCompanyOtherCriteria();
		}
		return criteria;
	}
}
