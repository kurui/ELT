package com.chinarewards.gwt.elt.client.orderConfirmation.view;

import com.chinarewards.gwt.elt.client.orderConfirmation.presenter.OrderConfirmationPresenter.OrderConfirmationDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class OrderConfirmationWidget extends Composite implements
		OrderConfirmationDisplay {
	@UiField
	InlineLabel orderId;
	
	@UiField
	Button confirmbutton;
	@UiField
	Button returnbutton;
	private static OrderConfirmationWidgetUiBinder uiBinder = GWT
			.create(OrderConfirmationWidgetUiBinder.class);

	interface OrderConfirmationWidgetUiBinder extends
			UiBinder<Widget, OrderConfirmationWidget> {
	}

	public OrderConfirmationWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setOrderId(String orderId) {
		this.orderId.setText(orderId);
		
	}


	



}
