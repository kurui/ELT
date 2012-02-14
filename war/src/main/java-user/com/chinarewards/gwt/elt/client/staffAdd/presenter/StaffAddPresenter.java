package com.chinarewards.gwt.elt.client.staffAdd.presenter;


import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

public interface StaffAddPresenter extends Presenter<StaffAddPresenter.StaffAddDisplay> {
	

	public static interface StaffAddDisplay extends Display {

		public HasClickHandlers getAddBtnClickHandlers();
		public HasClickHandlers getImportBtnClickHandlers();

		void setBreadCrumbs(Widget breadCrumbs);
		





	}
}
