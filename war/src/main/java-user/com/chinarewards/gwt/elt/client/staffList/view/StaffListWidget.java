package com.chinarewards.gwt.elt.client.staffList.view;


import com.chinarewards.gwt.elt.client.staffList.presenter.StaffListPresenter.StaffListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StaffListWidget extends Composite implements StaffListDisplay {


	
	private static StaffListWidgetUiBinder uiBinder = GWT
			.create(StaffListWidgetUiBinder.class);

	interface StaffListWidgetUiBinder extends
			UiBinder<Widget, StaffListWidget> {
	}

	public StaffListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}



}
