package com.chinarewards.gwt.elt.client.staff.view;


import com.chinarewards.gwt.elt.client.staff.presenter.HrRegisterPresenter.HrRegisterDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class HrRegisterWidget extends Composite implements HrRegisterDisplay {

	@UiField
	Button hrRegisterbutton;
	@UiField
	TextBox email;
	@UiField
	TextBox username;
	@UiField
	TextBox password;
	@UiField
	TextBox validatePassword;
	@UiField
	TextBox name;
	@UiField
	TextBox tell;
	
	
	private static HrRegisterWidgetUiBinder uiBinder = GWT
			.create(HrRegisterWidgetUiBinder.class);

	interface HrRegisterWidgetUiBinder extends
			UiBinder<Widget, HrRegisterWidget> {
	}

	public HrRegisterWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public HasClickHandlers getHrRegisterClickHandlers() {
		return hrRegisterbutton;
	}

	@Override
	public HasValue<String> getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public HasValue<String> getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public HasValue<String> getTell() {
		// TODO Auto-generated method stub
		return tell;
	}

	@Override
	public HasValue<String> getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public HasValue<String> getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public HasValue<String> getValidatePassword() {
		// TODO Auto-generated method stub
		return validatePassword;
	}

}
