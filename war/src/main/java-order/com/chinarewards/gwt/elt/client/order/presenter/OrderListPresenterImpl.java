package com.chinarewards.gwt.elt.client.order.presenter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.OrderListViewAdapter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.order.model.OrderSearchVo;
import com.chinarewards.gwt.elt.client.order.plugin.OrderListConstants;
import com.chinarewards.gwt.elt.client.order.presenter.OrderListPresenter.OrderListDisplay;
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
import com.google.inject.Inject;

public class OrderListPresenterImpl extends BasePresenter<OrderListDisplay>
		implements OrderListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	EltNewPager pager;
	ListCellTable<OrderSearchVo> cellTable;
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
								.openEditor(OrderListConstants.EDITOR_ORDERLIST_SEARCH,
										OrderListConstants.EDITOR_ORDERLIST_SEARCH, null);
					}
				}));
			}

	private void init() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("INITIAL", "未付积分");
		map.put("NUSHIPMENTS", " 待发货");
		map.put("SHIPMENTS", "已发货");
		map.put("AFFIRM", "确认发货");
		map.put("ERRORORDER", "问题定单");
		display.initOrderStatus(map);
		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<OrderSearchVo>();

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
		OrderSearchVo criteria = new OrderSearchVo();
		if (!StringUtil.isEmpty(display.getKeyName().getValue()))
			criteria.setName(display.getKeyName().getValue());
		if (!StringUtil.isEmpty(display.getStatus()))
			criteria.setStatus(OrderStatus.valueOf(display.getStatus()));

		listViewAdapter = new OrderListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display);
		listViewAdapter.addDataDisplay(cellTable);
	}

	private void initTableColumns() {
		Sorting<OrderSearchVo> ref = new Sorting<OrderSearchVo>() {
			@Override
			public void sortingCurrentPage(Comparator<OrderSearchVo> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};
		cellTable.addColumn("订单编号", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo order) {
						return order.getOrderCode();
					}
				}, ref, "orderCode");

		cellTable.addColumn("名称", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo order) {
						return order.getName();
					}
				}, ref, "name");

		
		cellTable.addColumn("数量", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo order) {
						return order.getAmount()+"";
					}
				}, ref, "amonut");
		cellTable.addColumn("兑换积分", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo order) {
						return order.getIntegral()+"";
					}
				}, ref, "integral");
		cellTable.addColumn("兑换员工", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo order) {
						return order.getGiftvo().getName() + "";
					}
				}, ref, "name");   
		cellTable.addColumn("来源", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo order) {
						return order.getGiftvo().getSource() + "";
					}
				}, ref, "source");   
		cellTable.addColumn("状态", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo order) {
						return order.getStatus().getDisplayName();
					}
				}, ref, "status");

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo gift) {
						if (gift.getStatus() != null
								&& gift.getStatus() == OrderStatus.INITIAL)
							return "未付积分";
						else if (gift.getStatus() != null
								&& gift.getStatus() == OrderStatus.NUSHIPMENTS)
							return "待发货";
						else if (gift.getStatus() != null
								&& gift.getStatus() == OrderStatus.SHIPMENTS)
							return "已发货";
						else if (gift.getStatus() != null
								&& gift.getStatus() == OrderStatus.AFFIRM)
							return "已收到货";
						else
							return "问题订单";
					}
				}, new FieldUpdater<OrderSearchVo, String>() {
					@Override
					public void update(int index, final OrderSearchVo o,
							String value) {
						String msgStr = "";
						if (o.getStatus() != null
								&& o.getStatus() == OrderStatus.NUSHIPMENTS)
							msgStr = "确定已发货?";
						if (o.getStatus() != null)
							msgStr = "确定退回?";
						
						win.confirm("提示", msgStr, new ConfirmHandler() {
							@Override
							public void confirm() {
								updateOrderStatus(o.getId(), o.getStatus());
							}
						});
					}
				});

		

//		cellTable.addColumn("查看", new HyperLinkCell(),
//				new GetValue<OrderSeacherVo, String>() {
//					@Override
//					public String getValue(OrderSeacherVo arg0) {
//						return "查看详细";
//					}
//				}, new FieldUpdater<OrderSeacherVo, String>() {
//					@Override
//					public void update(int index, OrderSeacherVo giftClient,
//							String value) {
//						giftClient.setThisAction(OrderListConstants.ACTION_GIFT_VIEW);
//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor(
//										OrderListConstants.EDITOR_GIFT_VIEW,
//										OrderListConstants.EDITOR_GIFT_VIEW
//												+ giftClient.getId(), giftClient);
//					}
//				});

		
	}

	

	public void updateOrderStatus(String gifId, final OrderStatus status) {

//		dispatch.execute(new UpdateOrderStatusRequest(gifId, sessionManager
//				.getSession().getToken(), status),
//				new AsyncCallback<UpdateOrderStatusResponse>() {
//
//					@Override
//					public void onFailure(Throwable t) {
//						Window.alert(t.getMessage());
//					}
//
//					@Override
//					public void onSuccess(UpdateOrderStatusResponse resp) {
//						if (status == OrderStatus.SHELF)
//							win.alert("上架成功!");
//						else if (status == OrderStatus.SHELVES)
//							win.alert("下架成功!");
//
//						doSearch();
//					}
//				});
	}

}
