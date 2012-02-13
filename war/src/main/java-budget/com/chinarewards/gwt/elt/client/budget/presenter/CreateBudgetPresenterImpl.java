package com.chinarewards.gwt.elt.client.budget.presenter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.model.DepBudgetVo;
import com.chinarewards.gwt.elt.client.budget.presenter.CreateBudgetPresenter.CreateBudgetDisplay;
import com.chinarewards.gwt.elt.client.budget.provider.DepBudgetListAdapter;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetResponse;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.order.model.OrderViewClient;
import com.chinarewards.gwt.elt.client.order.plugin.OrderViewConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.SimpleDateTimeProvider;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class CreateBudgetPresenterImpl extends BasePresenter<CreateBudgetDisplay>
		implements CreateBudgetPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;

	EltNewPager pager;
	ListCellTable<DepBudgetVo> cellTable;
	DepBudgetListAdapter listViewAdapter;
	private final BreadCrumbsPresenter breadCrumbs;
	@Inject
	public CreateBudgetPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,BreadCrumbsPresenter breadCrumbs,
			CreateBudgetDisplay display, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;
        this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		init();
		breadCrumbs.loadListPage();
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		registerHandler(display.getSaveBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						doSearch();
					}
				}));
		
			}

	private void init() {
		initYear();
		initDeparts();
		buildTable();
		doSearch();
	}
  
   private void initYear(){
	   
	   dispatch.execute(new InitCorpBudgetRequest(sessionManager.getSession()),
				new AsyncCallback<InitCorpBudgetResponse>() {
		          	@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询出错!");
						
					}

					@Override
					public void onSuccess(InitCorpBudgetResponse response) {
						 List<CorpBudgetVo> list = response.getResult();
						 Map<String, String> map = new HashMap<String, String>();
							map.put("INITIAL", "未付积分");
							map.put("NUSHIPMENTS", " 待发货");
							map.put("SHIPMENTS", "已发货");
							map.put("AFFIRM", "确认发货");
							map.put("ERRORORDER", "问题定单");
							display.initYear(map);
						
					}

				});
	 
   }
   private void initDeparts(){
	   Map<String, String> map = new HashMap<String, String>();
		map.put("inner", "内部直接提供");
		map.put("outter", "外部货品公司提供");
		
		display.initDepart(map);
   }
	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<DepBudgetVo>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(ViewConstants.per_page_number_in_dialog);

		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
	}

	private void doSearch() {
		DepBudgetVo criteria = new DepBudgetVo();
		listViewAdapter = new DepBudgetListAdapter(dispatch, criteria,errorHandler, sessionManager, display);
		listViewAdapter.addDataDisplay(cellTable);
	}

	private void initTableColumns() {
		Sorting<DepBudgetVo> ref = new Sorting<DepBudgetVo>() {
			@Override
			public void sortingCurrentPage(Comparator<DepBudgetVo> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};
		cellTable.addColumn("部门名称", new TextCell(),
				new GetValue<DepBudgetVo, String>() {
					@Override
					public String getValue(DepBudgetVo order) {
						return order.getDepartmentName();
					}
				}, ref, "depname");

		cellTable.addColumn("总积分", new TextCell(),
				new GetValue<DepBudgetVo, String>() {
					@Override
					public String getValue(DepBudgetVo order) {
						return order.getBudgetIntegral()+"";
					}
				}, ref, "budgetIntegral");

		
		
		cellTable.addColumn("已用积分", new TextCell(),
				new GetValue<DepBudgetVo, String>() {
					@Override
					public String getValue(DepBudgetVo order) {
						return order.getUseIntegeral()+"";
					}
				}, ref, "integral");
		

		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<DepBudgetVo, String>() {
					@Override
					public String getValue(DepBudgetVo order) {
						return "查看详细";
					}
				}, new FieldUpdater<DepBudgetVo, String>() {
					@Override
					public void update(int index, final DepBudgetVo order,String value) {
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								OrderViewConstants.EDITOR_ORDERVIEW_SEARCH,
								OrderViewConstants.EDITOR_ORDERVIEW_SEARCH+ order.getId(), order);
					}
				});
		
		
			
	      }




    }
