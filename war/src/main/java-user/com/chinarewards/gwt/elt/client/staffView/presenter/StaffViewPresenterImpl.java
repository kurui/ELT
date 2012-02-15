package com.chinarewards.gwt.elt.client.staffView.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria.StaffStatus;
import com.chinarewards.gwt.elt.client.staffList.plugin.StaffListConstants;
import com.chinarewards.gwt.elt.client.staffView.request.StaffViewRequest;
import com.chinarewards.gwt.elt.client.staffView.request.StaffViewResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class StaffViewPresenterImpl extends
		BasePresenter<StaffViewPresenter.StaffViewDisplay> implements
		StaffViewPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public StaffViewPresenterImpl(EventBus eventBus, StaffViewDisplay display,
			DispatchAsync dispatch, SessionManager sessionManager, Win win,
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
		breadCrumbs.loadChildPage("员工详细信息");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		//init();
		registerHandler(display.getAddBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						StaffViewRequest request = new StaffViewRequest();
						request.setSession(sessionManager.getSession());

						request.setStaffName(display.getStaffName());
						request.setStaffNo(display.getStaffNo());
						request.setDepartmentId(display.getDepartmentId());
						request.setPhoto(display.getPhoto().getValue());
						request.setJobPosition(display.getJobPosition());
						request.setLeadership(display.getLeadership());
						request.setPhone(display.getPhone());
						request.setEmail(display.getEmail());
						request.setDob(display.getDob().getValue());
						if (display.getStatus_JOB().getValue()) {
							request.setStatus(StaffStatus.JOB);
						} else if (display.getStatus_DEPARTURE().getValue()) {
							request.setStatus(StaffStatus.DEPARTURE);
						} else {
							request.setStatus(StaffStatus.ENTRY);
						}

						dispatch.execute(request,
								new AsyncCallback<StaffViewResponse>() {

									@Override
									public void onFailure(Throwable t) {
										win.alert(t.getMessage());
									}

									@Override
									public void onSuccess(StaffViewResponse resp) {
										win.alert("添加成功");
										Platform.getInstance()
										.getEditorRegistry()
										.openEditor(
												StaffListConstants.EDITOR_STAFFLIST_SEARCH,
												"EDITOR_STAFFLIST_SEARCH_DO_ID", null);
									}
								});
					}
				}));

	}

	

}
