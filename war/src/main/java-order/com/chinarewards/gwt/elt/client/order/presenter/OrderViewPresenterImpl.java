package com.chinarewards.gwt.elt.client.order.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.plugin.OrderConstants;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderByIdRequest;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderByIdResponse;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenter;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class OrderViewPresenterImpl extends
		BasePresenter<OrderViewPresenter.OrderViewDisplay> implements
		OrderViewPresenter {
	String instanceId;// 修改时传过来的ID

	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	String orderId;
	OrderVo orderVo = new OrderVo();

	@Inject
	public OrderViewPresenterImpl(EventBus eventBus, OrderViewDisplay display,
			DispatchAsync dispatcher, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.errorHandler = errorHandler;
	}

	@Override
	public void bind() {
		registerHandler(display.getBackClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										OrderConstants.EDITOR_GIFTLIST_SEARCH,
										OrderConstants.ACTION_GIFT_LIST,
										instanceId);
					}
				}));

		registerHandler(display.getUpdateClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						orderVo.setId(orderId);
						orderVo
								.setThisAction(OrderConstants.ACTION_GIFT_EDIT);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(OrderConstants.EDITOR_GIFT_EDIT,
										OrderConstants.ACTION_GIFT_EDIT,
										orderVo);
					}
				}));
	}

	// 查看时初始化数据
	@Override
	public void initInstanceId(String instanceId, OrderVo orderVo) {
		this.instanceId = instanceId;
		this.orderVo = orderVo;
		initDataToViewOrder(orderVo, instanceId);
	}

	private void initDataToViewOrder(final OrderVo orderVo,
			final String instanceId) {
		orderId = orderVo.getId();
		dispatcher.execute(new SearchOrderByIdRequest(orderId),
				new AsyncCallback<SearchOrderByIdResponse>() {
					@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询礼品出错!");
						Platform.getInstance()
								.getEditorRegistry()
								.closeEditor(OrderConstants.EDITOR_GIFT_VIEW,
										instanceId);
					}

					@Override
					public void onSuccess(SearchOrderByIdResponse response) {
						OrderVo item = response.getOrder();
						display.showOrder(item);
					}

				});

	}

}
