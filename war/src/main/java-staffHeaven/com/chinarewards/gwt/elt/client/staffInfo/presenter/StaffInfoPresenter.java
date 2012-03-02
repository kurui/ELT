package com.chinarewards.gwt.elt.client.staffInfo.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface StaffInfoPresenter extends
		Presenter<StaffInfoPresenter.StaffInfoDisplay> {

		public static interface StaffInfoDisplay extends Display {
		public HasClickHandlers getupadateBtnClickHandlers();
	
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
		
	}
}
