package com.chinarewards.gwt.elt.client.license.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.chinarewards.gwt.elt.client.license.plugin.LicenseConstants;
import com.chinarewards.gwt.elt.client.license.request.EditLicenseRequest;
import com.chinarewards.gwt.elt.client.license.request.EditLicenseResponse;
import com.chinarewards.gwt.elt.client.license.request.SearchLicenseByIdRequest;
import com.chinarewards.gwt.elt.client.license.request.SearchLicenseByIdResponse;
import com.chinarewards.gwt.elt.client.license.util.LicenseAdapterClient;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class LicensePresenterImpl extends BasePresenter<LicensePresenter.LicenseDisplay>
		implements LicensePresenter {
	String instanceId;// 修改时传过来的ID

	private String thisAction;
	private String licenseId;
	//
	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final SessionManager sessionManager;

	private final Win win;

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public LicensePresenterImpl(EventBus eventBus, LicenseDisplay display,
			DispatchAsync dispatcher, ErrorHandler errorHandler,
			SessionManager sessionManager, Win win,
			BreadCrumbsPresenter breadCrumbs) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		// 绑定事件
		init();

		if (LicenseConstants.ACTION_LICENSE_ADD.equals(thisAction)) {
			breadCrumbs.loadChildPage("新建礼品");
			initSave();
		} else if (LicenseConstants.ACTION_LICENSE_EDIT.equals(thisAction)) {
			initEdit();
			breadCrumbs.loadChildPage("编辑礼品");
		} else {
			win.alert("未定义的方法");
		}

		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
	}

	private void init() {
		// 保存事件
		registerHandler(display.getSaveClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						if (!validateSubmit()) {
							return;
						}

						LicenseVo licenseVo = LicenseAdapterClient
								.adapterDisplay(display);

						if (LicenseConstants.ACTION_LICENSE_ADD.equals(thisAction)) {
							licenseVo.setId(null);
							doSave(licenseVo);
						} else if (LicenseConstants.ACTION_LICENSE_EDIT
								.equals(thisAction)) {
							licenseVo.setId(licenseId);
							doEdit(licenseVo);
						} else {
							win.alert("未定义的方法");
						}
					}

					private void doSave(LicenseVo license) {
						dispatcher.execute(new EditLicenseRequest(license,
								sessionManager.getSession()),
								new AsyncCallback<EditLicenseResponse>() {
									@Override
									public void onFailure(Throwable t) {
										errorHandler.alert(t.toString());
									}

									@Override
									public void onSuccess(
											EditLicenseResponse response) {
										win.alert("添加成功");
										// if(instanceId!=null||!instanceId.equals(""))
										Platform.getInstance()
												.getEditorRegistry()
												.openEditor(
														LicenseConstants.EDITOR_LICENSELIST_SEARCH,
														LicenseConstants.ACTION_LICENSE_LIST,
														instanceId);
									}
								});
					}

					private void doEdit(LicenseVo license) {
						if (Window.confirm("确定修改?")) {
							dispatcher.execute(new EditLicenseRequest(license,
									sessionManager.getSession()),
									new AsyncCallback<EditLicenseResponse>() {
										@Override
										public void onFailure(Throwable t) {
											win.alert("修改失败");
											Platform.getInstance()
													.getEditorRegistry()
													.closeEditor(
															LicenseConstants.EDITOR_LICENSE_EDIT,
															instanceId);
										}

										@Override
										public void onSuccess(
												EditLicenseResponse arg0) {
											win.alert("修改成功");
											Platform.getInstance()
													.getEditorRegistry()
													.openEditor(
															LicenseConstants.EDITOR_LICENSELIST_SEARCH,
															LicenseConstants.ACTION_LICENSE_LIST,
															instanceId);
										}
									});
						}
					}

				}));


		registerHandler(display.getBackClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						breadCrumbs.getGoHistory();
					}
				}));		
	
	}

	// 验证方法
	private boolean validateSubmit() {
		boolean flag = true;
		StringBuilder errorMsg = new StringBuilder();
		if (display.getName().getValue() == null
				|| "".equals(display.getName().getValue().trim())) {
			errorMsg.append("请填写礼品名称!<br>");
			flag = false;
		}

		if (display.getStock() == null
				|| StringUtil.valueOf(display.getStock().getValue()) == null
				|| StringUtil.valueOf(display.getStock().getValue()) < 0) {
			errorMsg.append("请正确填写礼品库存!<br>");
			flag = false;
		}
		if (display.getIndate().getValue() == null|| "".equals(display.getIndate().getValue())) {
			errorMsg.append("有效期不能为空<br>");
			flag = false;
		}
	

		if (!flag) {
			win.alert(errorMsg.toString());
		}

		return flag;
	}

	private void initEdit() {
		dispatcher.execute(new SearchLicenseByIdRequest(licenseId),
				new AsyncCallback<SearchLicenseByIdResponse>() {
					@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询出错!");
						Platform.getInstance()
								.getEditorRegistry()
								.closeEditor(LicenseConstants.EDITOR_LICENSE_EDIT,
										instanceId);
					}

					@Override
					public void onSuccess(SearchLicenseByIdResponse response) {
						LicenseVo licenseVo = response.getLicense();
						clear();
						display.initEditLicense(licenseVo);
					}
				});
	}

	private void initSave() {
		display.initAddLicense(new LicenseVo());
	}

	private void clear() {
		display.clear();
	}

	public void setId(String id) {
		this.licenseId = id;
	}

	@Override
	public void initEditor(String licenseId, String thisAction) {
		this.licenseId = licenseId;
		this.thisAction = thisAction;
	}

}
