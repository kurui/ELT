package com.chinarewards.gwt.elt.client.gift.module;


import com.chinarewards.gwt.elt.client.gift.presenter.OrderListPresenter;
import com.chinarewards.gwt.elt.client.gift.presenter.OrderListPresenter.OrderListDisplay;
import com.chinarewards.gwt.elt.client.gift.presenter.OrderListPresenterImpl;
import com.chinarewards.gwt.elt.client.gift.view.OrderListWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class OrderListModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(OrderListPresenter.class).to(OrderListPresenterImpl.class);
		bind(OrderListDisplay.class).to(OrderListWidget.class);
	}

}
