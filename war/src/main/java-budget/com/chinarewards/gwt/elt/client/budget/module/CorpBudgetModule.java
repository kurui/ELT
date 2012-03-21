package com.chinarewards.gwt.elt.client.budget.module;

import com.chinarewards.gwt.elt.client.budget.presenter.CorpBudgetPresenter;
import com.chinarewards.gwt.elt.client.budget.presenter.CorpBudgetPresenter.CorpBudgetDisplay;
import com.chinarewards.gwt.elt.client.budget.presenter.CorpBudgetPresenterImpl;
import com.chinarewards.gwt.elt.client.budget.view.CorpBudgetWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class CorpBudgetModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(CorpBudgetPresenter.class).to(CorpBudgetPresenterImpl.class);
		bind(CorpBudgetDisplay.class).to(CorpBudgetWidget.class);
	}
}
