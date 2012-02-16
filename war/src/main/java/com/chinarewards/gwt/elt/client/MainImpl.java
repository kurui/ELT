package com.chinarewards.gwt.elt.client;

import com.chinarewards.gwt.elt.client.core.ui.event.PlatformInitEvent;
import com.chinarewards.gwt.elt.client.core.ui.event.PlatformInitHandler;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.login.event.LoginHandler;
import com.chinarewards.gwt.elt.client.login.presenter.LoginPresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;

public class MainImpl implements Main, PlatformInitHandler, LoginHandler {

	final EltGinjector injector;
	final SessionManager sessionManager;
	final EventBus eventBus;
    final Win win;
	RootLayoutPanel rootLayoutPanel;
	LoginPresenter login;
	@Inject
	public MainImpl(EltGinjector injector, SessionManager sessionManager,
			EventBus eventBus,Win win) {
		this.injector = injector;
		this.sessionManager = sessionManager;
		this.eventBus = eventBus;
	    this.win = win;
		login = injector.getLoginPresenter();
	}

	public void init(RootLayoutPanel panel) {
		GWT.log("Main Initializing...");
		this.rootLayoutPanel = panel;
		eventBus.addHandler(PlatformInitEvent.getType(), this);
		eventBus.addHandler(LoginEvent.getType(), this);

		// let session manager to determine the login status
		// if (GWT.isScript()) {
		// // running in hosted mode.
		// // eventBus.fireEvent(new LoginEvent(LoginStatus.LOGIN_OK));
		// GWT.log("Running in hosted mode, faking login...");
		// eventBus.fireEvent(new PlatformInitEvent(true));
		// } else {
		// sessionManager.bind(); // this will show the login panel
		// }
	//	login.bind();
	//	rootLayoutPanel.add(login.getDisplay().asWidget());
		sessionManager.initialize();
	}

	public void onInit(boolean loggedIn) {
		rootLayoutPanel.clear();
		if (!loggedIn) {
			login.bind();
			rootLayoutPanel.add(login.getDisplay().asWidget());
		} else {
			login.unbind();
			UserRoleVo role=sessionManager.getSession().getLastLoginRole();
			if(role==UserRoleVo.CORP_ADMIN)
				injector.getPlatform().initialize(injector.getPluginSetAdmin(),rootLayoutPanel);
			else if( role==UserRoleVo.DEPT_MGR)
				injector.getPlatform().initialize(injector.getPluginSetDept(),rootLayoutPanel);
			else if(role==UserRoleVo.STAFF)
				injector.getPlatform().initializeStaff(injector.getPluginSetStaff(),rootLayoutPanel);
			else if(role==UserRoleVo.GIFT)
				injector.getPlatform().initializeGift(injector.getPluginSetGift(),rootLayoutPanel);
			
		}
	}

	public void onLogin(LoginEvent event) {
		switch (event.getStatus()) {
		case LOGIN_OK:
			rootLayoutPanel.clear();
			login.unbind();
			injector.getPlatform().initialize(injector.getPluginSetAdmin(),rootLayoutPanel);
			break;
		case LOGIN_OK_DEPT:
			rootLayoutPanel.clear();
			login.unbind();
			injector.getPlatform().initialize(injector.getPluginSetDept(),rootLayoutPanel);
			break;	
		case LOGIN_OK_GIFT:
			rootLayoutPanel.clear();
			login.unbind();
			injector.getPlatform().initializeGift(injector.getPluginSetGift(),rootLayoutPanel);
			break;
		case LOGIN_OK_STAFF:
			rootLayoutPanel.clear();
			login.unbind();
			injector.getPlatform().initializeStaff(injector.getPluginSetStaff(),rootLayoutPanel);
			break;
		case LOGIN_FAILED:
			win.alert("登录失败，请重试！");
			break;
		case LOGIN_EXPIRED:
		case LOGOUT:
			// if (!GWT.isScript()) {
			// break;
			// }
			// win.alert("Logout event received");
			// sessionManager.logout();
			sessionManager.resetLogin();
			Window.Location.reload();
			// rootLayoutPanel.clear();
			// login.bind();
			// rootLayoutPanel.add(login.getDisplay().asWidget());
			break;

		}
	}

}
