/**
 * 
 */
package com.chinarewards.gwt.elt.client.user.presenter;

import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.user.dp.UserSearchAsyncDataProvider;
import com.chinarewards.gwt.elt.client.user.model.UserSearchVo;
import com.chinarewards.gwt.elt.client.user.presenter.UserSearchPresenter.UserSearchDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

/**
 * User search presenter impl.
 * 
 * @author yanxin
 * @since 0.0.1 2010-09-25
 */
public class UserSearchPresenterImpl extends BasePresenter<UserSearchDisplay>
		implements UserSearchPresenter {

	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final SessionManager sessionManager;

	@Inject
	public UserSearchPresenterImpl(EventBus eventBus,
			UserSearchDisplay display, DispatchAsync dispatchAsync,
			ErrorHandler errorHandler, SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatcher = dispatchAsync;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
	}

	@Override
	public void bind() {
		// doSearch();
		init();
		// search btn
		registerHandler(display.getSearchHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						GWT.log("running click handlers. search");
						Window.alert("开始查询");
						doSearch();
					}
				}));
	}

	void init() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Active", "已激活");
		map.put("Inactive", "已注销");
		display.initUserStatus(map);
		doSearch();
	}

	private void doSearch() {
		UserSearchVo searchVo = new UserSearchVo();
		searchVo.setAccountName(display.getAccountName().getValue());
		searchVo.setEmail(display.getEmail().getValue());
		searchVo.setMobile(display.getMobile().getValue());
		searchVo.setStatus(display.getStatus());
		searchVo.setEnterpriseId(display.getEnterpriseId());
		UserSearchAsyncDataProvider listViewAdapter = new UserSearchAsyncDataProvider(
				searchVo, errorHandler, sessionManager, dispatcher);
		display.setListViewAdapter(listViewAdapter);
	}
}
