package com.chinarewards.gwt.elt.client.rewards.view;

import com.chinarewards.gwt.elt.client.rewards.presenter.RewardsListPresenter.RewardsListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RewardsListWidget extends Composite implements RewardsListDisplay {
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;
	@UiField
	Panel breadCrumbs;
	
	@UiField
	Button searchBtn;
	
	@UiField
	TextBox rewardsName;
	@UiField
	TextBox definition;
	@UiField
	CheckBox nowJudge;
	@UiField
	InlineLabel dataCount;
	
	@UiField
	ListBox pageNumber;
	private static RewardsListWidgetUiBinder uiBinder = GWT
			.create(RewardsListWidgetUiBinder.class);

	interface RewardsListWidgetUiBinder extends UiBinder<Widget, RewardsListWidget> {
	}

	public RewardsListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		pageNumber.clear();
		pageNumber.addItem("10","10");
		pageNumber.addItem("20","20");
		pageNumber.addItem("50","50");
	}

	@Override
	public HasClickHandlers getSearchBtnClickHandlers() {
		return searchBtn;
	}
	@Override
	public HasValue<Boolean> getNowJudge() {
		return nowJudge;
	}
	@Override
	public Panel getResultPanel() {
		return resultPanel;
	}

	@Override
	public HasValue<String> getName() {
		return rewardsName;
	}

	@Override
	public HasValue<String> getDefinition() {
		return definition;
	}

	@Override
	public Panel getResultpage() {
		return resultpage;
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
	public ListBox getPageNumber() {
		return pageNumber;
	}


}
