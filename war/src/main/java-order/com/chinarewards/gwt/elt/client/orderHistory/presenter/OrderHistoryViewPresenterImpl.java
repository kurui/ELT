package com.chinarewards.gwt.elt.client.orderHistory.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.awardShop.plugin.AwardShopListConstants;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.order.model.OrderSearchVo;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.orderHistory.module.OrderHistoryViewClient;
import com.chinarewards.gwt.elt.client.orderHistory.plugin.OrderHistoryConstants;
import com.chinarewards.gwt.elt.client.orderHistory.presenter.OrderHistoryViewPresenter.OrderHistoryViewDisplay;
import com.chinarewards.gwt.elt.client.orderHistory.request.OrderHistoryViewRequest;
import com.chinarewards.gwt.elt.client.orderHistory.request.OrderHistoryViewResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class OrderHistoryViewPresenterImpl extends
		BasePresenter<OrderHistoryViewDisplay> implements
		OrderHistoryViewPresenter {

	String orderId;

	final DispatchAsync dispatcher;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	OrderHistoryViewClient orderHistoryViewClient = new OrderHistoryViewClient();

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public OrderHistoryViewPresenterImpl(EventBus eventBus,
			DispatchAsync dispatcher, ErrorHandler errorHandler,
			SessionManager sessionManager, OrderHistoryViewDisplay display,
			Win win, BreadCrumbsPresenter breadCrumbs) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("查看兑换详细");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

		initWidget();
	}

	private void initWidget() {

		registerEvent();
	}

	private void registerEvent() {
		display.getConfirmbutton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dispatcher.execute(new OrderHistoryViewRequest(
						orderHistoryViewClient.getOrderId(), sessionManager
								.getSession().getToken()),
						new AsyncCallback<OrderHistoryViewResponse>() {
							@Override
							public void onFailure(Throwable e) {
								errorHandler.alert(e.getMessage());
							}

							@Override
							public void onSuccess(
									OrderHistoryViewResponse response) {
								// display.getConfirmbuttonObj().setEnabled(false);
								// String rs = response.getResult();
								// if ("ok".equals(rs)) {
								// Platform.getInstance()
								// .getEditorRegistry()
								// .openEditor(
								// OrderHistoryConstants.EDITOR_ORDERHISTORY_SEARCH,
								// OrderHistoryConstants.EDITOR_ORDERHISTORY_SEARCH,
								// orderHistoryViewClient);
								//
								// // 完成后..跳出订单列表
								// } else {
								// win.alert("支付失败!");
								// }

							}
						});

			}
		});

		display.getReturnbutton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								AwardShopListConstants.EDITOR_AWARDSHOPLIST_SEARCH,
								"EDITOR_AWARDSHOPLIST_SEARCH_DO_ID", null);

			}
		});

	}

	// 查看时初始化数据
	@Override
	public void initInstanceId(String instanceId, OrderSearchVo orderSearchVo) {
		// this.instanceId = instanceId;
		// this.orderHistoryViewClient = orderHistoryViewClient;
		initDataToViewOrderHistory(orderSearchVo, instanceId);
	}

	private void initDataToViewOrderHistory(final OrderSearchVo orderSearchVo,
			final String instanceId) {
		orderId = orderSearchVo.getId();
		System.out.println("initDataToViewOrderHistory===orderId:" + orderId);
		
		dispatcher.execute(new OrderHistoryViewRequest(orderId),
				new AsyncCallback<OrderHistoryViewResponse>() {
					@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询兑换历史出错!");
						Platform.getInstance()
								.getEditorRegistry()
								.closeEditor(
										OrderHistoryConstants.EDITOR_ORDERHISTORY_VIEW,
										instanceId);
					}

					@Override
					public void onSuccess(OrderHistoryViewResponse response) {
						OrderVo orderVo = response.getOrderVo();
						display.showOrderHistory(orderVo);
					}
				});
	}
	

}
