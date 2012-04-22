package com.chinarewards.gwt.elt.client.staffView.presenter;

import java.util.Comparator;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.order.model.OrderSearchVo;
import com.chinarewards.gwt.elt.client.staffAdd.plugin.StaffAddConstants;
import com.chinarewards.gwt.elt.client.staffIntegral.request.StaffIntegralRequest;
import com.chinarewards.gwt.elt.client.staffIntegral.request.StaffIntegralResponse;
import com.chinarewards.gwt.elt.client.staffView.dataprovider.StaffExchangeHistoryDataAdapter;
import com.chinarewards.gwt.elt.client.staffView.dataprovider.StaffWinAdapter;
import com.chinarewards.gwt.elt.client.staffView.model.StaffWinClient;
import com.chinarewards.gwt.elt.client.staffView.model.StaffWinCriteria;
import com.chinarewards.gwt.elt.client.staffView.request.StaffViewRequest;
import com.chinarewards.gwt.elt.client.staffView.request.StaffViewResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.CssStyleConstants;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.util.DateTool;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class StaffViewPresenterImpl extends
		BasePresenter<StaffViewPresenter.StaffViewDisplay> implements
		StaffViewPresenter {

	private final DispatchAsync dispatch;
	private final SessionManager sessionManager;
	private final Win win;
	final ErrorHandler errorHandler;

	private final BreadCrumbsPresenter breadCrumbs;
	String staffId;
	boolean colleague=false;
	EltNewPager pager;
	ListCellTable<StaffWinClient> cellTable;
	
	StaffWinAdapter rewardlistViewAdapter;
	
	ListCellTable<OrderSearchVo> cellTableExchange;
	StaffExchangeHistoryDataAdapter exchangeHistoryDataAdapter;
	
	private String tabCloseClass="";
	private String tabSelectedClass="";
	private String normalListPagePanelClass="";
	private String normalIntegralPanelClass="";
	
	private String thisAction="";
	
	int pageSize=ViewConstants.per_page_number_in_entry;
	
	@Inject
	public StaffViewPresenterImpl(EventBus eventBus, StaffViewDisplay display,
			DispatchAsync dispatch, SessionManager sessionManager, Win win,
			BreadCrumbsPresenter breadCrumbs, ErrorHandler errorHandler) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;
		this.errorHandler = errorHandler;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("员工详细信息");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		
		initWidget();
		
		registerHandler(display.getUpadateBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								StaffAddConstants.EDITOR_STAFFADD_SEARCH,
								"EDITOR_STAFFADD_SEARCH_DO_ID", staffId);
					}
				}));
		registerHandler(display.getBtnIntegral().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						buildIntegralTable();
						display.getBtnIntegral().setStyleName(tabSelectedClass);
						display.getBtnRewardHistory().setStyleName(tabCloseClass);
						display.getBtnExchangeHistory().setStyleName(tabCloseClass);
					}
				}));
		
		registerHandler(display.getBtnRewardHistory().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						buildRewardHistoryTable();
						doRewardHistorySearch();
						display.getBtnIntegral().setStyleName(tabCloseClass);
						display.getBtnRewardHistory().setStyleName(tabSelectedClass);
						display.getBtnExchangeHistory().setStyleName(tabCloseClass);
					}
				}));
		
		registerHandler(display.getBtnExchangeHistory().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						buildExchangeHistoryTable();
						doExchangeSearch();
						display.getBtnIntegral().setStyleName(tabCloseClass);
						display.getBtnRewardHistory().setStyleName(tabCloseClass);
						display.getBtnExchangeHistory().setStyleName(tabSelectedClass);
						
					}
				}));
		
		registerHandler(display.getPageNumber().addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				pageSize=Integer.parseInt(display.getPageNumber().getValue(display.getPageNumber().getSelectedIndex()));
				
			}
		}));

	}

	private void initWidget() {
		if(colleague==true)
		{
			display.displayUpdateBtn(colleague);
		}
		if(sessionManager.getSession().getLastLoginRole()!=UserRoleVo.CORP_ADMIN)
		{
			display.displayUpdateBtn(true);
		}
		
		initStatff();

//		buildRewardHistoryTable();
//		doRewardHistorySearch();
		
		buildIntegralTable();
		
		tabSelectedClass=display.getBtnIntegral().getStyleName();
		tabCloseClass=display.getBtnRewardHistory().getStyleName();
		normalListPagePanelClass=display.getDataCount().getElement().getParentElement().getParentElement().getClassName();
		
		normalIntegralPanelClass=display.getHistoryIntegral().getElement().getParentElement().getParentElement().getParentElement().getClassName();

		
	}

	@Override
	public void initStaffView(String staffId) {
		this.staffId = staffId;

	}

	private void initStatff(){
		dispatch.execute(new StaffViewRequest(staffId),
				new AsyncCallback<StaffViewResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(StaffViewResponse resp) {
						
						display.setStaffNo(resp.getStaffNo());

						display.setStaffName(resp.getStaffName());

						if(resp.getDepartmentName().indexOf("ROOT")==-1)
						display.setDepartmentName(resp.getDepartmentName());
			
						display.setJobPosition(resp.getJobPosition());

						display.setLeadership(resp.getLeadership());

						display.setPhone(resp.getPhone());

						display.setEmail(resp.getEmail());

						display.setDob(DateTool.dateToString(resp.getDob()));

						display.setStaffImage(resp.getPhoto());
						if(resp.getStatus()!=null)
						display.setStaffStatus(resp.getStatus().toString());
						if(resp.getUserRoleVos()!=null && resp.getUserRoleVos().size()>0)
						{
							String roleString="";
							for (UserRoleVo role:resp.getUserRoleVos()) {
								if(role==UserRoleVo.CORP_ADMIN)
									roleString+="HR管理员;";
								else if(role==UserRoleVo.GIFT)
									roleString+="礼品管理员;";
							}
							if(!StringUtil.isEmpty(roleString))
								display.getStaffRoles().setText(roleString);
							else
								display.getStaffRoles().getElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
							
						}
						else
						{
							display.getStaffRoles().getElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
						}
					}
				});
		
		display.getPageNumber().clear();
		display.getPageNumber().addItem("5", "5");
		display.getPageNumber().addItem("20", "20");
		display.getPageNumber().addItem("50", "50");
		
		inistIntegral();
	
	}
	private void inistIntegral(){
		String staffId = sessionManager.getSession().getStaffId();
		dispatch.execute(new StaffIntegralRequest(staffId),
				new AsyncCallback<StaffIntegralResponse>() {

					@Override
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(StaffIntegralResponse resp) {
						display.getHistoryIntegral().setText(StringUtil.subZeroAndDot(resp.getHistoryIntegral()));
						display.getConsumptionIntegral().setText(StringUtil.subZeroAndDot(resp.getConsumptionIntegral()));
						display.getBalanceIntegral().setText(StringUtil.subZeroAndDot(resp.getBalanceIntegral()));

					}
				});
	}
	
	private void buildIntegralTable() {		
		display.getDetailTitle().setText("积分信息");
		display.getResultPanel().clear();
		display.getResultpage().clear();
		
		display.getHistoryIntegral().getElement().getParentElement().getParentElement().setClassName(normalIntegralPanelClass);
		display.getDataCount().getElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);		
		
		inistIntegral();
		
		thisAction="searchIntegral";
	}

	private void buildRewardHistoryTable() {
		display.getDataCount().getElement().getParentElement().getParentElement().setClassName(normalIntegralPanelClass);	
		display.getHistoryIntegral().getElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
		
		display.getDetailTitle().setText("获奖历史");
		
		// create a CellTable
		cellTable = new ListCellTable<StaffWinClient>();

		initRewardHistoryTableColumns();
		
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(pageSize);
		cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
		
		thisAction="searchRewardHistory";
	}
	
	private void buildExchangeHistoryTable() {
		display.getDataCount().getElement().getParentElement().getParentElement().setClassName(normalIntegralPanelClass);
		display.getHistoryIntegral().getElement().getParentElement().getParentElement().setClassName(CssStyleConstants.hidden);
		
		display.getDetailTitle().setText("兑换历史");
		
		cellTableExchange = new ListCellTable<OrderSearchVo>();

		initExchangeHistoryTableColumns();
		
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTableExchange.setWidth(ViewConstants.page_width);
		cellTableExchange.setPageSize(pageSize);
		cellTableExchange.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTableExchange);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
		
		thisAction="searchExchangeHistory";
		
	}

	private void doRewardHistorySearch() {
		StaffWinCriteria criteria = new StaffWinCriteria();
		criteria.setStaffId(staffId);

		rewardlistViewAdapter = new StaffWinAdapter(dispatch, criteria,
				errorHandler, sessionManager,display);
		rewardlistViewAdapter.addDataDisplay(cellTable);

	}
	

	private void doExchangeSearch() {
		OrderSearchVo criteria = new OrderSearchVo();
		criteria.setStaffId(staffId);
		exchangeHistoryDataAdapter = new StaffExchangeHistoryDataAdapter(dispatch, criteria,
				errorHandler, sessionManager,display);
		exchangeHistoryDataAdapter.addDataDisplay(cellTableExchange);

		
	}

	private void initRewardHistoryTableColumns() {
		Sorting<StaffWinClient> ref = new Sorting<StaffWinClient>() {
			@Override
			public void sortingCurrentPage(Comparator<StaffWinClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				rewardlistViewAdapter.sortFromDateBase(sorting, direction);

			}
		};


		cellTable.addColumn("奖项名称", new TextCell(),
				new GetValue<StaffWinClient, String>() {
					@Override
					public String getValue(StaffWinClient staff) {
						return staff.getRewardName();
					}
				}, ref, "reward.name");
		cellTable.addColumn("获奖时间", new TextCell(),
				new GetValue<StaffWinClient, String>() {
					@Override
					public String getValue(StaffWinClient staff) {
						return DateTool.dateToStringChina(staff.getWinTime());
					}
				}, ref, "winTime");
		cellTable.addColumn("颁奖人", new TextCell(),
				new GetValue<StaffWinClient, String>() {
					@Override
					public String getValue(StaffWinClient staff) {
						return staff.getPresentedName();
					}
				});
		
	}
	
	private void initExchangeHistoryTableColumns() {
		Sorting<StaffWinClient> ref = new Sorting<StaffWinClient>() {
			@Override
			public void sortingCurrentPage(Comparator<StaffWinClient> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}
			@Override
			public void sortingAll(String sorting, String direction) {
				exchangeHistoryDataAdapter.sortFromDateBase(sorting, direction);
			}
		};

		cellTableExchange.addColumn("订单编号", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo searchVo) {
						return searchVo.getOrderCode();
					}
				});
		cellTableExchange.addColumn("礼品名称", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo searchVo) {
						return searchVo.getGiftVo().getName();
					}
				});
		cellTableExchange.addColumn("兑换积分", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo searchVo) {
						return searchVo.getIntegral()+"";
					}
				});
		cellTableExchange.addColumn("兑换数量", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo searchVo) {
						return searchVo.getAmount()+"";
					}
				});
		cellTableExchange.addColumn("礼品来源", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo searchVo) {
						return searchVo.getGiftVo().getSourceText();
					}
				});
		cellTableExchange.addColumn("员工名称", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo searchVo) {
						return searchVo.getName();
					}
				});
		cellTableExchange.addColumn("状态", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo searchVo) {
						return searchVo.getStatus().getDisplayName();
					}
				});
		
	}


	@Override
	public void initStaffView_Colleague(String staffId,boolean colleague) {
		this.staffId = staffId;
		this.colleague=colleague;
	}
}
