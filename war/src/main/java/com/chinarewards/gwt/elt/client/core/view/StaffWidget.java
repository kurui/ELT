package com.chinarewards.gwt.elt.client.core.view;

import java.util.Date;

import com.chinarewards.gwt.elt.client.core.presenter.StaffPresenter.StaffDisplay;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class StaffWidget extends Composite implements StaffDisplay {

	@UiField
	DockLayoutPanel dock;


	@UiField
	Anchor logBtn;

	@UiField
	InlineLabel message;


	@UiField
	Anchor collectionBtn;
	

	
	@UiField
	Anchor managementCenter;
	@UiField
	Anchor giftExchange;
	@UiField
	Anchor staffCorner;
	
	
	// Set the format of datepicker.
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format_chinese);

	interface StaffWidgetBinder extends UiBinder<Widget, StaffWidget> {
	}

	private static StaffWidgetBinder uiBinder = GWT
			.create(StaffWidgetBinder.class);

	public StaffWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getlogBtn() {
		return logBtn;
	}


	@Override
	public DockLayoutPanel getDock() {
		return dock;
	}


	@Override
	public void setMessage(String userName) {
		String time = dateFormat.format(new Date());
		String msg = "欢迎你，" + userName + "！今天是:" + time;
		message.setText(msg);
	}




	@Override
	public HasClickHandlers getBtnCollection() {
		return collectionBtn;
	}

	@Override
	public HasClickHandlers getManagementCenter() {

		return managementCenter;
	}

	@Override
	public HasClickHandlers getGiftExchange() {
		return giftExchange;
	}

	@Override
	public HasClickHandlers getStaffCorner() {
		return staffCorner;
	}
	@Override
	public void disableManagementCenter() {
		managementCenter.setVisible(false);
		
	}

	@Override
	public void disableGiftExchange() {
		giftExchange.setVisible(false);
		
	}

	@Override
	public void disableStaffCorner() {
		staffCorner.setVisible(false);
		
	}
}
