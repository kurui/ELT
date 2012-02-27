package com.chinarewards.gwt.elt.client.message.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.dataprovider.BaseDataProvider;
import com.chinarewards.gwt.elt.client.message.model.MessageListClient;
import com.chinarewards.gwt.elt.client.message.model.MessageListCriteria;
import com.chinarewards.gwt.elt.client.message.presenter.MessageListPresenter.MessageListDisplay;
import com.chinarewards.gwt.elt.client.message.request.SearchMessageListRequest;
import com.chinarewards.gwt.elt.client.message.request.SearchMessageListResponse;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MessageListViewAdapter extends BaseDataProvider<MessageListClient> {

	final DispatchAsync dispatch;
	final MessageListDisplay display;
	MessageListCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public MessageListViewAdapter(DispatchAsync dispatch,
			MessageListCriteria criteria, ErrorHandler errorHandler,
			SessionManager sessionManager, MessageListDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display=display;
	}

	public void fetchData(final int start, final int length) {
		// if (!GWT.isScript()) {
		// List<MessageListClient> list = new ArrayList<MessageListClient>();
		// for (int i = start; i < start + length; i++) {
		// MessageListClient item = new MessageListClient();
		// item.setId("id" + i);
		// item.setName("rewards" + i);
		// //item.setStatus(MessageListStatus.TO_BE_ISSUE);
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
		dispatch.execute(new SearchMessageListRequest(getCriteria(), sessionManager
				.getSession()), new AsyncCallback<SearchMessageListResponse>() {
			@Override
			public void onFailure(Throwable e) {
				errorHandler.alert(e.getMessage());
			}

			@Override
			public void onSuccess(SearchMessageListResponse response) {
				updateRowData(start, response.getResult());
				updateRowCount(response.getTotal(), true);
				display.setDataCount(response.getTotal()+"");
			}

		});
		// }
	}


	public void setCriteria(MessageListCriteria criteria) {
		this.criteria = criteria;
	}

	private MessageListCriteria getCriteria() {
		if (criteria == null) {
			criteria = new MessageListCriteria();
		}

		return criteria;
	}
}
