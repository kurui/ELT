package com.chinarewards.gwt.elt.client.budget.presenter;


import java.util.Map;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface AskBudgetListPresenter extends Presenter<AskBudgetListPresenter.AskBudgetListDisplay> {

      
	public static interface AskBudgetListDisplay extends Display {

		
		public HasClickHandlers getSearchBtnClickHandlers();
		HasClickHandlers getAddBtnClickHandlers();
		void setTotalCount(String text);
		void setRemainCount(String text);
		
		String getYear();
		
		Panel getResultPanel();
		Panel getResultpage();
		public void initYear(Map<String, String> map);
		public void initDepart(Map<String, String> map);
		String getDepart();
		void setBreadCrumbs(Widget breadCrumbs);
		ListBox getPageNumber();
		void setAddBtnShow();
	}
}
