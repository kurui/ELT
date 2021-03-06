package com.chinarewards.gwt.elt.client.staffHeavenIndex.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.broadcastSave.request.BroadcastSaveRequest;
import com.chinarewards.gwt.elt.client.broadcastSave.request.BroadcastSaveResponse;
import com.chinarewards.gwt.elt.client.broadcasting.model.BroadcastingListCriteria.BroadcastingCategory;
import com.chinarewards.gwt.elt.client.chooseOrganization.model.OrganSearchCriteria.OrganType;
import com.chinarewards.gwt.elt.client.core.presenter.StaffPresenter;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.message.model.MessageListClient;
import com.chinarewards.gwt.elt.client.message.model.MessageListCriteria;
import com.chinarewards.gwt.elt.client.message.request.SearchMessageListRequest;
import com.chinarewards.gwt.elt.client.message.request.SearchMessageListResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.dataprovider.StaffHeavenIndexViewAdapter;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexClient;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.model.StaffHeavenIndexCriteria;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.request.StaffHeavenIndexRequest;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.request.StaffHeavenIndexResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.util.StringUtil;
import com.chinarewards.gwt.elt.client.view.constant.CssStyleConstants;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
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
	private final StaffPresenter staffPresenter;
	final ErrorHandler errorHandler;
	EltNewPager pager;
	ListCellTable<StaffHeavenIndexClient> cellTable;
	StaffHeavenIndexViewAdapter listViewAdapter;
	BroadcastingCategory indexBroadcastingCategory=null;
	String ruietlyCss=display.getReceiveQuietly().getElement().getParentElement().getParentElement().getParentElement().getClassName();
	String heSayCss=display.getHeSay().getElement().getParentElement().getParentElement().getParentElement().getClassName();
	@Inject
	public StaffHeavenIndexPresenterImpl(EventBus eventBus,
			StaffHeavenIndexDisplay display, DispatchAsync dispatch,
			SessionManager sessionManager, ErrorHandler errorHandler, Win win,StaffPresenter staffPresenter) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;
		this.staffPresenter=staffPresenter;

	}

	@Override
	public void bind() {
		display.getReceiveQuietly().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
		display.getHeSay().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
		init();

	     StaffHeavenIndexCriteria  criteria = new StaffHeavenIndexCriteria();
	     criteria.setNowDate(true);
	     //当前可以在顶级显示的广播
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
									 staffPresenter.setTopBroadcast(list.get(t).getContent());
									 t++;
								}
							};

						     timer.schedule(2000);
						     timer.scheduleRepeating(10000); 
						     timer.run();
						}
					}

				});
	 	//当前可以在顶级显示的消息
	 	MessageListCriteria crteria=new MessageListCriteria();
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(0);
		pagination.setLimit(1);
		crteria.setPagination(pagination);

		dispatch.execute(new SearchMessageListRequest(crteria,
				sessionManager.getSession()),
				new AsyncCallback<SearchMessageListResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchMessageListResponse response) {
						List<MessageListClient> giftList = response.getResult();
						if(giftList.size()>0)
						{
							display.setTopMessage(giftList.get(0).getContent());
							display.getCloseMessageBtn().addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									display.getCloseMessageBtn().getElement().getParentElement().addClassName(CssStyleConstants.hidden);
								}
							});
						}
						else
						{
							display.getCloseMessageBtn().getElement().getParentElement().addClassName(CssStyleConstants.hidden);
						}

					}

				});
		//员工主界面添加广播
		display.getAddBroadcastBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				
				if(StringUtil.isEmpty(display.getBroadcastContent()))
				{
					win.alertStaff("请填写广播内容!");
					return;
				}

				BroadcastSaveRequest request = new BroadcastSaveRequest();

				request.setSession(sessionManager.getSession());
				request.setBroadcastingTimeStart(new Date());
				request.setBroadcastingTimeEnd(new Date());
				request.setContent(display.getBroadcastContent());
				
				request.setAllowreplies(true);

				//接收对象为当前人机构
				List<String[]> organList=new ArrayList<String[]>();
				String[] nameAndId = new String[3];
				nameAndId[0] = sessionManager.getSession().getCorporationId();
				nameAndId[1] = sessionManager.getSession().getCorporationName();
				nameAndId[2] = OrganType.ORG.toString();
				organList.add(nameAndId);
				
				request.setOrganList(organList);
				if(display.getMoot())
					request.setBroadcastingCategory(BroadcastingCategory.THEMEBROADCAST);
				else
					request.setBroadcastingCategory(BroadcastingCategory.STAFFBROADCAST);
				
				dispatch.execute(request,
						new AsyncCallback<BroadcastSaveResponse>() {

							@Override
							public void onFailure(Throwable t) {
								win.alert(t.getMessage());
							}

							@Override
							public void onSuccess(BroadcastSaveResponse resp) {
								win.alertStaff("保存成功");
								display.successClean();
								buildTable();
								doSearch(indexBroadcastingCategory,sessionManager.getSession().getToken(),false);
							}
						});
			
			
		
			}
		});
		
	}

	String onStyle = display.getAllInformation().getStyleName();
	String noStyle = display.getStaffInformation().getStyleName();

	String onStylex = display.getReceiveQuietly().getElement().getParentElement().getClassName();
	String noStylex = display.getMyquietly().getElement().getParentElement().getClassName();

	private void init() {

		display.getAllInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getReceiveQuietly().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getHeSay().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getAllInformation().setStyleName(onStyle);
				display.getStaffInformation().setStyleName(noStyle);
				display.getSysInformation().setStyleName(noStyle);
				display.getThemeInformation().setStyleName(noStyle);
				display.getQuietlyInformation().setStyleName(noStyle);
				buildTable();
				doSearch(null,sessionManager.getSession().getToken(),false);
				indexBroadcastingCategory=null;
			}
		});
		display.getStaffInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getHeSay().getElement().getParentElement().getParentElement().getParentElement().setClassName(heSayCss);
				display.getHeSay().getElement().getParentElement().setClassName(onStylex);
				
				
				display.getReceiveQuietly().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getAllInformation().setStyleName(noStyle);
				display.getStaffInformation().setStyleName(onStyle);
				display.getSysInformation().setStyleName(noStyle);
				display.getThemeInformation().setStyleName(noStyle);
				display.getQuietlyInformation().setStyleName(noStyle);
				buildTable();
				doSearch(BroadcastingCategory.STAFFBROADCAST,null,false);
				indexBroadcastingCategory=BroadcastingCategory.STAFFBROADCAST;
			}
		});
		display.getSysInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getReceiveQuietly().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getHeSay().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getAllInformation().setStyleName(noStyle);
				display.getStaffInformation().setStyleName(noStyle);
				display.getSysInformation().setStyleName(onStyle);
				display.getThemeInformation().setStyleName(noStyle);
				display.getQuietlyInformation().setStyleName(noStyle);
				buildTable();
				doSearch(BroadcastingCategory.SYSBROADCAST,null,false);
				indexBroadcastingCategory=BroadcastingCategory.SYSBROADCAST;
			}
		});
		display.getThemeInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getReceiveQuietly().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getHeSay().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getAllInformation().setStyleName(noStyle);
				display.getStaffInformation().setStyleName(noStyle);
				display.getSysInformation().setStyleName(noStyle);
				display.getThemeInformation().setStyleName(onStyle);
				display.getQuietlyInformation().setStyleName(noStyle);
				buildTable();
				doSearch(BroadcastingCategory.THEMEBROADCAST,null,false);
				indexBroadcastingCategory=BroadcastingCategory.THEMEBROADCAST;
			}
		});
		display.getQuietlyInformation().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getHeSay().getElement().getParentElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
				display.getReceiveQuietly().getElement().getParentElement().getParentElement().getParentElement().setClassName(ruietlyCss);
				display.getReceiveQuietly().getElement().getParentElement().setClassName(onStylex);
				display.getMyquietly().getElement().getParentElement().setClassName(noStylex);
				display.getAllInformation().setStyleName(noStyle);
				display.getStaffInformation().setStyleName(noStyle);
				display.getSysInformation().setStyleName(noStyle);
				display.getThemeInformation().setStyleName(noStyle);
				display.getQuietlyInformation().setStyleName(onStyle);
				buildTable();
				doSearch(BroadcastingCategory.QUIETLYINFORMATION,null,false);
				indexBroadcastingCategory=BroadcastingCategory.QUIETLYINFORMATION;
			}
		});
		
		display.getReceiveQuietly().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				display.getReceiveQuietly().getElement().getParentElement().setClassName(onStylex);
				display.getMyquietly().getElement().getParentElement().setClassName(noStylex);
				buildTable();
				doSearch(BroadcastingCategory.QUIETLYINFORMATION,null,false);
				indexBroadcastingCategory=BroadcastingCategory.QUIETLYINFORMATION;
			}
		});
		display.getMyquietly().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getReceiveQuietly().getElement().getParentElement().setClassName(noStylex);
				display.getMyquietly().getElement().getParentElement().setClassName(onStylex);
				buildTable();
				doSearch(BroadcastingCategory.QUIETLYINFORMATION,sessionManager.getSession().getToken(),true);
				indexBroadcastingCategory=BroadcastingCategory.QUIETLYINFORMATION;
			}
		});
		
		display.getHeSay().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				display.getHeSay().getElement().getParentElement().setClassName(onStylex);
				display.getMySay().getElement().getParentElement().setClassName(noStylex);


				buildTable();
				doSearch(BroadcastingCategory.STAFFBROADCAST,null,false);
				indexBroadcastingCategory=BroadcastingCategory.STAFFBROADCAST;
			}
		});
		display.getMySay().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				display.getHeSay().getElement().getParentElement().setClassName(noStylex);
				display.getMySay().getElement().getParentElement().setClassName(onStylex);
				buildTable();
				doSearch(BroadcastingCategory.STAFFBROADCAST,sessionManager.getSession().getToken(),true);
				indexBroadcastingCategory=BroadcastingCategory.STAFFBROADCAST;
			}
		});
		
		
		display.getRefeshxx().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buildTable();
				doSearch(indexBroadcastingCategory,sessionManager.getSession().getToken(),false);

			}
		});
		buildTable();
		doSearch(null,sessionManager.getSession().getToken(),false);
	}

	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<StaffHeavenIndexClient>();

		// initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_staff);
		// cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		// display.getResultPanel().clear();
		// display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);

	}

	private void doSearch(BroadcastingCategory category,String userId,boolean onlyMyself) {
		StaffHeavenIndexCriteria criteria = new StaffHeavenIndexCriteria();
		if (category != null)
			criteria.setCategory(category);
		if(userId != null)
			criteria.setUserId(userId);
		if(onlyMyself==true)
			criteria.setOnlyMyself(onlyMyself);
		listViewAdapter = new StaffHeavenIndexViewAdapter(dispatch, criteria,
				errorHandler, sessionManager, display, win,this);
		listViewAdapter.addDataDisplay(cellTable);

	}

	@Override
	public void reonload() {
		buildTable();
		doSearch(null,sessionManager.getSession().getToken(),false);
		
	}

}
