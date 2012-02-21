package com.chinarewards.gwt.elt.client.broadcastReply.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class BroadcastReplyPresenterImpl extends
		BasePresenter<BroadcastReplyPresenter.BroadcastReplyDisplay> implements
		BroadcastReplyPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;
	String broadcastId = null;
	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public BroadcastReplyPresenterImpl(
			EventBus eventBus,
			BroadcastReplyDisplay display,
			DispatchAsync dispatch,
			SessionManager sessionManager,
			Win win,
			BreadCrumbsPresenter breadCrumbs,
			ErrorHandler errorHandler) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;
		this.breadCrumbs = breadCrumbs;

	}

	@Override
	public void bind() {

		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

		registerHandler(display.getSaveBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						win.alert("添加");

					}
				}));

	}

	@Override
	public void initBroadcast(String broadcastId) {
		this.broadcastId = broadcastId;
	}

}
