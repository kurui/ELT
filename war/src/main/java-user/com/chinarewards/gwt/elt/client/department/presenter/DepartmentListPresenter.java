package com.chinarewards.gwt.elt.client.department.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface DepartmentListPresenter extends
		Presenter<DepartmentListPresenter.DepartmentListDisplay> {

	public static interface DepartmentListDisplay extends Display {

		public HasClickHandlers getAddSameLevelBtnClickHandlers();

		public HasClickHandlers getAddChildBtnClickHandlers();

		public HasClickHandlers getDeleteBtnClickHandlers();

		public HasClickHandlers getEditBtnClickHandlers();

		public HasClickHandlers getMergeBtnClickHandlers();

		public HasClickHandlers getSynchBtnClickHandlers();

		Panel getResultPanel();

		void setBreadCrumbs(Widget breadCrumbs);

	}
}
