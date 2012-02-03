package com.chinarewards.gwt.elt.client.orderHistory.view;

import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.orderHistory.presenter.OrderHistoryViewPresenter.OrderHistoryViewDisplay;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class OrderHistoryViewWidget extends Composite implements
		OrderHistoryViewDisplay {
	@UiField
	Button confirmbutton;
	@UiField
	Button returnbutton;

	@UiField
	Label orderCode;
	@UiField
	Label exchangeDate;
	@UiField
	Label statusText;

	@UiField
	Label receiver;
	@UiField
	Label tel;
	@UiField
	Label address;
	@UiField
	Label postcode;
	
	@UiField
	Anchor shopText;
	@UiField
	InlineLabel unitprice;
	@UiField
	Label number;
	@UiField
	Image shopImage;
	@UiField
	InlineLabel total;
	@UiField
	InlineLabel source;
	@UiField
	InlineLabel message;
	@UiField
	InlineLabel mybalance;
	@UiField
	TextArea orderDefinition;

	@UiField
	Panel breadCrumbs;
	
	private static OrderHistoryViewWidgetUiBinder uiBinder = GWT
			.create(OrderHistoryViewWidgetUiBinder.class);

	interface OrderHistoryViewWidgetUiBinder extends
			UiBinder<Widget, OrderHistoryViewWidget> {
	}

	public OrderHistoryViewWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void showOrderHistory(OrderVo orderVo) {
		orderCode.setText(orderVo.getOrderCode());
		exchangeDate.setText(DateTool.dateToString(orderVo.getExchangeDate()));
		
		statusText.setText(orderVo.getStatus().getDisplayName());

		receiver.setText(orderVo.getReceiver());
		tel.setText(orderVo.getTel());

		address.setText(orderVo.getAddress());
		postcode.setText(orderVo.getPostcode());
		
//		shopImage.setUrl("imageshow?imageName="+orderVo.get);
//		shopText.setText(orderVo.getGiftName());
		total.setText(orderVo.getIntegral()+"");
		unitprice.setText(orderVo.getIntegral()+"");
//		source.setText(orderVo.getSource());
//		number.setText(orderVo.getNumber()+"");
	}

	@Override
	public HasClickHandlers getConfirmbutton() {
		return confirmbutton;
	}

	@Override
	public HasClickHandlers getReturnbutton() {
		return returnbutton;
	}

	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

}
