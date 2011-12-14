package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.core.presenter.DockPresenter.DockDisplay;
import com.chinarewards.gwt.elt.client.core.view.DockWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class DockModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(DockPresenter.class).to(DockPresenterImpl.class);
		bind(DockDisplay.class).to(DockWidget.class);
	}

}
