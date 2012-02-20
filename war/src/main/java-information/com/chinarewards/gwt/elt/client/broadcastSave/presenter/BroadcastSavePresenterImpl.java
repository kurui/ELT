package com.chinarewards.gwt.elt.client.broadcastSave.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.broadcastSave.dialog.ChooseOrganizationListDialog;
import com.chinarewards.gwt.elt.client.broadcastSave.request.BroadcastSaveRequest;
import com.chinarewards.gwt.elt.client.chooseOrganization.event.ChooseOrganizationEvent;
import com.chinarewards.gwt.elt.client.chooseOrganization.handler.ChooseOrganizationHandler;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.ui.DialogCloseListener;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class BroadcastSavePresenterImpl extends
		BasePresenter<BroadcastSavePresenter.BroadcastSaveDisplay> implements
		BroadcastSavePresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;
	String broadcastId = null;
	private final BreadCrumbsPresenter breadCrumbs;
	private final Provider<ChooseOrganizationListDialog> chooseOrganizationDialogProvider;
	@Inject
	public BroadcastSavePresenterImpl(EventBus eventBus, BroadcastSaveDisplay display,
			DispatchAsync dispatch, SessionManager sessionManager, Win win,
			BreadCrumbsPresenter breadCrumbs, ErrorHandler errorHandler,Provider<ChooseOrganizationListDialog> chooseOrganizationDialogProvider) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
		this.chooseOrganizationDialogProvider=chooseOrganizationDialogProvider;
	}

	@Override
	public void bind() {
		if(broadcastId!=null)
		{
			breadCrumbs.loadChildPage("修改广播");
			display.setTitleText("修改广播");
		}
		else
		{
			breadCrumbs.loadChildPage("发送新广播");
			display.setTitleText("发送新广播");
		}
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		init();
		registerHandler(display.getSaveBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if(StringUtil.isEmpty(display.getContent()))
						{
							win.alert("请填写广播内容!");
							return;
						}
						else if(display.getBroadcastingTimeStart()==null)
						{
							win.alert("请填写广播开始时间!");
							return;
						}
						else if(display.getBroadcastingTimeEnd()==null)
						{
							win.alert("请填写广播结束时间!");
							return;
						}
						
						BroadcastSaveRequest request = new BroadcastSaveRequest();
						if (broadcastId != null)
							request.setBroadcastId(broadcastId);
						request.setSession(sessionManager.getSession());
						request.setBroadcastingTimeStart(display.getBroadcastingTimeStart().getValue());
						request.setBroadcastingTimeEnd(display.getBroadcastingTimeEnd().getValue());
						
						if (display.isAllowreplies_true().getValue()) {
							request.setAllowreplies(true);
						}  else {
							request.setAllowreplies(false);
						}
						List<String[]> orginList=display.getRealOrginzationIds();
						if(orginList.size()>0)
						{
							for (int i = 0; i < orginList.size(); i++) {
								String [] orgin=orginList.get(i);
								win.alert(orgin[0]+"--"+orgin[1]+"--"+orgin[2]);
							}
						}

//						dispatch.execute(request,
//								new AsyncCallback<BroadcastSaveResponse>() {
//
//									@Override
//									public void onFailure(Throwable t) {
//										win.alert(t.getMessage());
//									}
//
//									@Override
//									public void onSuccess(BroadcastSaveResponse resp) {
//										win.alert("保存成功");
//										Platform.getInstance()
//										.getEditorRegistry()
//										.openEditor(
//												BroadcastingListConstants.EDITOR_BROADCASTINGLIST_SEARCH,
//												"EDITOR_BROADCASTINGLIST_SEARCH_DO_ID", null);
//									}
//								});
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
													System.out.println("ds=="+ r);
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

	}

	private void init() {
		if (broadcastId != null) {
//			// 修改加载数据
//			dispatch.execute(new BroadcastViewRequest(broadcastId),
//					new AsyncCallback<BroadcastViewResponse>() {
//
//						@Override
//						public void onFailure(Throwable t) {
//							win.alert(t.getMessage());
//						}
//
//						@Override
//						public void onSuccess(BroadcastViewResponse resp) {
//
//							display.setBroadcastNo(resp.getBroadcastNo());
//							display.setBroadcastName(resp.getBroadcastName());
//							display.setDepartmentId(resp.getDepartmentId());
//							display.setDepartmentName(resp.getDepartmentName());
//							display.setJobPosition(resp.getJobPosition());
//							display.setLeadership(resp.getLeadership());
//							display.setPhone(resp.getPhone());
//							display.setEmail(resp.getEmail());
//							display.setDob(resp.getDob());
//							display.setBroadcastImage(resp.getPhoto());
//							display.setPhoto(resp.getPhoto());
//							display.setStatus(resp.getStatus().toString());
//
//						}
//					});

		}

	}

	@Override
	public void initBroadcastUpdate(String broadcastId) {
		this.broadcastId = broadcastId;
	}

}
