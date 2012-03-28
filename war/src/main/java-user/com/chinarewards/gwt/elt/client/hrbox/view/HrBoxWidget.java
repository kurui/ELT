package com.chinarewards.gwt.elt.client.hrbox.view;

import java.util.Date;

import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.hrbox.presenter.HrBoxPresenter.HrBoxDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class HrBoxWidget extends Composite implements	HrBoxDisplay {
	@UiField
	Panel rewardWindow;
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;	
	
	@UiField
	InlineLabel send;
	@UiField
	InlineLabel tm;
	
	@UiField
	Anchor sends;
	@UiField
	Anchor tms;
	@UiField
	Anchor view;	
	@UiField
	Anchor viewBudget;
	@UiField
	InlineLabel message;
	@UiField
	InlineLabel mess;
	@UiField
	ListBox pageNumber;
	// Set the format of datepicker.
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(ViewConstants.date_format_chinese);

	private static HrBoxWidgetUiBinder uiBinder = GWT
			.create(HrBoxWidgetUiBinder.class);

	interface HrBoxWidgetUiBinder extends
			UiBinder<Widget, HrBoxWidget> {
	}

	public HrBoxWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		pageNumber.clear();
		pageNumber.addItem("10","10");
		pageNumber.addItem("20","20");
	}
   
	
	
	@Override
	public Panel getRewardWindow() {
		return rewardWindow;
	}

	@Override
	public void setHrSend(String text) {
		send.setText(text);
		
	}
	@Override
	public void setTms(String text) {
		tms.setText(text);
		
	}
	@Override
	public void setHrSends(String text) {
		sends.setText(text);
		
	}
	@Override
	public void setTm(String text) {
		tm.setText(text);
		
	}
	@Override
	public HasClickHandlers getView() {
		return view;
	}
	
	@Override
	public HasClickHandlers getSends() {
		return sends;
	}
	@Override
	public HasClickHandlers getTms() {
		return tms;
	}
	@Override
	public HasClickHandlers getViewBudget() {
		return viewBudget;
	}
	@Override
	public Panel getResultpage() {
		return resultpage;
	}

	@Override
	public void setMessage(String week) {
		String time = dateFormat.format(new Date());
		
		String msg =  "今天是:" + time+" "+week;
		message.setText(msg);
	}
	@Override
	public void setMess(String text){
		mess.setText(text);
	}
	@Override
	public Panel getResultPanel() {
		return resultPanel;
	}

	@Override
	public ListBox getPageNumber() {
		return pageNumber;
	}
}
