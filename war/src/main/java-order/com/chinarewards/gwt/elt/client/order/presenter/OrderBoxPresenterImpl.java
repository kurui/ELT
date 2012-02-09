package com.chinarewards.gwt.elt.client.order.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.order.model.OrderStatus;
import com.chinarewards.gwt.elt.client.order.model.OrderViewClient;
import com.chinarewards.gwt.elt.client.order.presenter.OrderBoxPresenter.OrderBoxDisplay;
import com.chinarewards.gwt.elt.client.order.request.OrderBoxRequest;
import com.chinarewards.gwt.elt.client.order.request.OrderBoxResponse;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderByIdResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.model.ChoosePanel.InitChoosePanelParam;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class OrderBoxPresenterImpl extends BasePresenter<OrderBoxDisplay>
		implements OrderBoxPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	String orderId;
	private final BreadCrumbsPresenter breadCrumbs;
	@Inject
	public OrderBoxPresenterImpl(EventBus eventBus, DispatchAsync dispatch,BreadCrumbsPresenter breadCrumbs,
			ErrorHandler errorHandler, SessionManager sessionManager,OrderBoxDisplay display, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
        this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("订单详细");
		InitChoosePanelParam initChooseParam = new InitChoosePanelParam();
		initChooseParam.setTopName("订单详细：");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		
		

	}

	

	@Override
	public void initOrderBox() {
		
		{
			//没有付积分的
			 dispatch.execute(new OrderBoxRequest(sessionManager.getSession(),OrderStatus.INITIAL+""),
					new AsyncCallback<OrderBoxResponse>() {
						@Override
						public void onFailure(Throwable arg0) {
							errorHandler.alert("查询出错!");
							
						}
						@Override
						public void onSuccess(OrderBoxResponse response) {
							
							display.setOrderNew(response.getTotal()+"");
						}

					});
			//要发货的
			 dispatch.execute(new OrderBoxRequest(sessionManager.getSession(),OrderStatus.NUSHIPMENTS+""),
					new AsyncCallback<OrderBoxResponse>() {
						@Override
						public void onFailure(Throwable arg0) {
							errorHandler.alert("查询出错!");
							
						}
						@Override
						public void onSuccess(OrderBoxResponse response) {
							
							display.setOrderSend(response.getTotal()+"");
						}

					});
		    }		
	}



}
