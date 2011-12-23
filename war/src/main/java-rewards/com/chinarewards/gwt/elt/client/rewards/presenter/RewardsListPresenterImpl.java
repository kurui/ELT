package com.chinarewards.gwt.elt.client.rewards.presenter;

import java.util.Comparator;
import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.awardReward.plugin.AwardRewardConstants;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.dataprovider.RewardsListViewAdapter;
import com.chinarewards.gwt.elt.client.detailsOfAward.plugin.DetailsOfAwardConstants;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.nominate.plugin.NominateConstants;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.rewards.presenter.RewardsListPresenter.RewardsListDisplay;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.widget.DefaultPager;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
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
		registerHandler(display.getSearchBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						init();
					}
				}));
	}

	private void init() {
		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<RewardsClient>();

		initTableColumns();
		pager = new DefaultPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);
		
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultPanel().add(pager);
	}
	private void doSearch(){
		RewardsCriteria criteria = new RewardsCriteria();
		criteria.setName(display.getName().getValue());
		criteria.setDefinition(display.getDefinition().getValue());
		listViewAdapter = new RewardsListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager);
		listViewAdapter.addDataDisplay(cellTable);
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
		cellTable.addColumn("奖项编号", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return "01";
					}
				}, ref, "id");

		cellTable.addColumn("奖励名称", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getName();
					}
				}, ref, "name");

		cellTable.addColumn("奖项积分", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getTotalAmtLimit() + "";
					}
				}, ref, "totalAmtLimit");
		cellTable.addColumn("说明", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getDefinition();
					}
				}, ref, "definition");
		cellTable.addColumn("发起人", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return rewards.getCreatedBy();
					}
				}, ref, "createdBy");
		cellTable.addColumn("颁奖方式", new TextCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return "领导提名";
					}
				}, ref, "name");
		cellTable.addColumn("预计提名时间",
				new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")),
				new GetValue<RewardsClient, Date>() {
					@Override
					public Date getValue(RewardsClient rewards) {
						return rewards.getExpectNominateDate();
					}
				}, ref, "expectNominateDate");
		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return "提名";
					}
				}, new FieldUpdater<RewardsClient, String>() {

					@Override
					public void update(int index, RewardsClient o, String value) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										NominateConstants.EDITOR_NOMINATE_SEARCH,
										NominateConstants.EDITOR_NOMINATE_SEARCH
												+ o.getId(), o);

					}

				});
		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return "颁奖";
					}
				}, new FieldUpdater<RewardsClient, String>() {

					@Override
					public void update(int index, RewardsClient o, String value) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										AwardRewardConstants.EDITOR_AWARDREWARD_SEARCH,
										AwardRewardConstants.EDITOR_AWARDREWARD_SEARCH
												+ o.getId(), o);

					}

				});
		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<RewardsClient, String>() {
					@Override
					public String getValue(RewardsClient rewards) {
						return "颁奖详细";
					}
				}, new FieldUpdater<RewardsClient, String>() {

					@Override
					public void update(int index, RewardsClient o, String value) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										DetailsOfAwardConstants.EDITOR_DETAILSOFAWARD_SEARCH,
										DetailsOfAwardConstants.EDITOR_DETAILSOFAWARD_SEARCH
												+ o.getId(), o);

					}

				});

	}
}
