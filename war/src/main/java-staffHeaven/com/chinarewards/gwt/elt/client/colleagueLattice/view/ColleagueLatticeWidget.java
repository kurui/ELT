package com.chinarewards.gwt.elt.client.colleagueLattice.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ColleagueLatticeWidget extends Composite {

	private static ColleagueLatticeWidgetUiBinder uiBinder = GWT
			.create(ColleagueLatticeWidgetUiBinder.class);

	interface ColleagueLatticeWidgetUiBinder extends
			UiBinder<Widget, ColleagueLatticeWidget> {
	}

	public ColleagueLatticeWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
