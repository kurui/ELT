package com.chinarewards.gwt.elt.client.breadCrumbs.module;


import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter.BreadCrumbsDisplay;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenterImpl;
import com.chinarewards.gwt.elt.client.breadCrumbs.view.BreadCrumbsWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class BreadCrumbsModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(BreadCrumbsPresenter.class).to(BreadCrumbsPresenterImpl.class);
		bind(BreadCrumbsDisplay.class).to(BreadCrumbsWidget.class);
		
	}

}
