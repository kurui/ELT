package com.chinarewards.gwt.elt.client.order.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenter;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface OrderViewPresenter extends
		Presenter<OrderViewPresenter.OrderViewDisplay> {
	void initInstanceId(String instanceId, OrderVo item);

	public static interface OrderViewDisplay extends Display {

		public HasClickHandlers getBackClick();

		public HasClickHandlers getUpdateClick();

		public void showOrder(OrderVo orderVo);

	}
}
