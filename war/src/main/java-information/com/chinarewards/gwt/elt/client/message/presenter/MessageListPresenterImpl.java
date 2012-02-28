package com.chinarewards.gwt.elt.client.message.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.message.dataprovider.MessageListViewAdapter;
import com.chinarewards.gwt.elt.client.message.model.MessageListClient;
import com.chinarewards.gwt.elt.client.message.model.MessageListCriteria;
import com.chinarewards.gwt.elt.client.messageSave.dialog.MessageSaveDialog;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MessageListPresenterImpl extends
		BasePresenter<MessageListPresenter.MessageListDisplay> implements
		MessageListPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;
	EltNewPager pager;
	ListCellTable<MessageListClient> cellTable;
	MessageListViewAdapter listViewAdapter;
	private final Provider<MessageSaveDialog> messageSaveDialog;
	@Inject
	public MessageListPresenterImpl(EventBus eventBus,
			MessageListDisplay display, DispatchAsync dispatch,
			SessionManager sessionManager,ErrorHandler errorHandler,Provider<MessageSaveDialog> messageSaveDialog,Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler=errorHandler;
		this.messageSaveDialog=messageSaveDialog;
		this.win=win;

	}
String styleon="";
String styleno="";
	@Override
	public void bind() {
		styleon=display.getReceivedMessage().getElement().getParentElement().getAttribute("class");
		init();

		registerHandler(display.getAddBtn().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						final MessageSaveDialog dialog = messageSaveDialog.get();

						Platform.getInstance().getSiteManager().openDialog(dialog,null);
						
					}
				}));
		registerHandler(display.getReceivedMessage().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.getReceivedMessage().getElement().getParentElement().setAttribute("class", styleon);
						display.getSendMessage().getElement().getParentElement().setAttribute("class", styleno);
						doSearch();
					}
				}));
		registerHandler(display.getSendMessage().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.getReceivedMessage().getElement().getParentElement().setAttribute("class", styleno);
						display.getSendMessage().getElement().getParentElement().setAttribute("class", styleon);
						doSearch();
					}
				}));
	}
	
	private void init() {	

		buildTable();
		doSearch();
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<MessageListClient>();


		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);
	//	cellTable.getColumn(0).setCellStyleNames("divTextLeft");
	//	display.getResultPanel().clear();
	//	display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
		
	}

	private void doSearch() {
		MessageListCriteria criteria = new MessageListCriteria();

		listViewAdapter = new MessageListViewAdapter(dispatch, criteria,
				errorHandler, sessionManager,display,win);
		listViewAdapter.addDataDisplay(cellTable);

	}


}
