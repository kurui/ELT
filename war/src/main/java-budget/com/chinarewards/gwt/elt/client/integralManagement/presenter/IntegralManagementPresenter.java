package com.chinarewards.gwt.elt.client.integralManagement.presenter;


import java.util.List;

import com.chinarewards.gwt.elt.client.integralManagement.model.Category;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface IntegralManagementPresenter extends Presenter<IntegralManagementPresenter.IntegralManagementDisplay> {


	public static interface IntegralManagementDisplay extends Display {

		Panel getCellTree();
		void refresh(List<Category> result,String corporationId);
		void setBreadCrumbs(Widget breadCrumbs);
		public HasClickHandlers getNominateClickHandlers();
	}
}
