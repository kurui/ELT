package com.chinarewards.gwt.elt.client.password.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffList.request.UpdateUserPwdRequest;
import com.chinarewards.gwt.elt.client.staffList.request.UpdateUserPwdResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class PasswordPresenterImpl extends	BasePresenter<PasswordPresenter.PasswordDisplay> implements	PasswordPresenter {

	final DispatchAsync dispatcher;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;


	@Inject
	public PasswordPresenterImpl(EventBus eventBus,ErrorHandler errorHandler, SessionManager sessionManager,
			PasswordDisplay display, DispatchAsync dispatcher,Win win	) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
	}

	@Override
	public void bind() {
		display.setUsername(sessionManager.getSession().getLoginName());
		registerHandler(display.getPasswordClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
												
						if (StringUtil.isEmpty(display.getNewPassword().getValue())) {
							win.alert("新密码不能为空!<br>");
							return;
						}
						
						if (!display.getNewPassword().getValue().equals(display.getValidatePassword().getValue())) {
							win.alert("新密码和确认密码不一致!<br>");
							return;
						}
						
						doPassword();
					}
				}));
	}

	protected void doPassword() {
	
		if(sessionManager.getSession()!=null){
		dispatcher.execute(new UpdateUserPwdRequest(sessionManager.getSession().getStaffId(),display.getNewPassword().getValue(),sessionManager.getSession()),
				new AsyncCallback<UpdateUserPwdResponse>() {
					public void onFailure(Throwable t) {
						win.alert("修改失败");
					}

					@Override
					public void onSuccess(UpdateUserPwdResponse response) {
                        if(response.getMessage().equals("success"))
						   win.alert("修改成功!");
                        else
                        	win.alert("修改失败");
					}
				});
	    }else{
		win.alert("请重新登录!");
	    }
		
	}
	
}
