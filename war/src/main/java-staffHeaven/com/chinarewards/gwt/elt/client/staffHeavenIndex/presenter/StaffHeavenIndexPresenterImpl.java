package com.chinarewards.gwt.elt.client.staffHeavenIndex.presenter;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.broadcasting.model.BroadcastingListCriteria.BroadcastingCategory;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.dataprovider.StaffHeavenIndexViewAdapter;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexClient;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexCriteria;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.request.StaffHeavenIndexRequest;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.request.StaffHeavenIndexResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

	     StaffHeavenIndexCriteria  criteria = new StaffHeavenIndexCriteria();
	     
	     //条件未加
	 	dispatch.execute(new StaffHeavenIndexRequest(criteria,
				sessionManager.getSession()),
				new AsyncCallback<StaffHeavenIndexResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(StaffHeavenIndexResponse response) {

						final List<StaffHeavenIndexClient> list=response.getResult();
						if(list.size()>0)
						{
							final int k = 0;
							Timer timer=new Timer() {
								int t = k;
								@Override
								public void run() {
									 
									 if(t>=list.size())
										t=0;
									 display.setTopBroadcast(list.get(t).getContent());
									 t++;
								}
							};

						     timer.schedule(2000);
						     timer.scheduleRepeating(10000); 
						     timer.run();
						}
					}

				});
		
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
