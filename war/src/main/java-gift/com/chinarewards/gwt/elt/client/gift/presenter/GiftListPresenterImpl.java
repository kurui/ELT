package com.chinarewards.gwt.elt.client.gift.presenter;

import java.util.Comparator;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.ui.DialogCloseListener;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.GiftListViewAdapter;
import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.client.gift.model.GiftCriteria;
import com.chinarewards.gwt.elt.client.gift.model.GiftCriteria.GiftStatus;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftConstants;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftListPresenter.GiftListDisplay;
import com.chinarewards.gwt.elt.client.gift.request.DeleteGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.DeleteGiftResponse;
import com.chinarewards.gwt.elt.client.gift.request.UpdateGiftStatusRequest;
import com.chinarewards.gwt.elt.client.gift.request.UpdateGiftStatusResponse;
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
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GiftListPresenterImpl extends BasePresenter<GiftListDisplay>
		implements GiftListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	EltNewPager pager;
	ListCellTable<GiftClient> cellTable;
	GiftListViewAdapter listViewAdapter;
	
	private final Provider<ChooseExportTypeDialog> chooseExportTypeDialogProvider;

	private final BreadCrumbsPresenter breadCrumbs;

	int pageSize = ViewConstants.per_page_number_in_dialog;

	@Inject
	public GiftListPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			GiftListDisplay display, Win win, BreadCrumbsPresenter breadCrumbs,Provider<ChooseExportTypeDialog> chooseExportTypeDialogProvider) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
		this.chooseExportTypeDialogProvider=chooseExportTypeDialogProvider;

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

						GiftClient client = new GiftClient();
						client.setThisAction(GiftConstants.ACTION_GIFT_ADD);
						client.setFromMenu(false);

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(GiftConstants.EDITOR_GIFT_EDIT,
										GiftConstants.ACTION_GIFT_ADD, client);
					}
				}));
		registerHandler(display.getImportBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("导入礼品...待实现~");
					}
				}));

		registerHandler(display.getExportBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
//						doExport();
						openChooseExportTypeWindow();
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
		cellTable = new ListCellTable<GiftClient>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(pageSize);
		cellTable.getColumn(0).setCellStyleNames("divTextLeft");// 第一列左对齐
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
	}

	private void doSearch() {
		GiftCriteria criteria = new GiftCriteria();
		if (!StringUtil.isEmpty(display.getKeyName().getValue()))
			criteria.setName(display.getKeyName().getValue());

		int selectedStatusIndex = display.getStatus().getSelectedIndex();
		String status = display.getStatus().getValue(selectedStatusIndex);
		if(!StringUtil.isEmpty(status)){
			criteria.setStatus(GiftStatus.valueOf(status));
		}
		

		listViewAdapter = new GiftListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display);
		listViewAdapter.addDataDisplay(cellTable);
	}

	private void openChooseExportTypeWindow() {
		final HandlerRegistration registration = eventBus.addHandler(
				ChooseExportTypeEvent.getType(), new ChooseExportTypeHandler() {				

					@Override
					public void chooseExportType(String exportFileType) {
						if (StringUtil.isEmpty(exportFileType)) {
							win.alert("请选择导出文件类型");
						}else{
							doExport(exportFileType);
						}
						
						
					}
				});
		//
		final ChooseExportTypeDialog dialog = chooseExportTypeDialogProvider.get();
		//
		Platform.getInstance().getSiteManager()
				.openDialog(dialog, new DialogCloseListener() {
					public void onClose(String dialogId, String instanceId) {
						registration.removeHandler();
					}
				});
	}

	private void doExport(String exportFileType) {
		String title = "导出礼品信息";

		String data = "name=" + display.getKeyName().getValue();

		int selectedStatusIndex = display.getStatus().getSelectedIndex();
		String status = display.getStatus().getValue(selectedStatusIndex);
		data = data + "&status=" + status;
		data=data+"&exportFileType="+exportFileType;

		String userdata = "&corporationId="
				+ sessionManager.getSession().getCorporationId();
		userdata = userdata + "&userId="
				+ sessionManager.getSession().getToken();

		// data = data+"&content=true";
		UserRoleVo[] userRoleVos = sessionManager.getSession().getUserRoles();
		for (UserRoleVo role : userRoleVos) {
			String s = role.toString();
			if (s.equals("CORP_ADMIN")) {
				userdata = userdata + "&userRole=CORP_ADMIN";
			} else if (s.equals("DEPT_MGR")) {
				userdata = userdata + "&userRole=DEPT_MGR";
			}
		}

		String url = GWT.getModuleBaseURL() + "servlet.exportGift";
		String wholeUrl = url + "?" + data + userdata + "&radom="
				+ Math.random();
		Window.open(wholeUrl, title,
				"menubar=yes,location=no,resizable=yes,scrollbars=yes,status=yes");
	}

	private void initTableColumns() {
		Sorting<GiftClient> ref = new Sorting<GiftClient>() {
			@Override
			public void sortingCurrentPage(Comparator<GiftClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};

		cellTable.addColumn("名称", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient gift) {
						return gift.getName();
					}
				}, ref, "name");

		cellTable.addColumn("来源", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient gift) {
						return gift.getSourceText() + "";
					}
				}, ref, "source");
		cellTable.addColumn("库存", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient gift) {
						return gift.getInventory();
					}
				}, ref, "stock");

		cellTable.addColumn("状态", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient gift) {
						return gift.getStatus().getDisplayName();
					}
				}, ref, "status");

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient gift) {
						if (gift.getStatus() != null
								&& gift.getStatus() == GiftStatus.SHELF)
							return "上架";
						else if (gift.getStatus() != null
								&& gift.getStatus() == GiftStatus.SHELVES)
							return "下架";
						else
							return "未知状态";
					}
				}, new FieldUpdater<GiftClient, String>() {
					@Override
					public void update(int index, final GiftClient o,
							String value) {
						String msgStr = "";
						if (o.getStatus() != null
								&& o.getStatus() == GiftStatus.SHELF)
							msgStr = "确定上架?";
						else if (o.getStatus() != null
								&& o.getStatus() == GiftStatus.SHELVES)
							msgStr = "确定下架?";
						else
							msgStr = "未知状态";
						win.confirm("提示", msgStr, new ConfirmHandler() {
							@Override
							public void confirm() {
								updateGiftStatus(o.getId(), o.getStatus());
							}
						});
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient arg0) {
						return "查看详细";
					}
				}, new FieldUpdater<GiftClient, String>() {
					@Override
					public void update(int index, GiftClient giftClient,
							String value) {
						giftClient
								.setThisAction(GiftConstants.ACTION_GIFT_VIEW);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										GiftConstants.EDITOR_GIFT_VIEW,
										GiftConstants.EDITOR_GIFT_VIEW
												+ giftClient.getId(),
										giftClient);
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient arg0) {
						return "修改";
					}
				}, new FieldUpdater<GiftClient, String>() {
					@Override
					public void update(int index, final GiftClient giftClient,
							String value) {
						giftClient
								.setThisAction(GiftConstants.ACTION_GIFT_EDIT);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										GiftConstants.EDITOR_GIFT_EDIT,
										GiftConstants.EDITOR_GIFT_EDIT
												+ giftClient.getId(),
										giftClient);
					}
				});

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient gift) {
						return "删除";
					}
				}, new FieldUpdater<GiftClient, String>() {

					@Override
					public void update(int index, GiftClient o, String value) {
						if (Window.confirm("确定删除?")) {
							delteGift(o.getId());
						}
					}

				});

	}

	public void delteGift(String gifId) {

		dispatch.execute(new DeleteGiftRequest(gifId, sessionManager
				.getSession().getToken()),
				new AsyncCallback<DeleteGiftResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(DeleteGiftResponse resp) {
						win.alert("删除成功");
						doSearch();
					}
				});
	}

	public void updateGiftStatus(String gifId, final GiftStatus status) {

		dispatch.execute(new UpdateGiftStatusRequest(gifId, sessionManager
				.getSession().getToken(), status),
				new AsyncCallback<UpdateGiftStatusResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(UpdateGiftStatusResponse resp) {
						if (status == GiftStatus.SHELF)
							win.alert("上架成功!");
						else if (status == GiftStatus.SHELVES)
							win.alert("下架成功!");

						doSearch();
					}
				});
	}
}
