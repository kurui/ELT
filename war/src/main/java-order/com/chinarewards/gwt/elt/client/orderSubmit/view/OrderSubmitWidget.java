package com.chinarewards.gwt.elt.client.orderSubmit.view;

import com.chinarewards.gwt.elt.client.orderSubmit.presenter.OrderSubmitPresenter.OrderSubmitDisplay;
import com.chinarewards.gwt.elt.client.view.constant.CssStyleConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class OrderSubmitWidget extends Composite implements
		OrderSubmitDisplay {

	@UiField
	Button confirmbutton;
	@UiField
	Button returnbutton;
	
	@UiField
	TextBox name;
	@UiField
	TextBox phone;
	@UiField
	TextBox address;
	@UiField
	TextBox zipCode;
	@UiField
	Anchor shopText;
	@UiField
	InlineLabel unitprice;
	@UiField
	TextBox number;
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
	InlineLabel business;
	@UiField
	InlineLabel servicetell;
	@UiField
	InlineLabel specialNote;
	
	private static OrderSubmitWidgetUiBinder uiBinder = GWT
			.create(OrderSubmitWidgetUiBinder.class);

	interface OrderSubmitWidgetUiBinder extends
			UiBinder<Widget, OrderSubmitWidget> {
	}

	public OrderSubmitWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public HasValue<String> getName() {
		return name;
	}

	@Override
	public HasValue<String> getPhone() {
		return phone;
	}

	@Override
	public HasValue<String> getAddress() {
		return address;
	}

	@Override
	public HasValue<String> getZipCode() {
		return zipCode;
	}

	@Override
	public HasValue<String> getNumber() {
		return number;
	}

	@Override
	public Image getShopImage() {
		return shopImage;
	}

	@Override
	public void setShopText(String text) {
		shopText.setText(text);
		
	}

	@Override
	public String getUnitprice() {
		return unitprice.getText();
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
	public TextBox getNumberChange() {
		return number;
	}

	@Override
	public void setTotal(String total) {
		this.total.setText(total);		
	}

	@Override
	public void setUnitprice(String unitprice) {
		this.unitprice.setText(unitprice);
		
	}

	@Override
	public void setSource(String source) {
		if("inner".equals(source))
		{
			this.source.setText("内部直接提供");
		}
		else if("outter".equals(source))
		{
			this.source.setText("外部货品公司提供");
		}
		
	}

	@Override
	public void setNumber(String number) {
		this.number.setText(number);
		
	}


	@Override
	public InlineLabel getMessage() {
		return message;
	}


	@Override
	public Button getConfirmbuttonObj() {
		return confirmbutton;
	}


	@Override
	public void setMybalance(String mybalance) {
		this.mybalance.setText(mybalance);		
	}


	@Override
	public HasValue<String> getOrderDefinition() {
		return orderDefinition;
	}


	@Override
	public void setName(String text) {
	name.setText(text);
		
	}


	@Override
	public void setPhone(String text) {
		phone.setText(text);
		
	}


	@Override
	public void setAddress(String text) {
	address.setText(text);
		
	}


	@Override
	public void setZipCode(String text) {
		zipCode.setText(text);
		
	}


	@Override
	public void setOrderDefinition(String text) {
		orderDefinition.setText(text);
		
	}


	@Override
	public HasClickHandlers getShopText() {
		return shopText;
	}



	@Override
	public void setBusiness(String text) {
		business.setText(text);
	}


	@Override
	public void setServicetell(String text) {
		servicetell.setText(text);
	}


	@Override
	public void disableSpecialNote() {
		business.getElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
		specialNote.setVisible(false);
	}




	



}
