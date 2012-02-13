package com.chinarewards.gwt.elt.client.integralManagement.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.integralManagement.presenter.IntegralManagementPresenter.IntegralManagementDisplay;
import com.chinarewards.gwt.elt.client.integralManagement.request.IntegralManagementRequest;
import com.chinarewards.gwt.elt.client.integralManagement.request.IntegralManagementResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class IntegralManagementPresenterImpl extends
		BasePresenter<IntegralManagementDisplay> implements
		IntegralManagementPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	private final BreadCrumbsPresenter breadCrumbs;
	@Inject
	public IntegralManagementPresenterImpl(EventBus eventBus,
			DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager, IntegralManagementDisplay display,
			Win win,BreadCrumbsPresenter breadCrumbs) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs=breadCrumbs;

	}

	@Override
	public void bind() {
		breadCrumbs.loadListPage();
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		
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
		registerHandler(display.getNominateClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("待实现");
//						RewardsPageClient rpc=new RewardsPageClient();
//						rpc.setTitleName("待提名奖项");
//						rpc.setPageType(RewardPageType.NOMINATEPAGE);
//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor(
//										RewardsListConstants.EDITOR_REWARDSLIST_SEARCH,
//										"EDITOR_REWARDSLIST_"+RewardPageType.NOMINATEPAGE,rpc);
					}
				}));

	 
	}

}
