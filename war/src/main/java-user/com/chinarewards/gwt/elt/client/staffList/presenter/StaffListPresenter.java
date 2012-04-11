package com.chinarewards.gwt.elt.client.staffList.presenter;


import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface StaffListPresenter extends Presenter<StaffListPresenter.StaffListDisplay> {
	

	public static interface StaffListDisplay extends Display {

		public HasClickHandlers getSearchBtnClickHandlers();
		public HasClickHandlers getSynchronousStaffBtnClickHandlers();
		public HasClickHandlers getAddStaffBtnClickHandlers();
		public HasClickHandlers getCreateSysUserBtnClickHandlers();
		public HasClickHandlers getPrintBtnClickHandlers();
		public HasClickHandlers getSendMailAllBtnClickHandlers();

		void initStaffStatus();
		String getSttaffStatus();
		String getSttaffRole();
	
		HasValue<String> getStaffNameorNo();
		HasValue<String> getStaffEmail();

		void setDataCount(String text);
		void setBreadCrumbs(Widget breadCrumbs);
		
		Panel getResultPanel();
		Panel getResultpage();

		void displayBtn();

		ListBox getPageNumber();
		ListBox getDepartment();
	}
}
