package com.chinarewards.gwt.elt.client.staffAdd.view;


import com.chinarewards.gwt.elt.client.staffAdd.presenter.StaffAddPresenter.StaffAddDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class StaffAddWidget extends Composite implements StaffAddDisplay {

//	@UiField
//	TextBox staffNameorNo;

	@UiField
	Button addBtn;
	@UiField
	Button importBtn;

	@UiField
	Panel breadCrumbs;
	
	private static StaffAddWidgetUiBinder uiBinder = GWT
			.create(StaffAddWidgetUiBinder.class);

	interface StaffAddWidgetUiBinder extends
			UiBinder<Widget, StaffAddWidget> {
	}

	public StaffAddWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
		
	}


	@Override
	public HasClickHandlers getAddBtnClickHandlers() {
		return addBtn;
	}


	@Override
	public HasClickHandlers getImportBtnClickHandlers() {
		return importBtn;
	}




}
