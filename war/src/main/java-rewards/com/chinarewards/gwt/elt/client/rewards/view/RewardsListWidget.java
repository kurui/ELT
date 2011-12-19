package com.chinarewards.gwt.elt.client.rewards.view;

import com.chinarewards.gwt.elt.client.rewards.presenter.RewardsListPresenter.RewardsListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class RewardsListWidget extends Composite implements RewardsListDisplay {
	@UiField
	Panel resultPanel;
	@UiField
	Button addBtn;
	
	private static HrRegisterWidgetUiBinder uiBinder = GWT
			.create(HrRegisterWidgetUiBinder.class);

	interface HrRegisterWidgetUiBinder extends UiBinder<Widget, RewardsListWidget> {
	}

	public RewardsListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getNominateClickHandlers() {
		return addBtn;
	}

	@Override
	public Panel getResultPanel() {
		return resultPanel;
	}


}
