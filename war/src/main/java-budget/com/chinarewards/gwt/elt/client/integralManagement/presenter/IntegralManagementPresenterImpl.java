package com.chinarewards.gwt.elt.client.integralManagement.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.integralManagement.presenter.IntegralManagementPresenter.IntegralManagementDisplay;
import com.chinarewards.gwt.elt.client.integralManagement.request.IntegralManagementRequest;
import com.chinarewards.gwt.elt.client.integralManagement.request.IntegralManagementResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class IntegralManagementPresenterImpl extends
		BasePresenter<IntegralManagementDisplay> implements
		IntegralManagementPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	@Inject
	public IntegralManagementPresenterImpl(EventBus eventBus,
			DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager, IntegralManagementDisplay display,
			Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;

	}

	@Override
	public void bind() {

		dispatch.execute(new IntegralManagementRequest(sessionManager.getSession().getCorporationId()),
				new AsyncCallback<IntegralManagementResponse>() {
					@Override
					public void onFailure(Throwable e) {
						win.alert(e.getMessage());
					}

					@Override
					public void onSuccess(IntegralManagementResponse response) {
						    	   
						    	   display.refresh(response.getResult(),sessionManager.getSession().getCorporationId());
					}
				});
	
	 
	}

}
