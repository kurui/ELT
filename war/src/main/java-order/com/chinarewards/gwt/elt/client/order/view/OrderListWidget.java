package com.chinarewards.gwt.elt.client.order.view;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.chinarewards.gwt.elt.client.order.view.OrderListWidget;
import com.chinarewards.gwt.elt.client.order.presenter.OrderListPresenter.OrderListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class OrderListWidget extends Composite implements OrderListDisplay {
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;
	
	@UiField
	Button searchBtn;
	@UiField
	Button addBtn;
	@UiField
	Button importingBtn;
	
	@UiField
	TextBox keyName;
	@UiField
	ListBox status;
	@UiField
	InlineLabel dataCount;
	
	private static OrderWidgetUiBinder uiBinder = GWT
			.create(OrderWidgetUiBinder.class);

	interface OrderWidgetUiBinder extends UiBinder<Widget, OrderListWidget> {
	}

	public OrderListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getSearchBtnClickHandlers() {
		return searchBtn;
	}

	@Override
	public Panel getResultPanel() {
		return resultPanel;
	}


	@Override
	public HasValue<String> getKeyName() {
		return keyName;
	}

	@Override
	public Panel getResultpage() {
		return resultpage;
	}

	@Override
	public String getStatus() {
		return status.getValue(status.getSelectedIndex());
	}

	@Override
	public void initOrderStatus(Map<String, String> map) {

		status.addItem("不限", "");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			status.addItem(entry.getValue(), entry.getKey());
		}
	}

	@Override
	public HasClickHandlers getAddBtnClickHandlers() {
		return addBtn;
	}

	@Override
	public HasClickHandlers getimportingBtnClickHandlers() {
		return importingBtn;
	}

	@Override
	public void setDataCount(String text) {
		dataCount.setText(text);
		
	}
}