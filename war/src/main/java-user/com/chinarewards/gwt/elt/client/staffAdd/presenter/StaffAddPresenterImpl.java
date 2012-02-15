package com.chinarewards.gwt.elt.client.staffAdd.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffAdd.request.StaffAddRequest;
import com.chinarewards.gwt.elt.client.staffAdd.request.StaffAddResponse;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria.StaffStatus;
import com.chinarewards.gwt.elt.client.staffList.plugin.StaffListConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.XmlUtil_GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.inject.Inject;

public class StaffAddPresenterImpl extends
		BasePresenter<StaffAddPresenter.StaffAddDisplay> implements
		StaffAddPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public StaffAddPresenterImpl(EventBus eventBus, StaffAddDisplay display,
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
		breadCrumbs.loadChildPage("添加员工");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		init();
		registerHandler(display.getAddBtnClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						StaffAddRequest request = new StaffAddRequest();
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
								new AsyncCallback<StaffAddResponse>() {

									@Override
									public void onFailure(Throwable t) {
										win.alert(t.getMessage());
									}

									@Override
									public void onSuccess(StaffAddResponse resp) {
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

	private void init() {
		// 浏览即上传事件
		registerHandler(display.getPhotoUpload().addChangeHandler(
				new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent arg0) {
						System.out.println("==========="
								+ display.getPhotoUpload());

						display.getStaffImage().setVisible(true);
						display.getPhotoForm().setAction("fileupload");
						display.getPhotoForm().submit();
					}
				}));

		// 上传图片按钮事件
		registerHandler(display.getUploadClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						display.getPhotoForm().setAction("fileupload");
						display.getPhotoForm().submit();
					}
				}));

		// 文件上传后回调
		display.getPhotoForm().addSubmitCompleteHandler(
				new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						String eventResults = event.getResults();
						System.out.println("submitComplete event.getResults:"
								+ eventResults);
						// win.alert(eventResults);

						if (eventResults != null) {
							eventResults = XmlUtil_GWT
									.replaceSpecialStr(eventResults);

							try {
								// Document doc =
								// XmlUtil_GWT.parseXml(eventResults);
								// String result =
								// XmlUtil_GWT.getSingleNodeText(doc, "result");
								// String info =
								// XmlUtil_GWT.getSingleNodeText(doc, "info");
								String result = XmlUtil_GWT.getNormalNodeText(
										eventResults, "<result>", "</result>");
								String info = XmlUtil_GWT.getNormalNodeText(
										eventResults, "<info>", "</info>");

								if ("SUCCESS".equals(result)) {
									display.getPhoto().setValue(info);
									String giftImageUrl = "imageshow?imageName="
											+ info;
									display.getStaffImage()
											.setUrl(giftImageUrl);
								} else {
									win.alert("上传图片异常<br>" + info);
								}
							} catch (Exception e) {
								e.printStackTrace();
								win.alert("上传图片异常，请重试" + e.getMessage());
								return;
							}
						}
					}
				});
	}

}
