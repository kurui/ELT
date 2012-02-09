package com.chinarewards.gwt.elt.client.budget.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.plugin.CorpBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.request.EditCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.EditCorpBudgetResponse;
import com.chinarewards.gwt.elt.client.budget.request.SearchCorpBudgetByCorpIdRequest;
import com.chinarewards.gwt.elt.client.budget.request.SearchCorpBudgetByCorpIdResponse;
import com.chinarewards.gwt.elt.client.budget.util.CorpBudgetAdapterClient;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.enterprise.plugin.EnterpriseConstants;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class CorpBudgetPresenterImpl extends
		BasePresenter<CorpBudgetPresenter.CorpBudgetDisplay> implements
		CorpBudgetPresenter {
	String instanceId;// 修改时传过来的ID

	private String thisAction;
	private String corpId;
	//
	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final SessionManager sessionManager;

	private final Win win;

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public CorpBudgetPresenterImpl(EventBus eventBus,
			CorpBudgetDisplay display, DispatchAsync dispatcher,
			ErrorHandler errorHandler, SessionManager sessionManager, Win win,
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
		breadCrumbs.loadChildPage("今年财年预算");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

		registerEvent();

		initEdit();
	}

	private void registerEvent() {
		registerSaveEvent();

		registerHandler(display.getPeriodBtnClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										EnterpriseConstants.EDITOR_PERIOD_EDIT,
										"EnterpriseConstants.ACTION_PERIOD_EDIT",
										instanceId);
					}
				}));

		registerHandler(display.getBackClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										CorpBudgetConstants.EDITOR_CORPBUDGET_EDIT,
										CorpBudgetConstants.ACTION_CORPBUDGET_EDIT,
										instanceId);
					}
				}));

	}

	// 保存事件
	private void registerSaveEvent() {
		registerHandler(display.getSaveClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						if (!validateSubmit()) {
							return;
						}

						CorpBudgetVo corpBudgetVo = CorpBudgetAdapterClient
								.adapterDisplay(display);

						dispatcher.execute(new EditCorpBudgetRequest(
								corpBudgetVo, sessionManager.getSession()),
								new AsyncCallback<EditCorpBudgetResponse>() {
									@Override
									public void onFailure(Throwable t) {
										Window.alert("修改失败");
										Platform.getInstance()
												.getEditorRegistry()
												.closeEditor(
														CorpBudgetConstants.EDITOR_CORPBUDGET_EDIT,
														instanceId);
									}

									@Override
									public void onSuccess(
											EditCorpBudgetResponse arg0) {
										Window.alert("修改成功");
										Platform.getInstance()
												.getEditorRegistry()
												.openEditor(
														CorpBudgetConstants.EDITOR_CORPBUDGET_EDIT,
														CorpBudgetConstants.ACTION_CORPBUDGET_EDIT,
														instanceId);
									}
								});

					}
				}));
	}

	// 验证方法
	private boolean validateSubmit() {
		boolean flag = true;
		StringBuilder errorMsg = new StringBuilder();
		if (display.getBudgetAmount().getValue() == null
				|| "".equals(display.getBudgetAmount().getValue().trim())) {
			errorMsg.append("请填写预算金额!<br>");
			flag = false;
		}

		if (display.getBudgetIntegral().getValue() == null
				|| "".equals(display.getBudgetIntegral().getValue().trim())) {
			errorMsg.append("请填写预算积分!<br>");
			flag = false;
		}

		if (!flag) {
			win.alert(errorMsg.toString());
		}

		return flag;
	}

	private void initEdit() {
		dispatcher.execute(new SearchCorpBudgetByCorpIdRequest(corpId),
				new AsyncCallback<SearchCorpBudgetByCorpIdResponse>() {
					@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询出错!");
						Platform.getInstance()
								.getEditorRegistry()
								.closeEditor(
										CorpBudgetConstants.EDITOR_CORPBUDGET_EDIT,
										instanceId);
					}

					@Override
					public void onSuccess(
							SearchCorpBudgetByCorpIdResponse response) {
						CorpBudgetVo giftVo = response.getCorpBudget();

						display.initEditCorpBudget(giftVo);
					}
				});
	}

	@Override
	public void initEditor(String corpId, String thisAction) {
		this.corpId = corpId;
		this.thisAction = thisAction;
	}

}
