package com.chinarewards.gwt.elt.client.colleague.view;


import com.chinarewards.gwt.elt.client.colleague.presenter.ColleagueListPresenter.ColleagueListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ColleagueListWidget extends Composite implements ColleagueListDisplay {

	
	//@UiField
	InlineLabel dataCount;
	//@UiField
	Panel resultPanel;
	//@UiField
	Panel resultpage;

	
	private static ColleagueListWidgetUiBinder uiBinder = GWT
			.create(ColleagueListWidgetUiBinder.class);

	interface ColleagueListWidgetUiBinder extends
			UiBinder<Widget, ColleagueListWidget> {
	}

	public ColleagueListWidget() {
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



}
