package com.chinarewards.gwt.elt.client.breadCrumbs.view;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter.BreadCrumbsDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BreadCrumbsWidget extends Composite implements BreadCrumbsDisplay {

	public BreadCrumbsWidget() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	interface ChooseStaffPanelBinder extends
			UiBinder<Widget, BreadCrumbsWidget> {
	}

	private static ChooseStaffPanelBinder uiBinder = GWT
			.create(ChooseStaffPanelBinder.class);

	@Override
	public Widget asWidget() {
		return this;
	}

}
