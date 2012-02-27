package com.chinarewards.gwt.elt.client.staffHeavenIndex.view;

import com.chinarewards.gwt.elt.client.staffHeavenIndex.presenter.StaffHeavenIndexPresenter.StaffHeavenIndexDisplay;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class StaffHeavenIndexWidget extends Composite implements
		StaffHeavenIndexDisplay {

	@UiField
	InlineLabel dataCount;
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;

	@UiField
	Anchor allInformation;
	@UiField
	Anchor staffInformation;
	@UiField
	Anchor sysInformation;
	@UiField
	Anchor themeInformation;
	@UiField
	InlineLabel topBroadcast;
	
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static StaffHeavenIndexWidgetUiBinder uiBinder = GWT
			.create(StaffHeavenIndexWidgetUiBinder.class);

	interface StaffHeavenIndexWidgetUiBinder extends
			UiBinder<Widget, StaffHeavenIndexWidget> {
	}

	public StaffHeavenIndexWidget() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void setDataCount(String text) {
		dataCount.setText(text);

	}

	@Override
	public Panel getResultPanel() {
		return this.resultPanel;
	}

	@Override
	public Panel getResultpage() {
		return this.resultpage;
	}

	@Override
	public Anchor getAllInformation() {
		return this.allInformation;
	}

	@Override
	public Anchor getStaffInformation() {
		return staffInformation;
	}

	@Override
	public Anchor getSysInformation() {
		return sysInformation;
	}

	@Override
	public Anchor getThemeInformation() {
		return themeInformation;
	}

	@Override
	public void setTopBroadcast(String text) {
		topBroadcast.setText(text);
		
	}

}
