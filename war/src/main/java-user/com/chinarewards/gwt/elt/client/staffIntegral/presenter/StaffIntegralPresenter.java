package com.chinarewards.gwt.elt.client.staffIntegral.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface StaffIntegralPresenter extends
		Presenter<StaffIntegralPresenter.StaffIntegralDisplay> {

	public static interface StaffIntegralDisplay extends Display {

		void setStaffNo(String text);

		void setStaffName(String text);

		void setDepartmentName(String text);

		void setJobPosition(String text);

		void setLeadership(String text);

		void setPhone(String text);

		void setEmail(String text);

		void setDob(String text);

		void setStaffStatus(String text);

		void setStaffImage(String url);

		void setDataCount(String text);

		Panel getResultPanel();

		Panel getResultpage();

		void setBreadCrumbs(Widget breadCrumbs);
	}
}
