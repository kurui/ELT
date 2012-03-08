package com.chinarewards.gwt.elt.client.mail.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.broadcastSave.dialog.StaffChooseOrganizationListDialog;
import com.chinarewards.gwt.elt.client.mail.model.MailVo;
import com.chinarewards.gwt.elt.client.mail.request.MailRequest;
import com.chinarewards.gwt.elt.client.mail.request.MailResponse;
import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MailSendPresenterImpl extends	BaseDialogPresenter<MailSendPresenter.MailSendDisplay> implements
		MailSendPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;
	String broadcastId = null;
	
	
	@Inject
	public MailSendPresenterImpl(
			EventBus eventBus,
			MailSendDisplay display,
			DispatchAsync dispatch,
			SessionManager sessionManager,
			Win win,
			ErrorHandler errorHandler		) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;

		
	}

	@Override
	public void bind() {

	
		registerHandler(display.getSaveBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (StringUtil.isEmpty(display.getTitle())) {
							win.alert("请填写邮件标题!");
							return;
						}
						if (StringUtil.isEmpty(display.getContent())) {
							win.alert("请填写邮件内容!");
							return;
						}

						MailVo mailvo = new MailVo();
						mailvo.setContent(display.getContent());
						mailvo.setStaffId(display.getStaffId());
						mailvo.setTitle(display.getTitle());
						MailRequest request = new MailRequest(mailvo,sessionManager.getSession());
						dispatch.execute(request,new AsyncCallback<MailResponse>() {

									@Override
									public void onFailure(Throwable t) {
										win.alert(t.getMessage());
									}

									@Override
									public void onSuccess(MailResponse resp) {
										win.alert(resp.getToken());
										display.setContent("");
										closeDialog();
									}
								});
					}
				}));

		
		registerHandler(display.getReturnBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						closeDialog();

					}
				}));
		registerHandler(display.getContentKeyUpHandlers().addKeyUpHandler(
				new KeyUpHandler() {

					@Override
					public void onKeyUp(KeyUpEvent event) {
						int sum = display.getContent().length();
						if(sum>150)
							display.setContent(display.getContent().substring(0,150));
						display.setMessage((150-display.getContent().length())+"");
					    }
									
		  }));

	}


	@Override
	public void initBroadcastStaff(String staffId,String staffName) {
		display.setStaffId(staffId);
		display.setStaffName(staffName);
	}

	


}
