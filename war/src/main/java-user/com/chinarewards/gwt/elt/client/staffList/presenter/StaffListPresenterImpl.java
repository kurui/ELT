package com.chinarewards.gwt.elt.client.staffList.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.inject.Inject;

public class StaffListPresenterImpl extends
		BasePresenter<StaffListPresenter.StaffListDisplay> implements
		StaffListPresenter {

	private final DispatchAsync dispatcher;
	private final SessionManager sessionManager;
	private final Win win;


	@Inject
	public StaffListPresenterImpl(EventBus eventBus,
			StaffListDisplay display, DispatchAsync dispatcher,
			SessionManager sessionManager,Win win) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.sessionManager = sessionManager;
		this.win=win;
	}

	@Override
	public void bind() {
	
	}

	

}
