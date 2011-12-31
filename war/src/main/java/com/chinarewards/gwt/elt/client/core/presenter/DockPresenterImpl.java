package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.EltGinjector;
import com.chinarewards.gwt.elt.client.core.PluginManager;
import com.chinarewards.gwt.elt.client.core.presenter.DockPresenter.DockDisplay;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class DockPresenterImpl extends BasePresenter<DockDisplay> implements
		DockPresenter {
	final PluginManager pluginManager;
	final SessionManager sessionManager;
	final EltGinjector injector;
	final MenuProcessor menuProcessor;

	@Inject
	public DockPresenterImpl(EventBus eventBus, DockDisplay display,
			SessionManager sessionManager,PluginManager pluginManager,EltGinjector injector,MenuProcessor menuProcessor) {
		super(eventBus, display);
		this.sessionManager = sessionManager;
		this.pluginManager=pluginManager;
		this.injector=injector;
this.menuProcessor=menuProcessor;
	}

	public void bind() {
		registerHandler(display.getlogBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
			}
		}));
		registerHandler(display.getBtnEmail().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenu(null);
			}
		}));
		registerHandler(display.getBtnGb().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
		
				menuProcessor.initrender(display.getMenu(),"xx");
				
				
			}
		}));
		
	}

	public DockDisplay getDisplay() {
		return display;
	}

}
