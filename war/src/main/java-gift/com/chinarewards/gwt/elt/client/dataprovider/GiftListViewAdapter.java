package com.chinarewards.gwt.elt.client.dataprovider;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.gift.request.SearchGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftResponse;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.chinarewards.gwt.elt.model.gift.GiftClient;
import com.chinarewards.gwt.elt.model.gift.GiftCriteria;
import com.chinarewards.gwt.elt.model.gift.GiftCriteria.GiftStatus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GiftListViewAdapter extends BaseDataProvider<GiftClient> {

	final DispatchAsync dispatch;
	GiftCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public GiftListViewAdapter(DispatchAsync dispatch, GiftCriteria criteria,
			ErrorHandler errorHandler, SessionManager sessionManager) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
	}

	public void fetchData(final int start, final int length) {
		if (!GWT.isScript()) {
			List<GiftClient> list = new ArrayList<GiftClient>();
			for (int i = start; i < start + length; i++) {
				GiftClient item = new GiftClient();
				item.setId("id" + i);
				item.setName("gift" + i);
				item.setSource("来源"+i);
			    item.setStatus(GiftStatus.SHELF);
				list.add(item);
			}

			updateRowData(start, list);
			updateRowCount(100, true);
		} else {
			PaginationDetailClient pagination = new PaginationDetailClient();
			pagination.setStart(start);
			pagination.setLimit(length);
			getCriteria().setPagination(pagination);
			if (getSorting() != null) {
				getCriteria().setSorting(getSorting());
			}
			dispatch.execute(new SearchGiftRequest(getCriteria(),
					sessionManager.getSession().getCorporationId(),
					sessionManager.getSession().getUserRoles()),
					new AsyncCallback<SearchGiftResponse>() {
						@Override
						public void onFailure(Throwable e) {
							errorHandler.alert(e.getMessage());
						}

						@Override
						public void onSuccess(SearchGiftResponse response) {
							updateRowData(start, response.getResult());
							updateRowCount(response.getTotal(), true);
						}

					});
		}
	}

	public void setCriteria(GiftCriteria criteria) {
		this.criteria = criteria;
	}

	private GiftCriteria getCriteria() {
		if (criteria == null) {
			criteria = new GiftCriteria();
		}

		return criteria;
	}
}
