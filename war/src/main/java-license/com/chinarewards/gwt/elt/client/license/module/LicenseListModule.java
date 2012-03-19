package com.chinarewards.gwt.elt.client.license.module;


import com.chinarewards.gwt.elt.client.license.presenter.LicenseListPresenter;
import com.chinarewards.gwt.elt.client.license.presenter.LicenseListPresenter.LicenseListDisplay;
import com.chinarewards.gwt.elt.client.license.presenter.LicenseListPresenterImpl;
import com.chinarewards.gwt.elt.client.license.view.LicenseListWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class LicenseListModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(LicenseListPresenter.class).to(LicenseListPresenterImpl.class);
		bind(LicenseListDisplay.class).to(LicenseListWidget.class);
	}

}
