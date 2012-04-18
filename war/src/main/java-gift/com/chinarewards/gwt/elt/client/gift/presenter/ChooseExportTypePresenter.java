package com.chinarewards.gwt.elt.client.gift.presenter;

import com.chinarewards.gwt.elt.client.mvp.DialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public interface ChooseExportTypePresenter extends
		DialogPresenter<ChooseExportTypePresenter.ChooseExportTypeDisplay> {


	public static interface ChooseExportTypeDisplay extends Display {
		ListBox getExportFileType();

		Widget asWidget();

		Button getChooseBtn();


	}

}
