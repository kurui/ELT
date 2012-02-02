package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Panel;

public interface StaffPresenter extends Presenter<StaffPresenter.StaffDisplay> {

	public static interface StaffDisplay extends Display {
		
		HasClickHandlers getlogBtn();
		HasClickHandlers getBtnEmail();
		HasClickHandlers getBtnGb();
		HasClickHandlers getBtnReward();
		HasClickHandlers getBtnRewardItem();
		HasClickHandlers getBtnStaff();
		HasClickHandlers getBtnSetting();
		HasClickHandlers getBtnGift();
		HasClickHandlers getBtnCollection();
		HasClickHandlers getManagementCenter();
		HasClickHandlers getGiftExchange();
		HasClickHandlers getStaffCorner();
		DockLayoutPanel getDock();
		
		Panel getMenu();
		
		void setMessage(String userName);
		void setMenu(Panel panel);
		void setMenuTitle(String title);
	}
	
	
}
