package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.model.OrderSeacherVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderListPresenter.OrderListDisplay;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrderListViewAdapter extends BaseDataProvider<OrderVo> {

	final DispatchAsync dispatch;
	final OrderSeacherVo criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final OrderListDisplay display;

	public OrderListViewAdapter(DispatchAsync dispatch, OrderSeacherVo criteria,
			ErrorHandler errorHandler, SessionManager sessionManager, OrderListDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display=display;
	}

	public void fetchData(final int start, final int length) {
		// if (!GWT.isScript()) {
		// List<OrderClient> list = new ArrayList<OrderClient>();
		// for (int i = start; i < start + length; i++) {
		// OrderClient item = new OrderClient();
		// item.setId("id" + i);
		// item.setSource("来源"+i);
		// item.setStatus(OrderStatus.SHELF);
		// list.add(item);
		// }
		//
		// updateRowData(start, list);
		// updateRowCount(100, true);
		// } else {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(start);
		pagination.setLimit(length);
//		criteria.setPagination(pagination);
//		if (getSorting() != null) {
//			criteria.setSorting(getSorting());
//		}
		dispatch.execute(new SearchOrderRequest(criteria, sessionManager
				.getSession().getCorporationId(), sessionManager.getSession()
				.getUserRoles(), sessionManager.getSession().getToken()),
				new AsyncCallback<SearchOrderResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchOrderResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
						display.setDataCount(response.getTotal()+"");
					}

				});
	}
	// }

}
