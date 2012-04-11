package com.chinarewards.gwt.elt.client.staffList.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.inject.Inject;

public class ImportStaffPresenterImpl extends
		BaseDialogPresenter<ImportStaffPresenter.ImportStaffDisplay> implements
		ImportStaffPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;



	@Inject
	public ImportStaffPresenterImpl(EventBus eventBus,
			ImportStaffDisplay display, DispatchAsync dispatch,
			SessionManager sessionManager,Win win,ErrorHandler errorHandler) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler=errorHandler;
		this.win=win;
	
	}

	@Override
	public void bind() {
	
	}
	


}
