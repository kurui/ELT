package com.chinarewards.gwt.elt.client.department.presenter;

import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface DepartmentPresenter extends
		Presenter<DepartmentPresenter.DepartmentDisplay> {

	public static interface DepartmentDisplay extends Display {

		public HasValue<String> getLeader();

		public HasValue<String> getSuperdepartment();

		public HasValue<String> getChilddepartment();

		public HasValue<String> getPeopleNumber();

		public HasValue<String> getYearintegral();

		public HasValue<String> getIssueintegral();

		public HasClickHandlers getSaveClick();

		public void clear();

		public void initAddDepartment(DepartmentVo giftVo);

		public void initEditDepartment(DepartmentVo giftVo);

		public HasClickHandlers getBackClick();

		void setBreadCrumbs(Widget breadCrumbs);

	}

	public void initEditor(String giftId, String thisAction);
}
