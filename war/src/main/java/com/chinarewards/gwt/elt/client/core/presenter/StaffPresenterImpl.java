package com.chinarewards.gwt.elt.client.core.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.EltGinjector;
import com.chinarewards.gwt.elt.client.core.PluginManager;
import com.chinarewards.gwt.elt.client.core.presenter.StaffPresenter.StaffDisplay;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.login.LastLoginRoleRequest;
import com.chinarewards.gwt.elt.client.login.LastLoginRoleResponse;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class StaffPresenterImpl extends BasePresenter<StaffDisplay> implements
		StaffPresenter {
	final PluginManager pluginManager;
	final SessionManager sessionManager;
	final EltGinjector injector;
	final MenuProcessor menuProcessor;
	final DispatchAsync dispatchAsync;
	@Inject
	public StaffPresenterImpl(EventBus eventBus, StaffDisplay display,
			SessionManager sessionManager, PluginManager pluginManager,
			EltGinjector injector, MenuProcessor menuProcessor,DispatchAsync dispatchAsync) {
		super(eventBus, display);
		this.sessionManager = sessionManager;
		this.pluginManager = pluginManager;
		this.injector = injector;
		this.menuProcessor = menuProcessor;
		this.dispatchAsync=dispatchAsync;
	}

	public void bind() {
		registerHandler(display.getlogBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
			}
		}));
		registerHandler(display.getBtnCollection().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("收藏");
			}
		}));

		registerHandler(display.getManagementCenter().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						dispatchAsync.execute(new LastLoginRoleRequest(sessionManager.getSession().getToken(),UserRoleVo.CORP_ADMIN),
								new AsyncCallback<LastLoginRoleResponse>() {
	
									@Override
									public void onFailure(Throwable e) {
									//	Window.alert("系统切换出错");
									}
	
									@Override
									public void onSuccess(LastLoginRoleResponse resp) {
										//成功
										if("success".equals(resp.getFal()))
											GWT.log("success update last login role ");
										
									}
								});
						Window.Location.reload();
					}
				}));
		registerHandler(display.getGiftExchange().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						dispatchAsync.execute(new LastLoginRoleRequest(sessionManager.getSession().getToken(),UserRoleVo.GIFT),
								new AsyncCallback<LastLoginRoleResponse>() {
	
									@Override
									public void onFailure(Throwable e) {
									//	Window.alert("系统切换出错");
									}
	
									@Override
									public void onSuccess(LastLoginRoleResponse resp) {
										//成功
										if("success".equals(resp.getFal()))
											GWT.log("success update last login role ");
										
									}
								});
						Window.Location.reload();
					}
				}));



	}

	public StaffDisplay getDisplay() {
		return display;
	}

}
