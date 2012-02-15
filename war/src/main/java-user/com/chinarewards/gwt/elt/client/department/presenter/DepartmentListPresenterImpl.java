package com.chinarewards.gwt.elt.client.department.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.dataprovider.DepartmentListViewAdapter;
import com.chinarewards.gwt.elt.client.department.model.DepartmentClient;
import com.chinarewards.gwt.elt.client.department.model.DepartmentNode;
import com.chinarewards.gwt.elt.client.department.plugin.DepartmentConstants;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentListPresenter.DepartmentListDisplay;
import com.chinarewards.gwt.elt.client.department.request.DeleteDepartmentRequest;
import com.chinarewards.gwt.elt.client.department.request.DeleteDepartmentResponse;
import com.chinarewards.gwt.elt.client.department.request.DepartmentManageRequest;
import com.chinarewards.gwt.elt.client.department.request.DepartmentManageResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class DepartmentListPresenterImpl extends
		BasePresenter<DepartmentListDisplay> implements DepartmentListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	EltNewPager pager;
	ListCellTable<DepartmentClient> cellTable;
	DepartmentListViewAdapter listViewAdapter;

	private String departmentIds;

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public DepartmentListPresenterImpl(EventBus eventBus,
			DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager, DepartmentListDisplay display,
			Win win, BreadCrumbsPresenter breadCrumbs) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadListPage();
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

		registerEvent();

		initTreeTable();

	}

	private void initTreeTable() {
		final String corporationId = sessionManager.getSession()
				.getCorporationId();
		dispatch.execute(new DepartmentManageRequest(corporationId,
				departmentIds), new AsyncCallback<DepartmentManageResponse>() {
			@Override
			public void onFailure(Throwable e) {
				win.alert(e.getMessage());
			}

			@Override
			public void onSuccess(DepartmentManageResponse response) {
				// display.setBudgetIntegral((int)response.getBudgetIntegral()+"");
				// display.setUseIntegeral((int)response.getBudgetIntegral()+"");
				List<DepartmentNode> nodeList = response.getResult();
				display.loadTreeData(nodeList, corporationId,departmentIds);
			}
		});
	}

	private void registerEvent() {
		// 增加同级部门
		registerHandler(display.getAddSameLevelBtnClickHandlers()
				.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("currentDepartmentId()----" + departmentIds);

					}
				}));

		// 增加子部门
		registerHandler(display.getAddChildBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("功能建设中");
					}
				}));
		// 删除部门
		registerHandler(display.getDeleteBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("功能建设中");
					}
				}));
		// 编辑部门
		registerHandler(display.getEditBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						// win.alert("功能建设中");
						DepartmentClient client = new DepartmentClient();
						client.setId(departmentIds);
						client.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_EDIT);

//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor(
//										DepartmentConstants.EDITOR_DEPARTMENT_EDIT,
//										DepartmentConstants.ACTION_DEPARTMENT_EDIT,
//										client);
					}
				}));
		// 合并部门
		registerHandler(display.getMergeBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("功能建设中");
						// 跳转页面
					}
				}));
		// 同步公司组织机构
		registerHandler(display.getSynchBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("功能建设中");
					}
				}));

	}

	public void delteDepartment(String departmentId) {
		dispatch.execute(new DeleteDepartmentRequest(departmentId,
				sessionManager.getSession().getToken()),
				new AsyncCallback<DeleteDepartmentResponse>() {

					@Override
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(DeleteDepartmentResponse resp) {
						win.alert("删除成功");
					}
				});
	}

	@Override
	public void initEditor(String departmentIds) {
		System.out.println("---initEditor:" + departmentIds);
		this.departmentIds = departmentIds;
		display.getCurrentDepartmentId().setValue(departmentIds);
		// win.alert(departmentId);
	}
}
