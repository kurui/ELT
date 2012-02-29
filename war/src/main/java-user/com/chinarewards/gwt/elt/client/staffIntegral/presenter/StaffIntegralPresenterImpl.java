package com.chinarewards.gwt.elt.client.staffIntegral.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffIntegral.request.StaffIntegralRequest;
import com.chinarewards.gwt.elt.client.staffIntegral.request.StaffIntegralResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class StaffIntegralPresenterImpl extends
		BasePresenter<StaffIntegralPresenter.StaffIntegralDisplay> implements
		StaffIntegralPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public StaffIntegralPresenterImpl(EventBus eventBus,
			StaffIntegralDisplay display, DispatchAsync dispatch,
			SessionManager sessionManager, Win win,
			BreadCrumbsPresenter breadCrumbs, ErrorHandler errorHandler) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("查看积分");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		init();
	}

	void init() {
		String staffId = sessionManager.getSession().getStaffId();
		dispatch.execute(new StaffIntegralRequest(staffId),
				new AsyncCallback<StaffIntegralResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(StaffIntegralResponse resp) {
						display.getHistoryIntegral().setText(resp.getHistoryIntegral());
						display.getConsumptionIntegral().setText(resp.getConsumptionIntegral());
						display.getBalanceIntegral().setText(resp.getBalanceIntegral());

					}
				});
	}

}
