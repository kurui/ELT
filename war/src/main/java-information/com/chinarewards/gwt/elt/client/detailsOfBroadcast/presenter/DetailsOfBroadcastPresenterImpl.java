package com.chinarewards.gwt.elt.client.detailsOfBroadcast.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.broadcastSave.plugin.BroadcastSaveConstants;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.detailsOfBroadcast.request.DetailsOfBroadcastRequest;
import com.chinarewards.gwt.elt.client.detailsOfBroadcast.request.DetailsOfBroadcastResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class DetailsOfBroadcastPresenterImpl extends
		BasePresenter<DetailsOfBroadcastPresenter.DetailsOfBroadcastDisplay>
		implements DetailsOfBroadcastPresenter {

	private final DispatchAsync dispatch;
//	private final SessionManager sessionManager;
//	private final Win win;
	final ErrorHandler errorHandler;
	String broadcastId = null;
	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public DetailsOfBroadcastPresenterImpl(EventBus eventBus,
			DetailsOfBroadcastDisplay display, DispatchAsync dispatch,
			//SessionManager sessionManager, Win win,
			BreadCrumbsPresenter breadCrumbs, ErrorHandler errorHandler) {
		super(eventBus, display);
		this.dispatch = dispatch;
	//	this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
	//	this.win = win;
		this.breadCrumbs = breadCrumbs;

	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("广播详细");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		init();

		registerHandler(display.getUpdateBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										BroadcastSaveConstants.EDITOR_BROADCASTSAVE_SEARCH,
										"EDITOR_BROADCASTSAVE_SEARCH_DO_ID",
										broadcastId);

					}
				}));

	}

	private void init() {
		dispatch.execute(new DetailsOfBroadcastRequest(broadcastId),
				new AsyncCallback<DetailsOfBroadcastResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(DetailsOfBroadcastResponse response) {
						display.setAllowreplies(response.getAllowreplies());
						display.setBroadcastingTime(response.getBroadcastingTime());
						display.setContent(response.getContent());
						display.setReceivingObject(response.getReceivingObject());
						display.setCreateUser(response.getCreateUser());
					}

				});

	}

	@Override
	public void initBroadcastDetails(String broadcastId) {
		this.broadcastId = broadcastId;
	}

}
