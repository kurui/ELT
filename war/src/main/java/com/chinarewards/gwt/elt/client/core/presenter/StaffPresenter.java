package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Panel;

public interface StaffPresenter extends Presenter<StaffPresenter.StaffDisplay> {

	public static interface StaffDisplay extends Display {
		
		HasClickHandlers getlogBtn();

		HasClickHandlers getBtnCollection();
		
		HasClickHandlers getManagementCenter();
		HasClickHandlers getGiftExchange();
		HasClickHandlers getStaffCorner();
		Panel getDock();
		

		
		void setMessage(String userName);
		void disableManagementCenter();
		void disableGiftExchange();
		void disableStaffCorner();

		HasClickHandlers getAwardShop();

		HasClickHandlers getViewPoints();
		HasClickHandlers getWinninghistory();
		HasClickHandlers getParticipationAwards();
		HasClickHandlers getOtherAwards();
		HasClickHandlers getExchangeHistory();
		HasClickHandlers getMyMessage();
		HasClickHandlers getStaffHeavenIndex();

	}
	
	
}
