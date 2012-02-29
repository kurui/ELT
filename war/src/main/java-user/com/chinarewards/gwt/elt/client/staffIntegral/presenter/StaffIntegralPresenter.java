package com.chinarewards.gwt.elt.client.staffIntegral.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.user.client.ui.Widget;

public interface StaffIntegralPresenter extends
		Presenter<StaffIntegralPresenter.StaffIntegralDisplay> {

	public static interface StaffIntegralDisplay extends Display {

		void setBreadCrumbs(Widget breadCrumbs);
	}
}
