package com.chinarewards.gwt.elt.client.department.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.dataprovider.BaseDataProvider;
import com.chinarewards.gwt.elt.client.department.model.LeaderSearchCriteria;
import com.chinarewards.gwt.elt.client.department.request.SearchLeaderChooseRequest;
import com.chinarewards.gwt.elt.client.department.request.SearchLeaderChooseResponse;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewards.model.StaffClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LeaderChooseAsyncDataProvider extends
		BaseDataProvider<StaffClient> {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final LeaderSearchCriteria criteria;
	final boolean filterByAcl;

	public LeaderChooseAsyncDataProvider(DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			LeaderSearchCriteria criteria, boolean filterByAcl) {
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.criteria = criteria;
		this.filterByAcl = filterByAcl;
	}

	@Override
	public void fetchData(final int start, final int length) {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(start);
		pagination.setLimit(length);
		criteria.setPagination(pagination);
		criteria.setSorting(getSorting());
		dispatch.execute(
				new SearchLeaderChooseRequest(criteria, sessionManager
						.getSession(), filterByAcl),
				new AsyncCallback<SearchLeaderChooseResponse>() {

					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchLeaderChooseResponse response) {
						updateRowData(start, response.getResult().getResult());
						updateRowCount(response.getResult().getTotal(), true);
					}
				});
	}

}
