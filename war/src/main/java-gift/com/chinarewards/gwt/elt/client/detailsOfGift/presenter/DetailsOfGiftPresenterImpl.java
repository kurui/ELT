package com.chinarewards.gwt.elt.client.detailsOfGift.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.detailsOfGift.model.DetailsOfGiftClient;
import com.chinarewards.gwt.elt.client.detailsOfGift.presenter.DetailsOfGiftPresenter.DetailsOfGiftDisplay;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.inject.Inject;

public class DetailsOfGiftPresenterImpl extends BasePresenter<DetailsOfGiftDisplay>
		implements DetailsOfGiftPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	DetailsOfGiftClient orderVo;

	@Inject
	public DetailsOfGiftPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			DetailsOfGiftDisplay display, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;

	}

	@Override
	public void bind() {
		init();

	}

	private void init() {
		
	}

	@Override
	public void initDetailsOfGift(DetailsOfGiftClient orderVo) {
		this.orderVo=orderVo;
		
	}



}
