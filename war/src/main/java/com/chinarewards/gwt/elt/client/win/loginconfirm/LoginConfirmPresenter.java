package com.chinarewards.gwt.elt.client.win.loginconfirm;


import com.chinarewards.gwt.elt.client.mvp.DialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface LoginConfirmPresenter extends DialogPresenter<LoginConfirmPresenter.LoginConfirmDisplay> {

	public static interface LoginConfirmDisplay extends Display {
		HasClickHandlers getOkBtn();
		String getLoginType();

	}

}
