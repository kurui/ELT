package com.chinarewards.gwt.elt.client.staffHeavenIndex.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

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
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.google.gwt.cell.client.TextCell;
import com.google.inject.Inject;

public class StaffHeavenIndexPresenterImpl extends
		BasePresenter<StaffHeavenIndexPresenter.StaffHeavenIndexDisplay>
		implements StaffHeavenIndexPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	// private final Win win;
	final ErrorHandler errorHandler;
	EltNewPager pager;
	ListCellTable<StaffHeavenIndexClient> cellTable;
	StaffHeavenIndexViewAdapter listViewAdapter;

	@Inject
	public StaffHeavenIndexPresenterImpl(EventBus eventBus,
			StaffHeavenIndexDisplay display, DispatchAsync dispatch,
			SessionManager sessionManager, ErrorHandler errorHandler) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		// this.win=win;

	}

	@Override
	public void bind() {

		init();

	}

	private void init() {

		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<StaffHeavenIndexClient>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);
		// cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);

	}

	private void doSearch() {
		StaffHeavenIndexCriteria criteria = new StaffHeavenIndexCriteria();

		listViewAdapter = new StaffHeavenIndexViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display);
		listViewAdapter.addDataDisplay(cellTable);

	}

	private void initTableColumns() {

		cellTable.addColumn("广播编号", new TextCell(),
				new GetValue<StaffHeavenIndexClient, String>() {
					@Override
					public String getValue(StaffHeavenIndexClient staff) {
						return staff.getNumber();
					}
				});

	}

}
