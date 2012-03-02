package com.chinarewards.gwt.elt.client.staffInfo.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffAdd.plugin.StaffAddConstants;
import com.chinarewards.gwt.elt.client.staffView.request.StaffViewRequest;
import com.chinarewards.gwt.elt.client.staffView.request.StaffViewResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class StaffInfoPresenterImpl extends
		BasePresenter<StaffInfoPresenter.StaffInfoDisplay> implements
		StaffInfoPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;


	String staffId;
	boolean colleague=false;
	EltNewPager pager;
	
	@Inject
	public StaffInfoPresenterImpl(EventBus eventBus, StaffInfoDisplay display,
			DispatchAsync dispatch, SessionManager sessionManager, Win win,
 ErrorHandler errorHandler) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;
		
	}

	@Override
	public void bind() {
		
		init();
		registerHandler(display.getupadateBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								StaffAddConstants.EDITOR_STAFFADD_SEARCH,
								"EDITOR_STAFFADD_SEARCH_DO_ID", staffId);
					}
				}));

	}

	void init() {
		
		dispatch.execute(new StaffViewRequest(sessionManager.getSession().getStaffId()),
				new AsyncCallback<StaffViewResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(StaffViewResponse resp) {
						
						display.setStaffNo(resp.getStaffNo());

						display.setStaffName(resp.getStaffName());

						if(resp.getDepartmentName().indexOf("ROOT")==-1)
						display.setDepartmentName(resp.getDepartmentName());
			
						display.setJobPosition(resp.getJobPosition());

						display.setLeadership(resp.getLeadership());

						display.setPhone(resp.getPhone());

						display.setEmail(resp.getEmail());

						display.setDob(DateTool.dateToString(resp.getDob()));

						display.setStaffImage(resp.getPhoto());
						display.setStaffStatus(resp.getStatus().toString());
						
					}
				});
		
	}

	
}
