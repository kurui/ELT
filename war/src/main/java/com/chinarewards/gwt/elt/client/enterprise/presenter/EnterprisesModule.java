package com.chinarewards.gwt.elt.client.enterprise.presenter;

import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisePresenter;
import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisePresenterImpl;
import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisePresenter.EnterpriseDisplay;
import com.chinarewards.gwt.elt.client.enterprise.view.EnterpWidget;

import com.google.gwt.inject.client.AbstractGinModule;

public class EnterprisesModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EnterprisePresenter.class).to(EnterprisePresenterImpl.class);
		bind(EnterpriseDisplay.class).to(EnterpWidget.class);
	}

}
