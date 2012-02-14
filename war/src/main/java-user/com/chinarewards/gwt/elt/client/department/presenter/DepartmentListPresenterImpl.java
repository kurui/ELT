package com.chinarewards.gwt.elt.client.department.presenter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.DepartmentListViewAdapter;
import com.chinarewards.gwt.elt.client.department.model.DepartmentCriteria;
import com.chinarewards.gwt.elt.client.department.model.DepartmentCriteria.DepartmentStatus;
import com.chinarewards.gwt.elt.client.department.plugin.DepartmentConstants;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentListPresenter.DepartmentListDisplay;
import com.chinarewards.gwt.elt.client.department.request.DeleteDepartmentRequest;
import com.chinarewards.gwt.elt.client.department.request.DeleteDepartmentResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.model.DepartmentClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
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
		init();
		registerHandler(display.getSearchBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						doSearch();
					}
				}));
		registerHandler(display.getAddBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {

						DepartmentClient client = new DepartmentClient();
						// client.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_ADD);

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										DepartmentConstants.EDITOR_DEPARTMENT_EDIT,
										DepartmentConstants.ACTION_DEPARTMENT_ADD,
										client);
					}
				}));
		registerHandler(display.getimportingBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("导入礼品...待实现~");
					}
				}));
	}

	private void init() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("SHELF", "未上架");
		map.put("SHELVES", "上架");
		display.initDepartmentStatus(map);
		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<DepartmentClient>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);

		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
	}

	private void doSearch() {
		DepartmentCriteria criteria = new DepartmentCriteria();
		if (!StringUtil.isEmpty(display.getKeyName().getValue()))
			criteria.setName(display.getKeyName().getValue());
		if (!StringUtil.isEmpty(display.getStatus()))
			criteria.setStatus(DepartmentStatus.valueOf(display.getStatus()));

		listViewAdapter = new DepartmentListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display);
		listViewAdapter.addDataDisplay(cellTable);
	}

	private void initTableColumns() {
		Sorting<DepartmentClient> ref = new Sorting<DepartmentClient>() {
			@Override
			public void sortingCurrentPage(
					Comparator<DepartmentClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};

		cellTable.addColumn("名称", new TextCell(),
				new GetValue<DepartmentClient, String>() {
					@Override
					public String getValue(DepartmentClient department) {
						return department.getName();
					}
				}, ref, "name");

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<DepartmentClient, String>() {
					@Override
					public String getValue(DepartmentClient arg0) {
						return "查看详细";
					}
				}, new FieldUpdater<DepartmentClient, String>() {
					@Override
					public void update(int index,
							DepartmentClient departmentClient, String value) {
						// departmentClient.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_VIEW);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										DepartmentConstants.EDITOR_DEPARTMENT_VIEW,
										DepartmentConstants.EDITOR_DEPARTMENT_VIEW
												+ departmentClient.getId(),
										departmentClient);
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<DepartmentClient, String>() {
					@Override
					public String getValue(DepartmentClient arg0) {
						return "修改";
					}
				}, new FieldUpdater<DepartmentClient, String>() {
					@Override
					public void update(int index,
							final DepartmentClient departmentClient,
							String value) {
						// departmentClient
						// .setThisAction(DepartmentConstants.ACTION_DEPARTMENT_EDIT);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										DepartmentConstants.EDITOR_DEPARTMENT_EDIT,
										DepartmentConstants.EDITOR_DEPARTMENT_EDIT
												+ departmentClient.getId(),
										departmentClient);
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<DepartmentClient, String>() {
					@Override
					public String getValue(DepartmentClient department) {
						return "删除";
					}
				}, new FieldUpdater<DepartmentClient, String>() {

					@Override
					public void update(int index, DepartmentClient o,
							String value) {
						if (Window.confirm("确定删除?")) {
							delteDepartment(o.getId());
						}
					}

				});

	}

	public void delteDepartment(String gifId) {

		dispatch.execute(new DeleteDepartmentRequest(gifId, sessionManager
				.getSession().getToken()),
				new AsyncCallback<DeleteDepartmentResponse>() {

					@Override
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(DeleteDepartmentResponse resp) {
						win.alert("删除成功");
						doSearch();
					}
				});
	}

}
