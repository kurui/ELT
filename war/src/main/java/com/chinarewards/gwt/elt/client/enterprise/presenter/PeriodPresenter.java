package com.chinarewards.gwt.elt.client.enterprise.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface PeriodPresenter extends
		Presenter<PeriodPresenter.PeriodDisplay> {

	public static interface PeriodDisplay extends Display {

		public HasClickHandlers getSaveClickHandlers();

		public HasValue<String> getPeriod();

		public String getEnterpriseId();

		void setBreadCrumbs(Widget breadCrumbs);
	}

}
