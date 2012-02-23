package com.chinarewards.gwt.elt.client.registerHr.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.EltGinjector;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.registerHr.model.HrVo;
import com.chinarewards.gwt.elt.client.registerHr.request.RegisterHrRequest;
import com.chinarewards.gwt.elt.client.registerHr.request.RegisterHrResponse;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;

public class RegisterHrPresenterImpl extends
		BasePresenter<RegisterHrPresenter.RegisterHrDisplay> implements
		RegisterHrPresenter {

	private final DispatchAsync dispatcher;
	private final EltGinjector injector = GWT.create(EltGinjector.class);

	@Inject
	public RegisterHrPresenterImpl(EventBus eventBus,
			RegisterHrDisplay display, DispatchAsync dispatcher	) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		
	}

	@Override
	public void bind() {
		registerHandler(display.getRegisterHrClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						
						if (StringUtil.isEmpty(display.getName().getValue())) {
							Window.alert("名字不能为空!<br>");
							return;
						}
						if (StringUtil.isEmpty(display.getPassword().getValue())) {
							Window.alert("密码不能为空!<br>");
							return;
						}
						if (StringUtil.isEmpty(display.getUsername().getValue())) {
							Window.alert("用户名不能为空!<br>");
							return;
						}
						if (!display.getPassword().getValue().equals(display.getValidatePassword().getValue())) {
							Window.alert("密码和确认密码不一致!<br>");
							return;
						}
						if (StringUtil.isEmpty(display.getEmail().getValue())) {
							Window.alert("电子邮件不能为空!<br>");
							return;
						}
//						else if(!StringUtil.isValidEmail(display.getEmail()))
//						{
//                           Window.alert("Email格式不正确,请重新填写Email!");
//							return;
//						}
						doRegisterHr();
					}
				}));
	}

	protected void doRegisterHr() {

		HrVo vo = new HrVo();
		vo.setName(display.getName().getValue());
		vo.setEmail(display.getEmail().getValue());
		vo.setTell(display.getTell().getValue());
		vo.setPassword(display.getPassword().getValue());
		vo.setUsername(display.getUsername().getValue());
		
		List<UserRoleVo> userRoleVos=new ArrayList<UserRoleVo>();
		
		userRoleVos.add(UserRoleVo.CORP_ADMIN);
		userRoleVos.add(UserRoleVo.GIFT);
		userRoleVos.add(UserRoleVo.STAFF);
		//userRoleVos.add(UserRoleVo.DEPT_MGR);
		
		vo.setUserRoleVos(userRoleVos);
		dispatcher.execute(new RegisterHrRequest(vo),
				new AsyncCallback<RegisterHrResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(RegisterHrResponse response) {

						Window.alert("添加成功!");
						injector.getMain().init(RootLayoutPanel.get());
					}
				});
	}

	@Override
	public void initRegister(String instanceId) {
	//	this.instanceId=instanceId;
		
	}

}
