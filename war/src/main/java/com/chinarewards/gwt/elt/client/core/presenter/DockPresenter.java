package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Panel;

public interface DockPresenter extends Presenter<DockPresenter.DockDisplay> {

	public static interface DockDisplay extends Display {
		
		HasClickHandlers getlogBtn();
		
		DockLayoutPanel getDock();
		
		Panel getMenu();
		
		void setMessage(String userName);
	}
	
	
}
