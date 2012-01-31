package com.chinarewards.gwt.elt.client.orderConfirmation.presenter;


import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;

public interface OrderConfirmationPresenter extends Presenter<OrderConfirmationPresenter.OrderConfirmationDisplay> {

	public void initOrderConfirmation(String orderId);
	public static interface OrderConfirmationDisplay extends Display {

		void setOrderId(String orderId);
	}
}
