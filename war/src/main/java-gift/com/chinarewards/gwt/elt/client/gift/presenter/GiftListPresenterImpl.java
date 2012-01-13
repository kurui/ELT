package com.chinarewards.gwt.elt.client.gift.presenter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.GiftListViewAdapter;
import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.client.gift.model.GiftCriteria;
import com.chinarewards.gwt.elt.client.gift.model.GiftCriteria.GiftStatus;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftListPresenter.GiftListDisplay;
import com.chinarewards.gwt.elt.client.gift.request.DeleteGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.DeleteGiftResponse;
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
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class GiftListPresenterImpl extends BasePresenter<GiftListDisplay>
		implements GiftListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	EltNewPager pager;
	ListCellTable<GiftClient> cellTable;
	GiftListViewAdapter listViewAdapter;

	@Inject
	public GiftListPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			GiftListDisplay display, Win win) {
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
						init();
					}
				}));
		registerHandler(display.getAddBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						win.alert("添加新礼品...实现ing~");
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
		display.initGiftStatus(map);
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
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);

		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
	}

	private void doSearch() {
		GiftCriteria criteria = new GiftCriteria();
		if (!StringUtil.isEmpty(display.getKeyName().getValue()))
			criteria.setName(display.getKeyName().getValue());
		if (!StringUtil.isEmpty(display.getStatus()))
			criteria.setStatus(GiftStatus.valueOf(display.getStatus()));

		listViewAdapter = new GiftListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager);
		listViewAdapter.addDataDisplay(cellTable);
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
					public String getValue(GiftClient rewards) {
						return rewards.getName();
					}
				}, ref, "name");

		cellTable.addColumn("来源", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient rewards) {
						return rewards.getSource() + "";
					}
				}, ref, "source");
		cellTable.addColumn("库存", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient rewards) {
						return rewards.getInventory();
					}
				}, ref, "inventory");

		cellTable.addColumn("状态", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient rewards) {
						return rewards.getStatus().getDisplayName();
					}
				}, ref, "status");

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient rewards) {
						return "删除";
					}
				}, new FieldUpdater<GiftClient, String>() {

					@Override
					public void update(int index, GiftClient o, String value) {
						if (Window.confirm("确定删除?")) {
							delteReward(o.getId());
						}
					}

				});
	}

	public void delteReward(String gifId) {

		dispatch.execute(new DeleteGiftRequest(gifId, sessionManager
				.getSession().getToken()),
				new AsyncCallback<DeleteGiftResponse>() {

					@Override
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(DeleteGiftResponse resp) {
						win.alert("删除成功");
						doSearch();
					}
				});
	}

}
