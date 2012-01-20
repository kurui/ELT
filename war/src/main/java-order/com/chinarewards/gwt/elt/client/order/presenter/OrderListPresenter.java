package com.chinarewards.gwt.elt.client.order.presenter;


import java.util.Map;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;

public interface OrderListPresenter extends Presenter<OrderListPresenter.OrderListDisplay> {


	public static interface OrderListDisplay extends Display {

		public HasClickHandlers getSearchBtnClickHandlers();
		public HasClickHandlers getAddBtnClickHandlers();
		public HasClickHandlers getimportingBtnClickHandlers();
		void setDataCount(String text);
		HasValue<String> getKeyName();
		String getStatus();
		Panel getResultPanel();
		Panel getResultpage();
		public void initOrderStatus(Map<String, String> map);

	}
}
