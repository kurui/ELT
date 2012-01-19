package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.gwt.elt.client.gift.model.OrderSeacherVo;
import com.chinarewards.gwt.elt.client.gift.presenter.OrderListPresenter.OrderListDisplay;
import com.chinarewards.gwt.elt.client.gift.request.SearchOrderRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchOrderResponse;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.SortingDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrderListViewAdapter extends BaseDataProvider<OrderSeacherVo> {

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
		// List<GiftClient> list = new ArrayList<GiftClient>();
		// for (int i = start; i < start + length; i++) {
		// GiftClient item = new GiftClient();
		// item.setId("id" + i);
		// item.setName("gift" + i);
		// item.setSource("来源"+i);
		// item.setStatus(GiftStatus.SHELF);
		// list.add(item);
		// }
		//
		// updateRowData(start, list);
		// updateRowCount(100, true);
		// } else {
		PaginationDetail pagination = new PaginationDetail();
		pagination.setStart(start);
		pagination.setLimit(length);
		criteria.setPaginationDetail(pagination);
		if (getSorting() != null) {
			SortingDetail sortingDetail = new SortingDetail();
			SortingDetailClient sortingDetailClient=getSorting();
			sortingDetail.setSort(sortingDetailClient.getSort());//把客户端的转成model的排序
			sortingDetail.setDirection(sortingDetailClient.getDirection());
			criteria.setSortingDetail(sortingDetail);
		}
		dispatch.execute(new SearchOrderRequest(criteria, sessionManager.getSession()),
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
