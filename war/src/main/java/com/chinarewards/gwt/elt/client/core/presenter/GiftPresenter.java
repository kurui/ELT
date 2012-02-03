package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public interface GiftPresenter extends Presenter<GiftPresenter.GiftDisplay> {

	public static interface GiftDisplay extends Display {
		
		HasClickHandlers getlogBtn();

		HasClickHandlers getBtnCollection();
		
		HasClickHandlers getManagementCenter();
		HasClickHandlers getGiftExchange();
		HasClickHandlers getStaffCorner();
		DockLayoutPanel getDock();
		

		
		void setMessage(String userName);

	}
	
	
}
