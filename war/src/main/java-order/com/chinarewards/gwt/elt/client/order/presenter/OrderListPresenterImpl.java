package com.chinarewards.gwt.elt.client.order.presenter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.OrderListViewAdapter;
import com.chinarewards.gwt.elt.client.order.presenter.OrderListPresenter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.model.OrderSeacherVo;
import com.chinarewards.gwt.elt.client.order.model.OrderSeacherVo.OrderStatus;
import com.chinarewards.gwt.elt.client.order.plugin.OrderConstants;
import com.chinarewards.gwt.elt.client.order.presenter.OrderListPresenter.OrderListDisplay;
import com.chinarewards.gwt.elt.client.order.request.DeleteOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.DeleteOrderResponse;
import com.chinarewards.gwt.elt.client.order.request.UpdateOrderStatusRequest;
import com.chinarewards.gwt.elt.client.order.request.UpdateOrderStatusResponse;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class OrderListPresenterImpl extends BasePresenter<OrderListDisplay>
		implements OrderListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	EltNewPager pager;
	ListCellTable<OrderVo> cellTable;
	OrderListViewAdapter listViewAdapter;

	@Inject
	public OrderListPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			OrderListDisplay display, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;

	}

	@Override
	public void bind() {
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
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(OrderConstants.EDITOR_ORDER_EDIT,
										OrderConstants.EDITOR_ORDER_EDIT, null);
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
		display.initOrderStatus(map);
		buildTable();
		doSearch();
	}

	private void buildTable() {
		cellTable = new ListCellTable<OrderVo>();

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
		OrderSeacherVo criteria = new OrderSeacherVo();
		if (!StringUtil.isEmpty(display.getKeyName().getValue()))
			criteria.setName(display.getKeyName().getValue());
		if (!StringUtil.isEmpty(display.getStatus()))
			criteria.setStatus(OrderStatus.valueOf(display.getStatus()));

//		listViewAdapter = new OrderListViewAdapter(dispatch, criteria,
//				errorHandler, sessionManager, display);
//		listViewAdapter.addDataDisplay(cellTable);
	}

	private void initTableColumns() {
		Sorting<OrderVo> ref = new Sorting<OrderVo>() {
			@Override
			public void sortingCurrentPage(Comparator<OrderVo> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};

		cellTable.addColumn("名称", new TextCell(),
				new GetValue<OrderVo, String>() {
					@Override
					public String getValue(OrderVo order) {
						return order.getName();
					}
				}, ref, "name");



//		cellTable.addColumn("状态", new TextCell(),
//				new GetValue<OrderVo, String>() {
//					@Override
//					public String getValue(OrderVo order) {
//						return order.getStatus().getDisplayName();
//					}
//				}, ref, "status");

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<OrderVo, String>() {
					@Override
					public String getValue(OrderVo order) {
//						if (order.getStatus() != null
//								&& order.getStatus() == OrderStatus.SHELF)
//							return "上架";
//						else if (order.getStatus() != null
//								&& order.getStatus() == OrderStatus.SHELVES)
//							return "下架";
//						else
//							return "未知状态";
						return "";
					}
				}, new FieldUpdater<OrderVo, String>() {
					@Override
					public void update(int index, final OrderVo o,
							String value) {
						String msgStr = "";
//						if (o.getStatus() != null
//								&& o.getStatus() == OrderStatus.SHELF)
//							msgStr = "确定上架?";
//						else if (o.getStatus() != null
//								&& o.getStatus() == OrderStatus.SHELVES)
//							msgStr = "确定下架?";
//						else
//							msgStr = "未知状态";
//						win.confirm("提示", msgStr, new ConfirmHandler() {
//							@Override
//							public void confirm() {
//								updateOrderStatus(o.getId(), o.getStatus());
//							}
//						});
					}
				});
		
		cellTable.addColumn("查看", new HyperLinkCell(),
				new GetValue<OrderVo, String>() {
					@Override
					public String getValue(OrderVo arg0) {
						return "查看详细";
					}
				}, new FieldUpdater<OrderVo, String>() {
					@Override
					public void update(int index, OrderVo orderClient,
							String value) {
						orderClient.setThisAction(OrderConstants.ACTION_ORDER_VIEW);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										OrderConstants.EDITOR_ORDER_VIEW,
										OrderConstants.EDITOR_ORDER_VIEW
												+ orderClient.getId(), orderClient);
					}
				});
		
		cellTable.addColumn("修改", new HyperLinkCell(),
				new GetValue<OrderVo, String>() {
					@Override
					public String getValue(OrderVo arg0) {
						return "修改";
					}
				}, new FieldUpdater<OrderVo, String>() {
					@Override
					public void update(int index, final OrderVo orderClient,
							String value) {
						orderClient.setThisAction(OrderConstants.ACTION_ORDER_EDIT);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										OrderConstants.EDITOR_ORDER_EDIT,
										OrderConstants.EDITOR_ORDER_EDIT
												+ orderClient.getId(), orderClient);
					}
				});

		

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<OrderVo, String>() {
					@Override
					public String getValue(OrderVo order) {
						return "删除";
					}
				}, new FieldUpdater<OrderVo, String>() {

					@Override
					public void update(int index, OrderVo o, String value) {
						if (Window.confirm("确定删除?")) {
							delteOrder(o.getId());
						}
					}

				});
		
		cellTable.addColumn("购买", new HyperLinkCell(),
				new GetValue<OrderVo, String>() {
					@Override
					public String getValue(OrderVo arg0) {
						return "购买";
					}
				}, new FieldUpdater<OrderVo, String>() {
					@Override
					public void update(int index, OrderVo orderClient,
							String value) {
						orderClient.setThisAction(OrderConstants.ACTION_ORDER_VIEW);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										OrderConstants.EDITOR_ORDER_VIEW,
										OrderConstants.EDITOR_ORDER_VIEW
												+ orderClient.getId(), orderClient);
					}
				});
	}

	public void delteOrder(String gifId) {

		dispatch.execute(new DeleteOrderRequest(gifId, sessionManager
				.getSession().getToken()),
				new AsyncCallback<DeleteOrderResponse>() {

					@Override
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(DeleteOrderResponse resp) {
						win.alert("删除成功");
						doSearch();
					}
				});
	}

	public void updateOrderStatus(String gifId, final OrderStatus status) {

		dispatch.execute(new UpdateOrderStatusRequest(gifId, sessionManager
				.getSession().getToken(), status),
				new AsyncCallback<UpdateOrderStatusResponse>() {

					@Override
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(UpdateOrderStatusResponse resp) {
//						if (status == OrderStatus.SHELF)
//							win.alert("上架成功!");
//						else if (status == OrderStatus.SHELVES)
//							win.alert("下架成功!");

						doSearch();
					}
				});
	}
}
