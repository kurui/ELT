package com.chinarewards.gwt.elt.client.rewardItem.presenter;

import java.util.Comparator;
import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.RewardsItemListStaffViewAdapter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemListStaffPresenter.RewardsItemListStaffDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria.RewardsStatus;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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
	ListCellTable<RewardsClient> cellTable;
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

		registerHandler(display.getSearchBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						// Window.alert(sessionManager.getSession().getLoginName());
						iniWidget();
					}
				}));
	}

	private void iniWidget() {
		buildTable();
		doSearch();
	}

	private void buildTable() {
		cellTable = new ListCellTable<RewardsClient>();

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
		RewardsItemCriteria criteria = new RewardsItemCriteria();
		criteria.setName(display.getName().getValue());
		criteria.setDefinition(display.getDefinition().getValue());

		
		listViewAdapter = new RewardsItemListStaffViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display);
//		listViewAdapter.addDataDisplay(cellTable);

	}

	private void initTableColumns() {
		Sorting<RewardsClient> ref = new Sorting<RewardsClient>() {
			@Override
			public void sortingCurrentPage(Comparator<RewardsClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);
			}
		};

		cellTable.addColumn("奖项名称", new HyperLinkCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getName();
					}
				}, ref, "name");

		cellTable.addColumn("奖励积分", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						int total = (int) (rewards.getTotalAmtLimit());
						return total + "";
					}
				}, ref, "totalAmtLimit");

		cellTable.addColumn("奖励时间",
				new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")),
				new GetValue<RewardsClient, Date>() {
					@Override
					public Date getValue(RewardsClient rewards) {
						return rewards.getExpectNominateDate();
					}
				}, ref, "expectNominateDate");

		cellTable.addColumn("颁奖人", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getCreatedBy();
					}
				}, ref, "createdBy");

		cellTable.addColumn("获奖人", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getCreatedBy();
					}
				}, ref, "createdBy");

	}

	@Override
	public void initRewardsItemList(RewardPageType pageType) {
		this.pageType = pageType;
	}
}
