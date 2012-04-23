package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListClient;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListCriteria;
import com.chinarewards.gwt.elt.client.gift.presenter.ImportGiftPresenter.ImportGiftDisplay;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftListRequest;
import com.chinarewards.gwt.elt.client.gift.request.ImportGiftListResponse;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

/***
 * 
 * @author yanrui
 * @since 1.5.2
 * **/
public class ImportGiftListAdapter extends
		BaseDataProvider<ImportGiftListClient> {

	final DispatchAsync dispatch;
	final ImportGiftDisplay display;
	ImportGiftListCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public ImportGiftListAdapter(DispatchAsync dispatch,
			ImportGiftListCriteria criteria, ErrorHandler errorHandler,
			SessionManager sessionManager, ImportGiftDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display = display;
	}

	public void fetchData(final int start, final int length) {
		// if (!GWT.isScript()) {
		// List<GiftListClient> list = new ArrayList<GiftListClient>();
		// for (int i = start; i < start + length; i++) {
		// GiftListClient item = new GiftListClient();
		// item.setId("id" + i);
		// item.setName("rewards" + i);
		// //item.setStatus(GiftListStatus.TO_BE_ISSUE);
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
		dispatch.execute(new ImportGiftListRequest(getCriteria(),
				sessionManager.getSession()),
				new AsyncCallback<ImportGiftListResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(ImportGiftListResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
						display.setDataCount(response.getTotal() + "");
						display.setSelectDataCount(response.getSelectTotal()
								+ "");

					}

				});
		// }
	}

	public void setCriteria(ImportGiftListCriteria criteria) {
		this.criteria = criteria;
	}

	private ImportGiftListCriteria getCriteria() {
		if (criteria == null) {
			criteria = new ImportGiftListCriteria();
		}

		return criteria;
	}
}
