package com.chinarewards.gwt.elt.client.awardShopLattice.view;

import com.chinarewards.gwt.elt.client.awardShopLattice.presenter.AwardShopLatticePresenter.AwardShopLatticeDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AwardShopLatticeWidget extends Composite implements
		AwardShopLatticeDisplay {

	private static AwardShopLatticeWidgetUiBinder uiBinder = GWT
			.create(AwardShopLatticeWidgetUiBinder.class);

	interface AwardShopLatticeWidgetUiBinder extends
			UiBinder<Widget, AwardShopLatticeWidget> {
	}

	public AwardShopLatticeWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
}
