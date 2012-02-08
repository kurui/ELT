package com.chinarewards.gwt.elt.client.budget.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.plugin.CorpBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.request.EditCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.EditCorpBudgetResponse;
import com.chinarewards.gwt.elt.client.budget.util.CorpBudgetAdapterClient;
import com.chinarewards.gwt.elt.client.core.Platform;
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
	private String giftId;
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
		// 绑定事件
		init();

		if (CorpBudgetConstants.ACTION_GIFT_ADD.equals(thisAction)) {
			breadCrumbs.loadChildPage("新建礼品");
//			initSave();
		} else if (CorpBudgetConstants.ACTION_GIFT_EDIT.equals(thisAction)) {
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

						CorpBudgetVo giftVo = CorpBudgetAdapterClient
								.adapterDisplay(display);

						if (CorpBudgetConstants.ACTION_GIFT_ADD
								.equals(thisAction)) {
							giftVo.setId(null);
							doSave(giftVo);
						} else if (CorpBudgetConstants.ACTION_GIFT_EDIT
								.equals(thisAction)) {
							giftVo.setId(giftId);
							doEdit(giftVo);
						} else {
							win.alert("未定义的方法");
						}
					}

					private void doSave(CorpBudgetVo gift) {
						dispatcher.execute(new EditCorpBudgetRequest(gift,
								sessionManager.getSession()),
								new AsyncCallback<EditCorpBudgetResponse>() {
									@Override
									public void onFailure(Throwable t) {
										errorHandler.alert(t.toString());
									}

									@Override
									public void onSuccess(
											EditCorpBudgetResponse response) {
										Window.alert("添加成功");
										// if(instanceId!=null||!instanceId.equals(""))
										Platform.getInstance()
												.getEditorRegistry()
												.openEditor(
														CorpBudgetConstants.EDITOR_GIFTLIST_SEARCH,
														CorpBudgetConstants.ACTION_GIFT_LIST,
														instanceId);
									}
								});
					}

					private void doEdit(CorpBudgetVo gift) {
						if (Window.confirm("确定修改?")) {
							dispatcher
									.execute(
											new EditCorpBudgetRequest(gift,
													sessionManager.getSession()),
											new AsyncCallback<EditCorpBudgetResponse>() {
												@Override
												public void onFailure(
														Throwable t) {
													Window.alert("修改失败");
													Platform.getInstance()
															.getEditorRegistry()
															.closeEditor(
																	CorpBudgetConstants.EDITOR_GIFT_EDIT,
																	instanceId);
												}

												@Override
												public void onSuccess(
														EditCorpBudgetResponse arg0) {
													Window.alert("修改成功");
													Platform.getInstance()
															.getEditorRegistry()
															.openEditor(
																	CorpBudgetConstants.EDITOR_GIFTLIST_SEARCH,
																	CorpBudgetConstants.ACTION_GIFT_LIST,
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
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										CorpBudgetConstants.EDITOR_GIFTLIST_SEARCH,
										CorpBudgetConstants.ACTION_GIFT_LIST,
										instanceId);
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

		if (!flag) {
			win.alert(errorMsg.toString());
		}

		return flag;
	}

	private void initEdit() {
		// dispatcher.execute(new SearchCorpBudgetByIdRequest(giftId),
		// new AsyncCallback<SearchCorpBudgetByIdResponse>() {
		// @Override
		// public void onFailure(Throwable arg0) {
		// errorHandler.alert("查询出错!");
		// Platform.getInstance()
		// .getEditorRegistry()
		// .closeEditor(CorpBudgetConstants.EDITOR_GIFT_EDIT,
		// instanceId);
		// }
		//
		// @Override
		// public void onSuccess(SearchCorpBudgetByIdResponse response) {
		// CorpBudgetVo giftVo = response.getCorpBudget();
		// clear();
		// display.initEditCorpBudget(giftVo);
		// }
		// });
	}

	private void clear() {
		display.clear();
	}

	public void setId(String id) {
		this.giftId = id;
	}

	@Override
	public void initEditor(String giftId, String thisAction) {
		this.giftId = giftId;
		this.thisAction = thisAction;
	}

}
