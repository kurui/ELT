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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RewardsListWidget extends Composite implements RewardsListDisplay {
	@UiField
	Panel resultPanel;
	@UiField
	Button searchBtn;
	
	@UiField
	TextBox rewardsName;
	@UiField
	TextBox definition;
	@UiField
	CheckBox nowJudge;
	
	private static HrRegisterWidgetUiBinder uiBinder = GWT
			.create(HrRegisterWidgetUiBinder.class);

	interface HrRegisterWidgetUiBinder extends UiBinder<Widget, RewardsListWidget> {
	}

	public RewardsListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
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


}
