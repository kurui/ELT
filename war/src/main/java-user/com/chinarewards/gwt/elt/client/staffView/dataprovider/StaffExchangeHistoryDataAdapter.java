package com.chinarewards.gwt.elt.client.staffView.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.dataprovider.BaseDataProvider;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.order.model.OrderSearchVo;
import com.chinarewards.gwt.elt.client.orderHistory.request.SearchOrderHistoryStaffRequest;
import com.chinarewards.gwt.elt.client.orderHistory.request.SearchOrderHistoryStaffResponse;
import com.chinarewards.gwt.elt.client.staffView.presenter.StaffViewPresenter.StaffViewDisplay;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StaffExchangeHistoryDataAdapter extends BaseDataProvider<OrderSearchVo> {

	final DispatchAsync dispatch;
	final OrderSearchVo criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final StaffViewDisplay display;

	public StaffExchangeHistoryDataAdapter(DispatchAsync dispatch, OrderSearchVo criteria,
			ErrorHandler errorHandler, SessionManager sessionManager, StaffViewDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display=display;
	}

	public void fetchData(final int start, final int length) {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(start);
		pagination.setLimit(length);
		criteria.setPagination(pagination);
		if (getSorting() != null) {
			criteria.setSorting(getSorting());
		}
		dispatch.execute(new SearchOrderHistoryStaffRequest(criteria, sessionManager.getSession()),
				new AsyncCallback<SearchOrderHistoryStaffResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchOrderHistoryStaffResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
						display.setDataCount(response.getTotal()+"");
					}

				});
	}

}
