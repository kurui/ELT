package com.chinarewards.gwt.elt.client.win.alert;


import com.chinarewards.gwt.elt.client.mvp.DialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface StaffAlertPresenter extends
		DialogPresenter<StaffAlertPresenter.StaffAlertDisplay> {

	public static interface StaffAlertDisplay extends Display {
		HasClickHandlers getOkBtn();

		void setMsg(String msg);
		void setImage(String url);
	}

}
