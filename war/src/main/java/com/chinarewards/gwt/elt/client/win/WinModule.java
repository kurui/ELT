package com.chinarewards.gwt.elt.client.win;


import com.chinarewards.gwt.elt.client.win.alert.AlertDialog;
import com.chinarewards.gwt.elt.client.win.alert.AlertPresenter;
import com.chinarewards.gwt.elt.client.win.alert.AlertPresenter.AlertDisplay;
import com.chinarewards.gwt.elt.client.win.alert.AlertPresenterImpl;
import com.chinarewards.gwt.elt.client.win.alert.AlertWidget;
import com.chinarewards.gwt.elt.client.win.alert.StaffAlertDialog;
import com.chinarewards.gwt.elt.client.win.alert.StaffAlertPresenter;
import com.chinarewards.gwt.elt.client.win.alert.StaffAlertPresenter.StaffAlertDisplay;
import com.chinarewards.gwt.elt.client.win.alert.StaffAlertPresenterImpl;
import com.chinarewards.gwt.elt.client.win.alert.StaffAlertWidget;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmDialog;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmPresenter;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmPresenter.ConfirmDisplay;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmPresenterImpl;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmWidget;
import com.chinarewards.gwt.elt.client.win.loginconfirm.LoginConfirmDialog;
import com.chinarewards.gwt.elt.client.win.loginconfirm.LoginConfirmPresenter;
import com.chinarewards.gwt.elt.client.win.loginconfirm.LoginConfirmPresenter.LoginConfirmDisplay;
import com.chinarewards.gwt.elt.client.win.loginconfirm.LoginConfirmPresenterImpl;
import com.chinarewards.gwt.elt.client.win.loginconfirm.LoginConfirmWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class WinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(Win.class);

		bind(ConfirmPresenter.class).to(ConfirmPresenterImpl.class);
		bind(ConfirmDisplay.class).to(ConfirmWidget.class);
		bind(ConfirmDialog.class);

		bind(AlertPresenter.class).to(AlertPresenterImpl.class);
		bind(AlertDisplay.class).to(AlertWidget.class);
		bind(AlertDialog.class);
		
		bind(StaffAlertPresenter.class).to(StaffAlertPresenterImpl.class);
		bind(StaffAlertDisplay.class).to(StaffAlertWidget.class);
		bind(StaffAlertDialog.class);
		
		
		bind(LoginConfirmPresenter.class).to(LoginConfirmPresenterImpl.class);
		bind(LoginConfirmDisplay.class).to(LoginConfirmWidget.class);
		bind(LoginConfirmDialog.class);
	}

}
