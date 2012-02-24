package com.chinarewards.gwt.elt.client.rewards.view;

import com.chinarewards.gwt.elt.client.rewards.presenter.RewardsListStaffPresenter.RewardsListStaffDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RewardsListStaffWidget extends Composite implements RewardsListStaffDisplay {
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;
//	@UiField
//	Panel breadCrumbs;
	
	@UiField
	Button searchBtn;
	
	@UiField
	TextBox rewardsName;
	@UiField
	TextBox definition;

	@UiField
	InlineLabel dataCount;
	private static RewardsListStaffWidgetUiBinder uiBinder = GWT
			.create(RewardsListStaffWidgetUiBinder.class);

	interface RewardsListStaffWidgetUiBinder extends UiBinder<Widget, RewardsListStaffWidget> {
	}

	public RewardsListStaffWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getSearchBtnClickHandlers() {
		return searchBtn;
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
//		this.breadCrumbs.clear();
//		this.breadCrumbs.add(breadCrumbs);
		
	}




}
