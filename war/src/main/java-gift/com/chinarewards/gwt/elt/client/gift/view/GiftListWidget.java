package com.chinarewards.gwt.elt.client.gift.view;

import com.chinarewards.gwt.elt.client.gift.presenter.GiftListPresenter.GiftListDisplay;
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

public class GiftListWidget extends Composite implements GiftListDisplay {
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;
	
	@UiField
	Button searchBtn;
	
	@UiField
	TextBox rewardsName;
	@UiField
	TextBox definition;
	@UiField
	CheckBox nowJudge;
	
	private static GiftWidgetUiBinder uiBinder = GWT
			.create(GiftWidgetUiBinder.class);

	interface GiftWidgetUiBinder extends UiBinder<Widget, GiftListWidget> {
	}

	public GiftListWidget() {
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

	@Override
	public Panel getResultpage() {
		return resultpage;
	}


}
