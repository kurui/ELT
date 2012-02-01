package com.chinarewards.gwt.elt.client.detailsOfGift.view;

import com.chinarewards.gwt.elt.client.detailsOfGift.presenter.DetailsOfGiftPresenter.DetailsOfGiftDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DetailsOfGiftWidget extends Composite implements
		DetailsOfGiftDisplay {


	private static DetailsOfGiftWidgetUiBinder uiBinder = GWT
			.create(DetailsOfGiftWidgetUiBinder.class);

	interface DetailsOfGiftWidgetUiBinder extends
			UiBinder<Widget, DetailsOfGiftWidget> {
	}

	public DetailsOfGiftWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}






	



}
