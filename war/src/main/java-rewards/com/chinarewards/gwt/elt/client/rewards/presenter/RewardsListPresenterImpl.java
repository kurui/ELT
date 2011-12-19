package com.chinarewards.gwt.elt.client.rewards.presenter;

import java.util.Comparator;
import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.RewardsListViewAdapter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.model.DepartmentClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.rewards.presenter.RewardsListPresenter.RewardsListDisplay;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.DefaultPager;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

public class RewardsListPresenterImpl extends BasePresenter<RewardsListDisplay>
		implements RewardsListPresenter {
	
	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	SimplePager pager;
	ListCellTable<RewardsClient> cellTable;
	RewardsListViewAdapter listViewAdapter;

	@Inject
	public RewardsListPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,
			RewardsListDisplay display) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		
	}

	@Override
	public void bind() {
		init();
				registerHandler(display.getNominateClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						
							Window.alert(".................");
						
					}
				}));
	}

	private void init() {
		buildTable();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<RewardsClient>();

		initTableColumns();
		pager = new DefaultPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);

		RewardsCriteria criteria = new RewardsCriteria();
		listViewAdapter = new RewardsListViewAdapter(dispatch, criteria,errorHandler, sessionManager);
		listViewAdapter.addDataDisplay(cellTable);
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultPanel().add(pager);
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
		cellTable.addColumn("奖励名称", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getName();
					}
				}, ref, "name");
		cellTable.addColumn("颁奖日期",
				new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")),
				new GetValue<RewardsClient, Date>() {
					@Override
					public Date getValue(RewardsClient rewards) {
						return rewards.getRewardsDate();
					}
				}, ref, "accountDate");
		cellTable.addColumn("部门", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						if (null != rewards.getBuilderDept()) {
							String s = "";
							// moving from leaf to root.
							DepartmentClient d = rewards.getBuilderDept();
							while (d != null) {
								if (s.length() > 0)
									s = " > " + s;
								s = d.getName() + s;
								d = d.getParent();
							}
							return s;
						} else {
							return "";
						}
					}
				}, ref, "builderDept.name");
		cellTable.addColumn("状态", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getStatus().getDisplayName();
					}
				}, ref, "status");

	}
}
