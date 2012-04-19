package com.chinarewards.gwt.elt.client.win.loginconfirm;


import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.win.confirm.WinEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class LoginConfirmPresenterImpl extends
		BaseDialogPresenter<LoginConfirmPresenter.LoginConfirmDisplay> implements LoginConfirmPresenter {

	@Inject
	public LoginConfirmPresenterImpl(EventBus eventBus, LoginConfirmPresenter.LoginConfirmDisplay display) {
		super(eventBus, display);
	}

	@Override
	public void bind() {
		registerHandler(display.getOkBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new WinEvent());
//				closeDialog();
			}
		}));

	}
}
