package com.chinarewards.gwt.elt.client.mail.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.broadcastSave.dialog.ChooseOrganizationListDialog;
import com.chinarewards.gwt.elt.client.chooseOrganization.event.ChooseOrganizationEvent;
import com.chinarewards.gwt.elt.client.chooseOrganization.handler.ChooseOrganizationHandler;
import com.chinarewards.gwt.elt.client.chooseOrganization.model.OrganSearchCriteria.OrganType;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.ui.DialogCloseListener;
import com.chinarewards.gwt.elt.client.mail.model.MailVo;
import com.chinarewards.gwt.elt.client.mail.request.MailSendAllRequest;
import com.chinarewards.gwt.elt.client.mail.request.MailSendAllResponse;
import com.chinarewards.gwt.elt.client.mvp.BaseDialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MailSendAllPresenterImpl extends	BaseDialogPresenter<MailSendAllPresenter.MailSendAllDisplay> implements
		MailSendAllPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;
	String broadcastId = null;
	String staffId = "";
	private final Provider<ChooseOrganizationListDialog> chooseOrganizationDialogProvider;
	
	@Inject
	public MailSendAllPresenterImpl(
			EventBus eventBus,
			MailSendAllDisplay display,
			DispatchAsync dispatch,
			SessionManager sessionManager,
			Win win,
			Provider<ChooseOrganizationListDialog> chooseOrganizationDialogProvider,
			ErrorHandler errorHandler		) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;
		this.chooseOrganizationDialogProvider = chooseOrganizationDialogProvider;
		
	}

	@Override
	public void bind() {

	
		registerHandler(display.getSaveBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (display.getRealOrginzationIds().size()==0) {
							win.alert("请选择收件人!");
							return;
						}
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
						mailvo.setTitle(display.getTitle());
						MailSendAllRequest request = new MailSendAllRequest(mailvo,sessionManager.getSession(),display.getRealOrginzationIds());
						dispatch.execute(request,new AsyncCallback<MailSendAllResponse>() {

									@Override
									public void onFailure(Throwable t) {
										win.alert(t.getMessage());
									}

									@Override
									public void onSuccess(MailSendAllResponse resp) {
										win.alert(resp.getToken());
										display.setContent("");
										closeDialog();
									}
								});
					}
				}));

		registerHandler(display.getChooseBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						final ChooseOrganizationListDialog dialog = chooseOrganizationDialogProvider.get();
						final HandlerRegistration registration = eventBus
								.addHandler(ChooseOrganizationEvent.getType(),
										new ChooseOrganizationHandler() {
											@Override
											public void chosenOrganization(
													List<OrganicationClient> list) {
												for (OrganicationClient r : list) {
													 if (!display.getSpecialTextArea().containsItem(r)) {
														display.getSpecialTextArea().addItem(r);
													}
												}
											}
										});

						Platform.getInstance().getSiteManager()
								.openDialog(dialog, new DialogCloseListener() {
									public void onClose(String dialogId,
											String instanceId) {
										registration.removeHandler();
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
						if(sum>250)
							display.setContent(display.getContent().substring(0,250));
						display.setMessage((250-display.getContent().length())+"");
					    }
									
		  }));

	}


	@Override
	public void initBroadcastStaff(String staffId,String staffName) {
		
		this.staffId=staffId;
		//this.quietlyOrDalliance=quietlyOrDalliance;
		OrganicationClient client=new OrganicationClient();
		client.setId(staffId);
		client.setName(staffName);
		client.setType(OrganType.STAFF);
		display.getSpecialTextArea().addItem(client);
	}

	
	public void displaySelectStaff() {
		display.displaySelectStaff();
		
	}



}
