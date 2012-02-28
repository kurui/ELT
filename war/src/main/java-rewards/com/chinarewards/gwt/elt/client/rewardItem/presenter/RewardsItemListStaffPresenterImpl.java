package com.chinarewards.gwt.elt.client.rewardItem.presenter;

import java.util.Comparator;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.RewardsItemListStaffViewAdapter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListStaffPresenter.RewardsItemListStaffDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemStaffClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemStaffCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.google.gwt.cell.client.TextCell;
import com.google.inject.Inject;

public class RewardsItemListStaffPresenterImpl extends
		BasePresenter<RewardsItemListStaffDisplay> implements
		RewardsItemListStaffPresenter {
	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	RewardPageType pageType;

	EltNewPager pager;
	ListCellTable<RewardsItemStaffClient> cellTable;
	RewardsItemListStaffViewAdapter listViewAdapter;

	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public RewardsItemListStaffPresenterImpl(EventBus eventBus,
			DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager, RewardsItemListStaffDisplay display,
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

		iniWidget();

//		registerHandler(display.getSearchBtnClickHandlers().addClickHandler(
//				new ClickHandler() {
//					public void onClick(ClickEvent paramClickEvent) {
//						// Window.alert(sessionManager.getSession().getLoginName());
//						iniWidget();
//					}
//				}));
	}

	private void iniWidget() {
		buildTable();
		doSearch();
	}

	private void buildTable() {
		cellTable = new ListCellTable<RewardsItemStaffClient>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);
		cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);

	}

	private void doSearch() {
		RewardsItemStaffCriteria criteria = new RewardsItemStaffCriteria();
//		criteria.setName(display.getName().getValue());
//		criteria.setDefinition(display.getDefinition().getValue());

		listViewAdapter = new RewardsItemListStaffViewAdapter(dispatch,
				criteria, errorHandler, sessionManager, display);
		listViewAdapter.addDataDisplay(cellTable);

	}

	private void initTableColumns() {
		Sorting<RewardsItemStaffClient> ref = new Sorting<RewardsItemStaffClient>() {
			@Override
			public void sortingCurrentPage(
					Comparator<RewardsItemStaffClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);
			}
		};

		cellTable.addColumn("奖项编号", new HyperLinkCell(),
				new GetValue<RewardsItemStaffClient, String>() {
					@Override
					public String getValue(RewardsItemStaffClient rewards) {
						return rewards.getName();
					}
				}, ref, "name");

		cellTable.addColumn("奖项名称", new HyperLinkCell(),
				new GetValue<RewardsItemStaffClient, String>() {
					@Override
					public String getValue(RewardsItemStaffClient rewards) {
						return rewards.getName();
					}
				}, ref, "name");

		cellTable.addColumn("奖励状态", new TextCell(),
				new GetValue<RewardsItemStaffClient, String>() {
					@Override
					public String getValue(RewardsItemStaffClient rewards) {
						if (rewards.isEnabled() == true) {
							return "已激活";
						} else {
							return "未激活";
						}

					}
				}, ref, "name");

		cellTable.addColumn("创建人", new TextCell(),
				new GetValue<RewardsItemStaffClient, String>() {
					@Override
					public String getValue(RewardsItemStaffClient rewards) {
						return rewards.getCreatedBy();
					}
				}, ref, "createdBy");

		cellTable.addColumn("获奖人", new TextCell(),
				new GetValue<RewardsItemStaffClient, String>() {
					@Override
					public String getValue(RewardsItemStaffClient rewards) {
						return rewards.getCreatedBy();
					}
				}, ref, "createdBy");

	}

	@Override
	public void initRewardsItemList(RewardPageType pageType) {
		this.pageType = pageType;
	}
}
