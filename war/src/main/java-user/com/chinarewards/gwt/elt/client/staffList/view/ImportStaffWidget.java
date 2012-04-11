package com.chinarewards.gwt.elt.client.staffList.view;

import com.chinarewards.gwt.elt.client.staffList.presenter.ImportStaffPresenter.ImportStaffDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ImportStaffWidget extends Composite implements ImportStaffDisplay {

	private static StaffImportWidgetUiBinder uiBinder = GWT
			.create(StaffImportWidgetUiBinder.class);

	interface StaffImportWidgetUiBinder extends
			UiBinder<Widget, ImportStaffWidget> {
	}

	public ImportStaffWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
