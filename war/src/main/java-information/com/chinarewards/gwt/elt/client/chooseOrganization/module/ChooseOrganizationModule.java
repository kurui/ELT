package com.chinarewards.gwt.elt.client.chooseOrganization.module;


import com.chinarewards.gwt.elt.client.chooseOrganization.presenter.ChooseOrganizationListPresenter;
import com.chinarewards.gwt.elt.client.chooseOrganization.presenter.ChooseOrganizationListPresenter.ChooseOrganizationListDisplay;
import com.chinarewards.gwt.elt.client.chooseOrganization.presenter.ChooseOrganizationListPresenterImpl;
import com.chinarewards.gwt.elt.client.chooseOrganization.view.ChooseOrganizationListWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class ChooseOrganizationModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ChooseOrganizationListPresenter.class).to(ChooseOrganizationListPresenterImpl.class);
		bind(ChooseOrganizationListDisplay.class).to(ChooseOrganizationListWidget.class);
		

	}

}
