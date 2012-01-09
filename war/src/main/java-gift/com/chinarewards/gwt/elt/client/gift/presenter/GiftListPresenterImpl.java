package com.chinarewards.gwt.elt.client.gift.presenter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.GiftListViewAdapter;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftListPresenter.GiftListDisplay;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.widget.DefaultPager;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.model.gift.GiftClient;
import com.chinarewards.gwt.elt.model.gift.GiftCriteria;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

public class GiftListPresenterImpl extends BasePresenter<GiftListDisplay>
		implements GiftListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;


	SimplePager pager;
	ListCellTable<GiftClient> cellTable;
	GiftListViewAdapter listViewAdapter;

	@Inject
	public GiftListPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			GiftListDisplay display) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;

	}

	@Override
	public void bind() {
		init();
		registerHandler(display.getSearchBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						// Window.alert(sessionManager.getSession().getLoginName());
						init();
					}
				}));
	}

	private void init() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("xxx", "未上架");
		map.put("yyy", "上架");
		display.initGiftStatus(map);
		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<GiftClient>();

		initTableColumns();
		pager = new DefaultPager(TextLocation.CENTER);
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

		criteria.setName(display.getKeyName().getValue());

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
				}, ref, "totalAmtLimit");
		cellTable.addColumn("库存", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient rewards) {
						return rewards.getInventory();
					}
				}, ref, "definition");

		cellTable.addColumn("状态", new TextCell(),
				new GetValue<GiftClient, String>() {
					@Override
					public String getValue(GiftClient rewards) {
						return rewards.getStatus().getDisplayName();
					}
				}, ref, "name");

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

	public void delteReward(String rewardsId) {

//		dispatch.execute(new DeleteGiftRequest(rewardsId, sessionManager
//				.getSession().getToken()),
//				new AsyncCallback<DeleteGiftResponse>() {
//
//					@Override
//					public void onFailure(Throwable t) {
//						Window.alert(t.getMessage());
//					}
//
//					@Override
//					public void onSuccess(DeleteGiftResponse resp) {
//						Window.alert("删除成功");
//						doSearch();
//					}
//				});
	}


}
