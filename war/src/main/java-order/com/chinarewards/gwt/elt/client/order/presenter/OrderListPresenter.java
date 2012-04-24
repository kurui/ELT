package com.chinarewards.gwt.elt.client.order.presenter;


import java.util.Map;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface OrderListPresenter extends Presenter<OrderListPresenter.OrderListDisplay> {

       void getBoxOrder(String status);
	public static interface OrderListDisplay extends Display {

		public HasClickHandlers getSearchBtnClickHandlers();
		void setDataCount(String text);
		HasValue<String> getKeyName();
		String getStatus();
		void setStatus(String status);
		Panel getResultPanel();
		Panel getResultpage();
		HasClickHandlers getExportBtnClickHandlers();
		public void initOrderSource(Map<String, String> map);
		String getSource();
		void setBreadCrumbs(Widget breadCrumbs);
		ListBox getPageNumber();
	}
}
