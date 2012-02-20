package com.chinarewards.gwt.elt.client.broadcasting.view;


import com.chinarewards.gwt.elt.client.broadcasting.presenter.BroadcastingListPresenter.BroadcastingListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class BroadcastingListWidget extends Composite implements BroadcastingListDisplay {

	@UiField
	TextBox createUser;
	@UiField
	ListBox status;
	@UiField
	Button addBtn;

	@UiField
	Button searchBtn;
	
	@UiField
	InlineLabel dataCount;
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;
	@UiField
	Panel breadCrumbs;
	
	@UiField
	DateBox broadcastingTime;
	@UiField
	DateBox broadcastingTimeEnd;
	private static BroadcastingListWidgetUiBinder uiBinder = GWT
			.create(BroadcastingListWidgetUiBinder.class);

	interface BroadcastingListWidgetUiBinder extends
			UiBinder<Widget, BroadcastingListWidget> {
	}

	public BroadcastingListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getSearchBtnClickHandlers() {
		return this.searchBtn;
	}


	@Override
	public HasClickHandlers getAddBtnClickHandlers() {
		return this.addBtn;
	}



	@Override
	public void setDataCount(String text) {
		dataCount.setText(text);
		
	}

	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
		
	}

	@Override
	public Panel getResultPanel() {
		return this.resultPanel;
	}

	@Override
	public Panel getResultpage() {
		return this.resultpage;
	}

	



}
