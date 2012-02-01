package com.chinarewards.gwt.elt.client.orderConfirmation.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.awardShop.plugin.AwardShopListConstants;
import com.chinarewards.gwt.elt.client.core.Platform;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class OrderConfirmationPresenterImpl extends BasePresenter<OrderConfirmationDisplay>
		implements OrderConfirmationPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	String orderId;
double balance;
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
		display.getMessage().setVisible(false);
		dispatch.execute(new OrderConfirmationRequest(orderId,sessionManager.getSession().getStaffId()),
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
						balance=response.getStaffBalance();
						display.setMybalance(balance+"");
						
					}

				});
		
	display.getNumberChange().addChangeHandler(new ChangeHandler() {
		
		@Override
		public void onChange(ChangeEvent event) {
			try {
				int price=Integer.parseInt(display.getUnitprice());
				int num=Integer.parseInt(display.getNumber().getValue());
				display.setTotal((price*num)+"");
				if(balance<price*num)
				{
					display.getMessage().setVisible(true);
					display.getConfirmbuttonObj().setEnabled(false);
				}
				else
				{
					display.getMessage().setVisible(false);
					display.getConfirmbuttonObj().setEnabled(true);
				}
			} catch (Exception e) {
				display.getNumberChange().setText("0");
				display.setTotal("0");
			}

			
		}
	});
	display.getConfirmbutton().addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			try {
				int price=Integer.parseInt(display.getUnitprice());
				int num=Integer.parseInt(display.getNumber().getValue());
				if(balance<price*num)
				{
					display.getMessage().setVisible(true);
					display.getConfirmbuttonObj().setEnabled(false);
					return;
				}
			} catch (Exception e) {
				display.getNumberChange().setText("0");
				display.setTotal("0");
			}
			
			win.alert("提交");
			
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
	public void initOrderConfirmation(String orderId) {
		this.orderId=orderId;
		
	}



}
