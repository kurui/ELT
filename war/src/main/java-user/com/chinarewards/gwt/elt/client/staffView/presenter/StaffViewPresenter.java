package com.chinarewards.gwt.elt.client.staffView.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

public interface StaffViewPresenter extends
		Presenter<StaffViewPresenter.StaffViewDisplay> {

	public void initStaffView(String staffId);

	public static interface StaffViewDisplay extends Display {

		public HasClickHandlers getupadateBtnClickHandlers();

		void setBreadCrumbs(Widget breadCrumbs);

		void setStaffNo(String text);

		void setStaffName(String text);

		void setDepartmentName(String text);

		void setJobPosition(String text);

		void setLeadership(String text);

		void setPhone(String text);

		void setEmail(String text);

		void setDob(String text);

		void setStaffImage(String url);

	}
}
