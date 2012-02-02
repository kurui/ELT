package com.chinarewards.gwt.elt.client.orderHistory.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.awardShop.plugin.AwardShopListConstants;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
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

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	OrderHistoryViewClient orderVo;

	private final BreadCrumbsPresenter breadCrumbs;
	
	@Inject
	public OrderHistoryViewPresenterImpl(EventBus eventBus,
			DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager, OrderHistoryViewDisplay display,
			Win win,BreadCrumbsPresenter breadCrumbs) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs=breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("查看兑换详细");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		
		init();

	}

	private void init() {		

		display.getConfirmbutton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dispatch.execute(
						new OrderHistoryViewRequest(orderVo.getOrderId(),
								sessionManager.getSession().getToken()),
						new AsyncCallback<OrderHistoryViewResponse>() {
							@Override
							public void onFailure(Throwable e) {
								errorHandler.alert(e.getMessage());
							}

							@Override
							public void onSuccess(
									OrderHistoryViewResponse response) {
//								display.getConfirmbuttonObj().setEnabled(false);
								String rs = response.getResult();
								if ("ok".equals(rs)) {
									Platform.getInstance()
											.getEditorRegistry()
											.openEditor(
													OrderHistoryConstants.EDITOR_ORDERHISTORY_SEARCH,
													OrderHistoryConstants.EDITOR_ORDERHISTORY_SEARCH,
													orderVo);

									// 完成后..跳出订单列表
								} else {
									win.alert("支付失败!");
								}

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

	@Override
	public void initOrderHistoryView(OrderHistoryViewClient orderVo) {
		this.orderVo = orderVo;

	}

}
