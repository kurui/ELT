package com.chinarewards.gwt.elt.client.department.module;

import com.chinarewards.gwt.elt.client.department.presenter.DepartmentPresenter;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentPresenter.DepartmentDisplay;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentPresenterImpl;
import com.chinarewards.gwt.elt.client.department.view.DepartmentWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class DepartmentModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(DepartmentPresenter.class).to(DepartmentPresenterImpl.class);
		bind(DepartmentDisplay.class).to(DepartmentWidget.class);

	}
}
