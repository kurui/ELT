package com.chinarewards.gwt.elt.client.order.module;

import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenter;
import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenterImpl;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenter;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenterImpl;
import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenter.OrderDisplay;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenter.OrderViewDisplay;
import com.chinarewards.gwt.elt.client.order.view.OrderViewWidget;
import com.chinarewards.gwt.elt.client.order.view.OrderWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class OrderModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(OrderPresenter.class).to(OrderPresenterImpl.class);
		bind(OrderDisplay.class).to(OrderWidget.class);

		bind(OrderViewPresenter.class).to(OrderViewPresenterImpl.class);
		bind(OrderViewDisplay.class).to(OrderViewWidget.class);
	}
}
