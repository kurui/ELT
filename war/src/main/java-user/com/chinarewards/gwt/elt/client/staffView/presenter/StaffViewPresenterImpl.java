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
import com.chinarewards.gwt.elt.client.staffView.dataprovider.ExchangeHistoryDataAdapter;
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
	ExchangeHistoryDataAdapter exchangeHistoryDataAdapter;
	
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
		
		registerHandler(display.getupadateBtnClickHandlers().addClickHandler(
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
		
		registerHandler(display.getBtnRewardHistory().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						buildRewardHistoryTable();
						doRewardHistorySearch();
						display.getBtnRewardHistory().setStyleName("menu-link menu-selected");
						display.getBtnExchangeHistory().setStyleName("menu-link");
					}
				}));
		
		registerHandler(display.getBtnExchangeHistory().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						buildExchangeHistoryTable();
						doExchangeSearch();
						display.getBtnRewardHistory().setStyleName("menu-link");
						display.getBtnExchangeHistory().setStyleName("menu-link menu-selected");
						
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

		buildRewardHistoryTable();
		doRewardHistorySearch();
		
		
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
	}


	private void buildRewardHistoryTable() {
		// create a CellTable
		cellTable = new ListCellTable<StaffWinClient>();

		initRewardHistoryTableColumns();
		
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_StaffWin);
		cellTable.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
		
	}
	
	private void buildExchangeHistoryTable() {
		cellTableExchange = new ListCellTable<OrderSearchVo>();

		initExchangeHistoryTableColumns();
		
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTableExchange.setWidth(ViewConstants.page_width);
		cellTableExchange.setPageSize(ViewConstants.per_page_number_in_StaffWin);
		cellTableExchange.getColumn(0).setCellStyleNames("divTextLeft");
		display.getResultPanel().clear();
		display.getResultPanel().add(cellTableExchange);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
		
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
//		criteria.setStaffId(staffId);

		exchangeHistoryDataAdapter = new ExchangeHistoryDataAdapter(dispatch, criteria,
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
//		Sorting<StaffWinClient> ref = new Sorting<StaffWinClient>() {
//			@Override
//			public void sortingCurrentPage(Comparator<StaffWinClient> comparator) {
//				// listViewAdapter.sortCurrentPage(comparator);
//			}
//			@Override
//			public void sortingAll(String sorting, String direction) {
//				exchangeHistoryDataAdapter.sortFromDateBase(sorting, direction);
//			}
//		};

		cellTableExchange.addColumn("订单编号", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo staff) {
						return staff.getOrderCode();
					}
				});
		cellTableExchange.addColumn("礼品名称", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo staff) {
						return staff.getGiftVo().getName();
					}
				});
		cellTableExchange.addColumn("兑换积分", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo staff) {
						return staff.getIntegral()+"";
					}
				});
		cellTableExchange.addColumn("兑换数量", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo staff) {
//						return staff.get;
						return "";
					}
				});
		cellTableExchange.addColumn("礼品来源", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo staff) {
						return staff.getGiftVo().getSourceText();
					}
				});
		cellTableExchange.addColumn("员工名称", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo staff) {
						return staff.getUserId();
					}
				});
		cellTableExchange.addColumn("状态", new TextCell(),
				new GetValue<OrderSearchVo, String>() {
					@Override
					public String getValue(OrderSearchVo staff) {
						return staff.getStatus().getDisplayName();
					}
				});
		
	}


	@Override
	public void initStaffView_Colleague(String staffId,boolean colleague) {
		this.staffId = staffId;
		this.colleague=colleague;
	}
}
