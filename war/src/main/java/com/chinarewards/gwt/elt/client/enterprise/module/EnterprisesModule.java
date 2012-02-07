package com.chinarewards.gwt.elt.client.enterprise.module;

import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisePresenter;
import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisePresenter.EnterpriseDisplay;
import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisePresenterImpl;
import com.chinarewards.gwt.elt.client.enterprise.presenter.IntegralPricePresenter;
import com.chinarewards.gwt.elt.client.enterprise.presenter.IntegralPricePresenter.IntegralPriceDisplay;
import com.chinarewards.gwt.elt.client.enterprise.presenter.IntegralPricePresenterImpl;
import com.chinarewards.gwt.elt.client.enterprise.presenter.PeriodPresenter;
import com.chinarewards.gwt.elt.client.enterprise.presenter.PeriodPresenter.PeriodDisplay;
import com.chinarewards.gwt.elt.client.enterprise.presenter.PeriodPresenterImpl;
import com.chinarewards.gwt.elt.client.enterprise.view.EnterpWidget;
import com.chinarewards.gwt.elt.client.enterprise.view.IntegralPriceWidget;
import com.chinarewards.gwt.elt.client.enterprise.view.PeriodWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class EnterprisesModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EnterprisePresenter.class).to(EnterprisePresenterImpl.class);
		bind(EnterpriseDisplay.class).to(EnterpWidget.class);
		
		bind(IntegralPricePresenter.class).to(IntegralPricePresenterImpl.class);
		bind(IntegralPriceDisplay.class).to(IntegralPriceWidget.class);
		
		bind(PeriodPresenter.class).to(PeriodPresenterImpl.class);
		bind(PeriodDisplay.class).to(PeriodWidget.class);
		
		
	}

}
