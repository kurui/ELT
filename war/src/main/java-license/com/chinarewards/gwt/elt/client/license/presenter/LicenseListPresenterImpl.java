package com.chinarewards.gwt.elt.client.license.presenter;

import java.util.Comparator;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.adapter.license.LicenseListViewAdapter;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.license.model.LicenseClient;
import com.chinarewards.gwt.elt.client.license.model.LicenseCriteria;
import com.chinarewards.gwt.elt.client.license.model.LicenseCriteria.LicenseStatus;
import com.chinarewards.gwt.elt.client.license.plugin.LicenseConstants;
import com.chinarewards.gwt.elt.client.license.presenter.LicenseListPresenter.LicenseListDisplay;
import com.chinarewards.gwt.elt.client.license.request.DeleteLicenseRequest;
import com.chinarewards.gwt.elt.client.license.request.DeleteLicenseResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class LicenseListPresenterImpl extends BasePresenter<LicenseListDisplay>
		implements LicenseListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	EltNewPager pager;
	ListCellTable<LicenseClient> cellTable;
	LicenseListViewAdapter listViewAdapter;

	private final BreadCrumbsPresenter breadCrumbs;

	int pageSize = ViewConstants.per_page_number_in_dialog;

	@Inject
	public LicenseListPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			LicenseListDisplay display, Win win,
			BreadCrumbsPresenter breadCrumbs) {
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

						LicenseClient client = new LicenseClient();
						client.setThisAction(LicenseConstants.ACTION_LICENSE_ADD);

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(LicenseConstants.EDITOR_LICENSE_EDIT,
										LicenseConstants.ACTION_LICENSE_ADD,
										client);
					}
				}));
		registerHandler(display.getimportingBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("导入礼品...待实现~");
					}
				}));

		registerHandler(display.getPageNumber().addChangeHandler(
				new ChangeHandler() {

					@Override
					public void onChange(ChangeEvent event) {
						pageSize = Integer.parseInt(display.getPageNumber()
								.getValue(
										display.getPageNumber()
												.getSelectedIndex()));
						buildTable();
						doSearch();
					}
				}));
	}

	private void init() {
		display.initWidget();

		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<LicenseClient>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(pageSize);

		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
	}

	private void doSearch() {
		LicenseCriteria criteria = new LicenseCriteria();
		if (!StringUtil.isEmpty(display.getKeyName().getValue()))
			criteria.setName(display.getKeyName().getValue());
		if (!StringUtil.isEmpty(display.getStatus()))
			criteria.setStatus(LicenseStatus.valueOf(display.getStatus()));

		listViewAdapter = new LicenseListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display);
		listViewAdapter.addDataDisplay(cellTable);
	}

	private void initTableColumns() {
		Sorting<LicenseClient> ref = new Sorting<LicenseClient>() {
			@Override
			public void sortingCurrentPage(Comparator<LicenseClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};

		cellTable.addColumn("名称", new TextCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient license) {
						return license.getName();
					}
				}, ref, "name");

		cellTable.addColumn("来源", new TextCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient license) {
						return license.getSourceText() + "";
					}
				}, ref, "source");
		cellTable.addColumn("库存", new TextCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient license) {
						return license.getInventory();
					}
				}, ref, "stock");

		cellTable.addColumn("状态", new TextCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient license) {
						return license.getStatus().getDisplayName();
					}
				}, ref, "status");

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient license) {
						if (license.getStatus() != null
								&& license.getStatus() == LicenseStatus.SHELF)
							return "上架";
						else if (license.getStatus() != null
								&& license.getStatus() == LicenseStatus.SHELVES)
							return "下架";
						else
							return "未知状态";
					}
				}, new FieldUpdater<LicenseClient, String>() {
					@Override
					public void update(int index, final LicenseClient o,
							String value) {
						String msgStr = "";
						if (o.getStatus() != null
								&& o.getStatus() == LicenseStatus.SHELF)
							msgStr = "确定上架?";
						else if (o.getStatus() != null
								&& o.getStatus() == LicenseStatus.SHELVES)
							msgStr = "确定下架?";
						else
							msgStr = "未知状态";
						win.confirm("提示", msgStr, new ConfirmHandler() {
							@Override
							public void confirm() {
//								updateLicenseStatus(o.getId(), o.getStatus());
							}
						});
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient arg0) {
						return "查看详细";
					}
				}, new FieldUpdater<LicenseClient, String>() {
					@Override
					public void update(int index, LicenseClient licenseClient,
							String value) {
						licenseClient
								.setThisAction(LicenseConstants.ACTION_LICENSE_VIEW);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										LicenseConstants.EDITOR_LICENSE_VIEW,
										LicenseConstants.EDITOR_LICENSE_VIEW
												+ licenseClient.getId(),
										licenseClient);
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient arg0) {
						return "修改";
					}
				}, new FieldUpdater<LicenseClient, String>() {
					@Override
					public void update(int index,
							final LicenseClient licenseClient, String value) {
						licenseClient
								.setThisAction(LicenseConstants.ACTION_LICENSE_EDIT);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										LicenseConstants.EDITOR_LICENSE_EDIT,
										LicenseConstants.EDITOR_LICENSE_EDIT
												+ licenseClient.getId(),
										licenseClient);
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<LicenseClient, String>() {
					@Override
					public String getValue(LicenseClient license) {
						return "删除";
					}
				}, new FieldUpdater<LicenseClient, String>() {

					@Override
					public void update(int index, LicenseClient o, String value) {
						if (Window.confirm("确定删除?")) {
							delteLicense(o.getId());
						}
					}

				});

	}

	public void delteLicense(String gifId) {

		dispatch.execute(new DeleteLicenseRequest(gifId, sessionManager
				.getSession().getToken()),
				new AsyncCallback<DeleteLicenseResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(DeleteLicenseResponse resp) {
						win.alert("删除成功");
						doSearch();
					}
				});
	}

}
