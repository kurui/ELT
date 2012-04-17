package com.chinarewards.gwt.elt.client.staffList.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.dataprovider.BaseDataProvider;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.staffList.model.ImportStaffListClient;
import com.chinarewards.gwt.elt.client.staffList.model.ImportStaffListCriteria;
import com.chinarewards.gwt.elt.client.staffList.presenter.ImportStaffPresenter.ImportStaffDisplay;
import com.chinarewards.gwt.elt.client.staffList.request.ImportStaffListRequest;
import com.chinarewards.gwt.elt.client.staffList.request.ImportStaffListResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ImportStaffListAdapter extends BaseDataProvider<ImportStaffListClient> {

	final DispatchAsync dispatch;
	final ImportStaffDisplay display;
	ImportStaffListCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public ImportStaffListAdapter(DispatchAsync dispatch,
			ImportStaffListCriteria criteria, ErrorHandler errorHandler,
			SessionManager sessionManager, ImportStaffDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display=display;
	}

	public void fetchData(final int start, final int length) {
		// if (!GWT.isScript()) {
		// List<StaffListClient> list = new ArrayList<StaffListClient>();
		// for (int i = start; i < start + length; i++) {
		// StaffListClient item = new StaffListClient();
		// item.setId("id" + i);
		// item.setName("rewards" + i);
		// //item.setStatus(StaffListStatus.TO_BE_ISSUE);
		// list.add(item);
		// }
		//
		// updateRowData(start, list);
		// updateRowCount(100, true);
		// } else {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(0);
		pagination.setLimit(0);
		getCriteria().setPagination(pagination);
		if (getSorting() != null) {
			getCriteria().setSorting(getSorting());
		}
		dispatch.execute(new ImportStaffListRequest(getCriteria(), sessionManager
				.getSession()), new AsyncCallback<ImportStaffListResponse>() {
			@Override
			public void onFailure(Throwable e) {
				errorHandler.alert(e.getMessage());
			}

			@Override
			public void onSuccess(ImportStaffListResponse response) {
				updateRowData(start, response.getResult());
				updateRowCount(response.getTotal(), true);
				display.setDataCount(response.getTotal()+"");
			}

		});
		// }
	}


	public void setCriteria(ImportStaffListCriteria criteria) {
		this.criteria = criteria;
	}

	private ImportStaffListCriteria getCriteria() {
		if (criteria == null) {
			criteria = new ImportStaffListCriteria();
		}

		return criteria;
	}
}
