package com.chinarewards.gwt.elt.client.colleagueParticular.view;


import com.chinarewards.gwt.elt.client.colleagueParticular.presenter.ColleagueParticularPresenter.ColleagueParticularDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ColleagueParticularWidget extends Composite implements ColleagueParticularDisplay {

	private static ColleagueParticularWidgetUiBinder uiBinder = GWT
			.create(ColleagueParticularWidgetUiBinder.class);

	interface ColleagueParticularWidgetUiBinder extends
			UiBinder<Widget, ColleagueParticularWidget> {
	}

	public ColleagueParticularWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}



}
