package com.chinarewards.gwt.elt.client.department.presenter;


import java.util.Map;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface DepartmentListPresenter extends Presenter<DepartmentListPresenter.DepartmentListDisplay> {


	public static interface DepartmentListDisplay extends Display {

		public HasClickHandlers getSearchBtnClickHandlers();
		public HasClickHandlers getAddBtnClickHandlers();
		public HasClickHandlers getimportingBtnClickHandlers();
		void setDataCount(String text);
		HasValue<String> getKeyName();
		String getStatus();
		Panel getResultPanel();
		Panel getResultpage();
		public void initDepartmentStatus(Map<String, String> map);
		
		void setBreadCrumbs(Widget breadCrumbs);

	}
}
