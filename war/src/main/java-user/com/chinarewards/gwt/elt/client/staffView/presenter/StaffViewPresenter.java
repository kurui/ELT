package com.chinarewards.gwt.elt.client.staffView.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface StaffViewPresenter extends
		Presenter<StaffViewPresenter.StaffViewDisplay> {

	public void initStaffView(String staffId);
	public void initStaffView_Colleague(String staffId,boolean colleague);
	public static interface StaffViewDisplay extends Display {

		public HasClickHandlers getUpadateBtnClickHandlers();

		void setBreadCrumbs(Widget breadCrumbs);

		void setStaffNo(String text);

		void setStaffName(String text);

		void setDepartmentName(String text);

		void setJobPosition(String text);

		void setLeadership(String text);

		void setPhone(String text);

		void setEmail(String text);

		void setDob(String text);
		void setStaffStatus(String text);
		InlineLabel getStaffRoles();

		void setStaffImage(String url);
		void setDataCount(String text);
		InlineLabel getDataCount();
		
		Panel getResultPanel();
		Panel getResultpage();
		void displayUpdateBtn(boolean colleague);

		public Anchor getBtnIntegral();
		public Anchor getBtnExchangeHistory();
		public Anchor getBtnRewardHistory();

		public InlineLabel getHistoryIntegral();

		public InlineLabel getConsumptionIntegral();

		public InlineLabel getBalanceIntegral();
		
		public InlineLabel getDetailTitle();

		public ListBox getPageNumber();
		
	}
}
