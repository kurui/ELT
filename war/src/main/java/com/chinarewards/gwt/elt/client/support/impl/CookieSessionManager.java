package com.chinarewards.gwt.elt.client.support.impl;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.ui.event.PlatformInitEvent;
import com.chinarewards.gwt.elt.client.login.LoginRequest;
import com.chinarewards.gwt.elt.client.login.LoginResponse;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.login.event.LoginHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.support.UserSession;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * Provide LoginEvent
 * 
 * @author kmtong
 * 
 */
public class CookieSessionManager implements SessionManager {

	final UserSession session;
	final EventBus eventBus;
	final DispatchAsync dispatchAsync;

	List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();

	@Inject
	public CookieSessionManager(UserSession session, final EventBus eventBus,
			DispatchAsync dispatchAsync) {
		this.session = session;
		this.eventBus = eventBus;
		this.dispatchAsync = dispatchAsync;
	}

	public void authenticate(String username, String password,String verifyCode) {
		if (null == username || username.trim().equals("")) {
			Window.alert("账号不能为空!");
			return;
		}
		if (null == password || password.trim().equals("")) {
			Window.alert("密码不能为空!");
			return;
		}
		
		LoginRequest req = new LoginRequest(username, password,verifyCode);
		dispatchAsync.execute(req, new AsyncCallback<LoginResponse>() {

			@Override
			public void onFailure(Throwable e) {
				tokenObtained(null);
				Window.alert("Failed");
				eventBus.fireEvent(new LoginEvent(
						LoginEvent.LoginStatus.LOGIN_FAILED, e));
			}

			@Override
			public void onSuccess(LoginResponse resp) {
				tokenObtained(resp.getToken());
				Window.alert(resp.getToken());
				eventBus.fireEvent(new LoginEvent(
						LoginEvent.LoginStatus.LOGIN_OK));
			}
		});
	}

	public void logout() {
		this.session.setToken(null);
		eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
	}

	public UserSession getSession() {
		return session;
	}

	public void registerLoginEventHandler(LoginHandler handler) {
		registerHandler(eventBus.addHandler(LoginEvent.getType(), handler));
	}

	protected void registerHandler(HandlerRegistration handlerRegistration) {
		handlerRegistrations.add(handlerRegistration);
	}

	public void bind() {
		GWT.log("SessionManager Bind");
		String token = Cookies.getCookie("token");
		if (token != null) {
			// userService.reauthenticate(token, new AsyncCallback<String>() {
			// public void onFailure(Throwable e) {
			// Window.alert("Login Error: " + e.getMessage());
			// tokenObtained(null);
			// // no login token in cookie
			// eventBus.fireEvent(new LoginEvent(
			// LoginEvent.LoginStatus.LOGIN_EXPIRED));
			// }
			//
			// public void onSuccess(String token) {
			// tokenObtained(token);
			// eventBus.fireEvent(new LoginEvent(
			// LoginEvent.LoginStatus.LOGIN_OK));
			// }
			// });
			eventBus.fireEvent(new LoginEvent(
					LoginEvent.LoginStatus.LOGOUT));
		} else {
			eventBus.fireEvent(new LoginEvent(
					LoginEvent.LoginStatus.LOGOUT));
		}
	}

	protected void tokenObtained(String token) {
		if (token != null) {
			session.setToken(token);
			Cookies.setCookie("token", token);
		} else {
			session.setToken(null);
			Cookies.removeCookie("token");
		}
	}

	public void resetLogin() {
		tokenObtained(null);
	}
	
	@Override
	public void initialize() {
		// Check Cookie Validity
				GWT.log("Initializing SessionManager...");
				String token = Cookies.getCookie("token");
				System.out.println("token=========="+token);
				if (null != token && !token.trim().equals("")) {
					// check the token value.
					eventBus.fireEvent(new PlatformInitEvent(true));
				} else {
					eventBus.fireEvent(new PlatformInitEvent(false));
				}

	}


}
