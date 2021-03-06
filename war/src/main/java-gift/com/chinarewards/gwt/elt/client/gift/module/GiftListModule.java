package com.chinarewards.gwt.elt.client.gift.module;


import com.chinarewards.gwt.elt.client.gift.presenter.ChooseExportTypePresenter;
import com.chinarewards.gwt.elt.client.gift.presenter.ChooseExportTypePresenter.ChooseExportTypeDisplay;
import com.chinarewards.gwt.elt.client.gift.presenter.ChooseExportTypePresenterImpl;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftImportPresenter;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftImportPresenter.GiftImportDisplay;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftImportPresenterImpl;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftListPresenter;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftListPresenter.GiftListDisplay;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftListPresenterImpl;
import com.chinarewards.gwt.elt.client.gift.view.ChooseExportTypeWidget;
import com.chinarewards.gwt.elt.client.gift.view.GiftImportWidget;
import com.chinarewards.gwt.elt.client.gift.view.GiftListWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class GiftListModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(GiftListPresenter.class).to(GiftListPresenterImpl.class);
		bind(GiftListDisplay.class).to(GiftListWidget.class);
		
		bind(ChooseExportTypePresenter.class).to(
				ChooseExportTypePresenterImpl.class);
		bind(ChooseExportTypeDisplay.class).to(ChooseExportTypeWidget.class);
		
		bind(GiftImportPresenter.class).to(GiftImportPresenterImpl.class);
		bind(GiftImportDisplay.class).to(GiftImportWidget.class);
	}

}
