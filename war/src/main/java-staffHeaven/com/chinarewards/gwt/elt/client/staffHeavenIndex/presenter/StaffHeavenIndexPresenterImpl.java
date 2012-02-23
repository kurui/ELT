package com.chinarewards.gwt.elt.client.staffHeavenIndex.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.broadcasting.model.BroadcastingListCriteria.BroadcastingCategory;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.dataprovider.StaffHeavenIndexViewAdapter;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexClient;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class StaffHeavenIndexPresenterImpl extends
		BasePresenter<StaffHeavenIndexPresenter.StaffHeavenIndexDisplay>
		implements StaffHeavenIndexPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;
	EltNewPager pager;
	ListCellTable<StaffHeavenIndexClient> cellTable;
	StaffHeavenIndexViewAdapter listViewAdapter;

	@Inject
	public StaffHeavenIndexPresenterImpl(EventBus eventBus,
			StaffHeavenIndexDisplay display, DispatchAsync dispatch,
			SessionManager sessionManager, ErrorHandler errorHandler, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;

	}

	@Override
	public void bind() {

		init();

	}

	String onStyle = display.getAllInformation().getStyleName();
	String noStyle = display.getStaffInformation().getStyleName();

	private void init() {

		display.getAllInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getAllInformation().setStyleName(onStyle);
				display.getStaffInformation().setStyleName(noStyle);
				display.getSysInformation().setStyleName(noStyle);
				display.getThemeInformation().setStyleName(noStyle);
				doSearch(null);
			}
		});
		display.getStaffInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getAllInformation().setStyleName(noStyle);
				display.getStaffInformation().setStyleName(onStyle);
				display.getSysInformation().setStyleName(noStyle);
				display.getThemeInformation().setStyleName(noStyle);
				doSearch(BroadcastingCategory.STAFFBROADCAST);
			}
		});
		display.getSysInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getAllInformation().setStyleName(noStyle);
				display.getStaffInformation().setStyleName(noStyle);
				display.getSysInformation().setStyleName(onStyle);
				display.getThemeInformation().setStyleName(noStyle);
				doSearch(BroadcastingCategory.SYSBROADCAST);
			}
		});
		display.getThemeInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getAllInformation().setStyleName(noStyle);
				display.getStaffInformation().setStyleName(noStyle);
				display.getSysInformation().setStyleName(noStyle);
				display.getThemeInformation().setStyleName(onStyle);
				doSearch(BroadcastingCategory.THEMEBROADCAST);
			}
		});
		buildTable();
		doSearch(null);
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<StaffHeavenIndexClient>();

		// initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);
		// cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		// display.getResultPanel().clear();
		// display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);

	}

	private void doSearch(BroadcastingCategory category) {
		StaffHeavenIndexCriteria criteria = new StaffHeavenIndexCriteria();
		if (category != null)
			criteria.setCategory(category);
		listViewAdapter = new StaffHeavenIndexViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display, win);
		listViewAdapter.addDataDisplay(cellTable);

	}

}
