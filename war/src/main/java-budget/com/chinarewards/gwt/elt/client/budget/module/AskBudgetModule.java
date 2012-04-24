package com.chinarewards.gwt.elt.client.budget.module;

import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetAddPresenter;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetAddPresenter.AskBudgetAddDisplay;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetAddPresenterImpl;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetListPresenter;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetListPresenter.AskBudgetListDisplay;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetListPresenterImpl;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetViewPresenter;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetViewPresenter.AskBudgetViewDisplay;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetViewPresenterImpl;
import com.chinarewards.gwt.elt.client.budget.view.AskBudgetAddWidget;
import com.chinarewards.gwt.elt.client.budget.view.AskBudgetListWidget;
import com.chinarewards.gwt.elt.client.budget.view.AskBudgetViewWidget;
import com.google.gwt.inject.client.AbstractGinModule;

/**
 * 
 * @author harry
 * @since 2010-12-16
 */
public class AskBudgetModule extends AbstractGinModule {

	@Override
	protected void configure() {

		bind(AskBudgetListPresenter.class).to(AskBudgetListPresenterImpl.class);
		bind(AskBudgetListDisplay.class).to(AskBudgetListWidget.class);

		bind(AskBudgetAddPresenter.class).to(AskBudgetAddPresenterImpl.class);
		bind(AskBudgetAddDisplay.class).to(AskBudgetAddWidget.class);
				
		bind(AskBudgetViewPresenter.class).to(AskBudgetViewPresenterImpl.class);
		bind(AskBudgetViewDisplay.class).to(AskBudgetViewWidget.class); 
	}

}
