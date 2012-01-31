package com.chinarewards.gwt.elt.client.orderConfirmation.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.orderConfirmation.presenter.OrderConfirmationPresenter.OrderConfirmationDisplay;
import com.chinarewards.gwt.elt.client.orderConfirmation.request.OrderConfirmationRequest;
import com.chinarewards.gwt.elt.client.orderConfirmation.request.OrderConfirmationResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class OrderConfirmationPresenterImpl extends BasePresenter<OrderConfirmationDisplay>
		implements OrderConfirmationPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	String orderId;

	@Inject
	public OrderConfirmationPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			OrderConfirmationDisplay display, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;

	}

	@Override
	public void bind() {
		init();

	}

	private void init() {
		
		dispatch.execute(new OrderConfirmationRequest(orderId),
				new AsyncCallback<OrderConfirmationResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(OrderConfirmationResponse response) {
						GiftClient client=response.getResult();
						display.getShopImage().setUrl("/imageshow?imageName="+client.getPhoto());
						display.setShopText(client.getName());
						display.setTotal(client.getIntegral()+"");
						display.setUnitprice(client.getIntegral()+"");
						display.setSource(client.getSource()+"");
						display.setNumber("1");
						
					}

				});
		
	display.getNumberChange().addChangeHandler(new ChangeHandler() {
		
		@Override
		public void onChange(ChangeEvent event) {
			try {
				int price=Integer.parseInt(display.getUnitprice());
				int num=Integer.parseInt(display.getNumber().getValue());
				display.setTotal((price*num)+"");
			} catch (Exception e) {
				display.getNumberChange().setText("0");
				display.setTotal("0");
			}

			
		}
	});
	}

	@Override
	public void initOrderConfirmation(String orderId) {
		this.orderId=orderId;
		
	}



}
