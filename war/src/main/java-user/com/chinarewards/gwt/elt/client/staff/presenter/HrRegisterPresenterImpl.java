package com.chinarewards.gwt.elt.client.staff.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staff.HrRegisterRequest;
import com.chinarewards.gwt.elt.client.staff.HrRegisterResponse;
import com.chinarewards.gwt.elt.client.staff.model.StaffVo;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.user.plugin.UserConstants;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class HrRegisterPresenterImpl extends
		BasePresenter<HrRegisterPresenter.HrRegisterDisplay> implements
		HrRegisterPresenter {

	private final DispatchAsync dispatcher;
	private final SessionManager sessionManager;
	//private  String instanceId;

	@Inject
	public HrRegisterPresenterImpl(EventBus eventBus,
			HrRegisterDisplay display, DispatchAsync dispatcher,
			SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.sessionManager = sessionManager;
	}

	@Override
	public void bind() {
		registerHandler(display.getHrRegisterClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						if (StringUtil.isEmpty(display.getEmail().getValue())) {
							Window.alert("电子邮件不能为空!<br>");
							return;
						}
						doHrRegister();
					}
				}));
	}

	protected void doHrRegister() {

		StaffVo vo = new StaffVo();
		vo.setName(display.getName().getValue());
		vo.setEmail(display.getEmail().getValue());
		vo.setTell(display.getTell().getValue());
		vo.setPassword(display.getPassword().getValue());
		vo.setUsername(display.getUsername().getValue());
		vo.setCreateUserId(sessionManager.getSession().getToken());
		vo.setDeptId(sessionManager.getSession().getDepartmentId());

		dispatcher.execute(new HrRegisterRequest(vo),
				new AsyncCallback<HrRegisterResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(HrRegisterResponse response) {

						Window.alert("添加成功!");
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(UserConstants.EDITOR_USER_SEARCH,
								"EDITOR_USER_SEARCH_DO_ID", null);}
				});
	}

	@Override
	public void initRegister(String instanceId) {
	//	this.instanceId=instanceId;
		
	}

}
