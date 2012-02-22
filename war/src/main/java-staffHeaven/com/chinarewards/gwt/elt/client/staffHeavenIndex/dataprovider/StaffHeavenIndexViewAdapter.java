package com.chinarewards.gwt.elt.client.staffHeavenIndex.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.dataprovider.BaseDataProvider;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexClient;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexCriteria;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.presenter.StaffHeavenIndexPresenter.StaffHeavenIndexDisplay;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.request.StaffHeavenIndexRequest;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.request.StaffHeavenIndexResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StaffHeavenIndexViewAdapter extends BaseDataProvider<StaffHeavenIndexClient> {

	final DispatchAsync dispatch;
	final StaffHeavenIndexDisplay display;
	StaffHeavenIndexCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public StaffHeavenIndexViewAdapter(DispatchAsync dispatch,
			StaffHeavenIndexCriteria criteria, ErrorHandler errorHandler,
			SessionManager sessionManager, StaffHeavenIndexDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display=display;
	}

	public void fetchData(final int start, final int length) {
		// if (!GWT.isScript()) {
		// List<StaffHeavenIndexClient> list = new ArrayList<StaffHeavenIndexClient>();
		// for (int i = start; i < start + length; i++) {
		// StaffHeavenIndexClient item = new StaffHeavenIndexClient();
		// item.setId("id" + i);
		// item.setName("rewards" + i);
		// //item.setStatus(StaffHeavenIndexStatus.TO_BE_ISSUE);
		// list.add(item);
		// }
		//
		// updateRowData(start, list);
		// updateRowCount(100, true);
		// } else {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(start);
		pagination.setLimit(length);
		getCriteria().setPagination(pagination);
		if (getSorting() != null) {
			getCriteria().setSorting(getSorting());
		}
		dispatch.execute(new StaffHeavenIndexRequest(getCriteria(), sessionManager
				.getSession()), new AsyncCallback<StaffHeavenIndexResponse>() {
			@Override
			public void onFailure(Throwable e) {
				errorHandler.alert(e.getMessage());
			}

			@Override
			public void onSuccess(StaffHeavenIndexResponse response) {
				updateRowData(start, response.getResult());
				updateRowCount(response.getTotal(), true);
				display.setDataCount(response.getTotal()+"");
			}

		});
		// }
	}


	public void setCriteria(StaffHeavenIndexCriteria criteria) {
		this.criteria = criteria;
	}

	private StaffHeavenIndexCriteria getCriteria() {
		if (criteria == null) {
			criteria = new StaffHeavenIndexCriteria();
		}

		return criteria;
	}
}
