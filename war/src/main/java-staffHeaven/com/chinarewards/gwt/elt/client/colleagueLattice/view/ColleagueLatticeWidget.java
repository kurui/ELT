package com.chinarewards.gwt.elt.client.colleagueLattice.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ColleagueLatticeWidget extends Composite {

	@UiField
	Image photo;
	@UiField
	Anchor staffName;
	@UiField
	InlineLabel deptName;
	private static ColleagueLatticeWidgetUiBinder uiBinder = GWT
			.create(ColleagueLatticeWidgetUiBinder.class);

	interface ColleagueLatticeWidgetUiBinder extends
			UiBinder<Widget, ColleagueLatticeWidget> {
	}

	public ColleagueLatticeWidget(String staffId,String staffName,String deptName,String photo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.staffName.setText(staffName);
		this.deptName.setText(deptName);
		this.photo.setUrl("imageshow?imageName="+photo);
		
	}

}
