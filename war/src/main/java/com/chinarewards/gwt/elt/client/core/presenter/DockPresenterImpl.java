package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.core.presenter.DockPresenter.DockDisplay;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class DockPresenterImpl extends BasePresenter<DockDisplay> implements
		DockPresenter {
	final SessionManager sessionManager;

	@Inject
	public DockPresenterImpl(EventBus eventBus, DockDisplay display,
			SessionManager sessionManager) {
		super(eventBus, display);
		this.sessionManager = sessionManager;
	}

	public void bind() {
		registerHandler(display.getlogBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
			}
		}));
	}

	public DockDisplay getDisplay() {
		return display;
	}

}
