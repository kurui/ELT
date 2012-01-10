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
				display.setMenuTitle("收件箱");
				display.setMenu(null);
			}
		}));
		registerHandler(display.getBtnGb().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenuTitle("广播");
				display.setMenu(null);
			}
		}));
		registerHandler(display.getBtnRewardItem().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenuTitle("奖项");
				menuProcessor.initrender(display.getMenu(),"RewardItem");	
			}
		}));
		registerHandler(display.getBtnReward().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenuTitle("应用奖项");
				menuProcessor.initrender(display.getMenu(),"Reward");	
			}
		}));
		registerHandler(display.getBtnStaff().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenuTitle("员工数据");
				menuProcessor.initrender(display.getMenu(),"Staff");	
			}
		}));
		registerHandler(display.getBtnSetting().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenuTitle("设置");
				menuProcessor.initrender(display.getMenu(),"Setting");	
			}
		}));
		registerHandler(display.getBtnGift().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenuTitle("兑换管理");
				menuProcessor.initrender(display.getMenu(),"Gift");	
			}
		}));
		
	}

	public DockDisplay getDisplay() {
		return display;
	}

}
