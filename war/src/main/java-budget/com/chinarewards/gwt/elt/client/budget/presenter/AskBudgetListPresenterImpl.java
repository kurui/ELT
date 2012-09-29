package com.chinarewards.gwt.elt.client.budget.presenter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetListPresenter.AskBudgetListDisplay;
import com.chinarewards.gwt.elt.client.budget.provider.AskBudgetListAdapter;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetResponse;
import com.chinarewards.gwt.elt.client.budget.request.InitDepartmentRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitDepartmentResponse;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.client.ui.UniversalCell;
import com.chinarewards.gwt.elt.client.widget.EltNewPager;
import com.chinarewards.gwt.elt.client.widget.EltNewPager.TextLocation;
import com.chinarewards.gwt.elt.client.widget.GetValue;
import com.chinarewards.gwt.elt.client.widget.ListCellTable;
import com.chinarewards.gwt.elt.client.widget.Sorting;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class AskBudgetListPresenterImpl extends BasePresenter<AskBudgetListDisplay>
		implements AskBudgetListPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
    String corpBudgetId="";
    double remainCount;//剩余的总积分
   
	EltNewPager pager;
	ListCellTable<AskBudgetClientVo> cellTable;
	AskBudgetListAdapter listViewAdapter;
	private final BreadCrumbsPresenter breadCrumbs;
	int pageSize=ViewConstants.per_page_number_in_dialog;
	@Inject
	public AskBudgetListPresenterImpl(EventBus eventBus, DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager,BreadCrumbsPresenter breadCrumbs,
			AskBudgetListDisplay display, Win win) {
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
        registerHandler(display.getPageNumber().addChangeHandler(new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			pageSize=Integer.parseInt(display.getPageNumber().getValue(display.getPageNumber().getSelectedIndex()));
			buildTable();
			toSearch(display.getManageDep().getValue(display.getManageDep().getSelectedIndex()));
		}
		}));
         registerHandler(display.getManageDep().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				toPrush();
			}
		}));
		registerHandler(display.getSearchBtnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						toSearch(display.getManageDep().getValue(display.getManageDep().getSelectedIndex()));
					}
		}));
		registerHandler(display.getAddBtnClickHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AskBudgetClientVo vo = new AskBudgetClientVo();
				Platform.getInstance()
				.getEditorRegistry()
				.openEditor(AskBudgetConstants.EDITOR_ADD_BUDGET,
						AskBudgetConstants.ACTION_ASKBUDGET_ADD, vo);
			}
		}));
		
	}

	private void init() {
		buildTable();
		initYear();
			
	}
  
	      
	
	 private void initYear(){
		   
		   dispatch.execute(new InitCorpBudgetRequest(sessionManager.getSession(),""),
					new AsyncCallback<InitCorpBudgetResponse>() {
			          	@Override
						public void onFailure(Throwable arg0) {
							errorHandler.alert("查询财年周期出错!");
							
						}

						@Override
						public void onSuccess(InitCorpBudgetResponse response) {
							 List<CorpBudgetVo> list = response.getResult();
							 Map<String, String> map = new HashMap<String, String>();
							 Map<String, String> depmap = new HashMap<String, String>();
							 map.clear(); depmap.clear();
							 CorpBudgetVo vo = new CorpBudgetVo();
							 if(list.size()>0){
								 for(int i=0;i<list.size();i++){
									   vo = list.get(i);
									   map.put(vo.getId(), vo.getBudgetTitle());
									   depmap.put(vo.getDepartmentId(), vo.getDepartmentName());
									   corpBudgetId = vo.getId();
										  
									   display.setTotalCount(vo.getBudgetIntegral()+"");
									   display.setRemainCount((vo.getBudgetIntegral()-vo.getUseIntegeral())+"");
									   remainCount = vo.getBudgetIntegral()-vo.getUseIntegeral();
								 }
								  							  
									
									
							 }else{
								 display.setTotalCount("0");
								 display.setRemainCount("0");
								 map.put(vo.getId(), "");
								
							 }
							    
							    display.initYear(map);	
							    display.initManageDepart(depmap);
							    AskBudgetClientVo criteria = new AskBudgetClientVo();
								criteria.setCorpBudgetId(corpBudgetId);
								criteria.setManageDepId(display.getManageDep().getValue(display.getManageDep().getSelectedIndex()));
								listViewAdapter = new AskBudgetListAdapter(dispatch, criteria,errorHandler, sessionManager, display);
								listViewAdapter.addDataDisplay(cellTable);
								List<UserRoleVo> roles = Arrays.asList(sessionManager.getSession().getUserRoles());
								if(roles.contains(UserRoleVo.CORP_ADMIN)){
									initDeparts("1");//HR得到一级部门
									display.setDisplay();
								}else
									initDeparts(display.getManageDep().getValue(display.getManageDep().getSelectedIndex())); //得到所管部门
								
						}

					});
		 
	   }
  
   private void toSearch(final String depId){
	   dispatch.execute(new InitCorpBudgetRequest(sessionManager.getSession(),depId),
				new AsyncCallback<InitCorpBudgetResponse>() {
		          	@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询财年周期出错!");
						
					}

					@Override
					public void onSuccess(InitCorpBudgetResponse response) {
						
						  List<CorpBudgetVo> list = response.getResult();
						  CorpBudgetVo vo = new CorpBudgetVo();
						 if(list.size()>0){//得到下拉框所选择的财年周期
							 for(int i=0;i<list.size();i++){
								 if(list.get(i).getId().equals(display.getYear())){
								   vo = list.get(i);
								 }
								  
							 }
							 //刷新列表和预算总数
							    corpBudgetId = vo.getId();
							    display.setTotalCount(vo.getBudgetIntegral()+"");
							    display.setRemainCount((vo.getBudgetIntegral()-vo.getUseIntegeral())+"");
							    remainCount = vo.getBudgetIntegral()-vo.getUseIntegeral();
							    List<UserRoleVo> roles = Arrays.asList(sessionManager.getSession().getUserRoles());
//								if(roles.contains(UserRoleVo.CORP_ADMIN)){
//									initDeparts("1");//HR得到一级部门
//									display.setDisplay();
//								}else
//									initDeparts(display.getManageDep().getValue(display.getManageDep().getSelectedIndex())); //得到所管部门
								
								AskBudgetClientVo criteria = new AskBudgetClientVo();
								criteria.setManageDepId(depId);
								criteria.setCorpBudgetId(corpBudgetId);
								criteria.setManageDepId(depId);
								criteria.setDepartmentId(display.getDepart());
								listViewAdapter = new AskBudgetListAdapter(dispatch, criteria,errorHandler, sessionManager, display);
								listViewAdapter.addDataDisplay(cellTable);

						 }else{
							 display.setTotalCount("0");
							 display.setRemainCount("0");
							
						 }
							
							
						
					}

				});
	   
   }
   private void toPrush(){
	   String manageDepId = display.getManageDep().getValue(display.getManageDep().getSelectedIndex());
	   dispatch.execute(new InitCorpBudgetRequest(sessionManager.getSession(),manageDepId),
				new AsyncCallback<InitCorpBudgetResponse>() {
		          	@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询财年周期出错!");
						
					}

					@Override
					public void onSuccess(InitCorpBudgetResponse response) {
						
						  List<CorpBudgetVo> list = response.getResult();
						  CorpBudgetVo vo = new CorpBudgetVo();
						 if(list.size()>0){//得到下拉框所选择的财年周期
							 for(int i=0;i<list.size();i++){
								 if(list.get(i).getId().equals(display.getYear())){
								   vo = list.get(i);
								 }
								  
							 }
							 //刷新列表和预算总数
							    corpBudgetId = vo.getId();
							    display.setTotalCount(vo.getBudgetIntegral()+"");
							    display.setRemainCount((vo.getBudgetIntegral()-vo.getUseIntegeral())+"");
							    remainCount = vo.getBudgetIntegral()-vo.getUseIntegeral();
							    
							    List<UserRoleVo> roles = Arrays.asList(sessionManager.getSession().getUserRoles());
								if(roles.contains(UserRoleVo.CORP_ADMIN)){
									initDeparts("1");//HR得到一级部门
									display.setDisplay();
								}else
									initDeparts(display.getManageDep().getValue(display.getManageDep().getSelectedIndex())); //得到所管部门

						 }else{
							 display.setTotalCount("0");
							 display.setRemainCount("0");
							
						 }
							
							
						
					}

				});
	   
   }
   private void initDeparts(String type){
	   dispatch.execute(new InitDepartmentRequest(sessionManager.getSession(),type),
				new AsyncCallback<InitDepartmentResponse>() {
		          	@Override
					public void onFailure(Throwable arg0) {
						errorHandler.alert("查询部门出错!");
						
					}

					@Override
					public void onSuccess(InitDepartmentResponse response) {
						 List<DepartmentVo> list = response.getResult();
						 Map<String, String> map = new HashMap<String, String>();
						 DepartmentVo vo = new DepartmentVo();
						 if(list.size()>0){
							 for(int i=0;i<list.size();i++){
								   vo = list.get(i);
								   map.put(vo.getId(), vo.getDepartmentName());
							 }
						 }
							
							display.initDepart(map);
						
					}

				});
		
   }
	private void buildTable() {
		// create a CellTable
		cellTable = new ListCellTable<AskBudgetClientVo>();

		initTableColumns();
		pager = new EltNewPager(TextLocation.CENTER);
		pager.setDisplay(cellTable);
		cellTable.setWidth(ViewConstants.page_width);
		cellTable.setPageSize(pageSize);

		display.getResultPanel().clear();
		display.getResultPanel().add(cellTable);
		display.getResultpage().clear();
		display.getResultpage().add(pager);
	}

	

	private void initTableColumns() {
		Sorting<AskBudgetClientVo> ref = new Sorting<AskBudgetClientVo>() {
			@Override
			public void sortingCurrentPage(Comparator<AskBudgetClientVo> comparator) {
				// listViewAdapter.sortCurrentPage(comparator);
			}

			@Override
			public void sortingAll(String sorting, String direction) {
				listViewAdapter.sortFromDateBase(sorting, direction);

			}
		};
		cellTable.addColumn("财年", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						return vo.getCorpBudget();
					}
				});
		cellTable.addColumn("部门名称", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						return vo.getDepName();
					}
				}, ref, "departmentId");
		cellTable.addColumn("申请额度", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						return vo.getAskIntegral()+"";
					}
				},ref,"askIntegral");

		cellTable.addColumn("批准额度", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						return vo.getApproveIntegeral()+"";
					}
				}, ref, "approveIntegeral");

		
		
		cellTable.addColumn("状态", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						if(vo.getStatus()==0)
						    return "待审批";
						else if(vo.getStatus()==1)
							return "审批通过";	
						else
							return "审批不通过";	
					}
				}, ref, "status");
		cellTable.addColumn("申请人", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						return vo.getRecordname()+"";
					}
				});
		
			
		cellTable.addColumn("申请日期", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						if(vo.getRecorddate()!=null)
						   return DateTool.dateToString(vo.getRecorddate());
						else
							return "";
					}
				}, ref, "useIntegeral");
		cellTable.addColumn("审批日期", new TextCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						if(vo.getApprovedate()!=null)
							   return DateTool.dateToString(vo.getApprovedate());
							else
								return "";
					}
				});
		cellTable.addColumn("操作", new UniversalCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo vo) {
						if(vo.getStatus()==0&& !vo.getRecorduser().equals(sessionManager.getSession().getStaffId()))
						    return  "<a style=\"color:bule;\" href=\"javascript:void(0);\">审批</a>";
						else
							return "<span style='color: rgb(221, 221, 221);'>审批</span>";
					}
				}, new FieldUpdater<AskBudgetClientVo, String>() {
					@Override
					public void update(int index, final AskBudgetClientVo vo,	String value) {
						if(vo.getStatus()==0)
							win.confirm("操作提示", "确定审批吗？", new ConfirmHandler() {
                            
								@Override
								public void confirm() {
									vo.setRemainCount(remainCount);
									Platform.getInstance()
									.getEditorRegistry()
									.openEditor(
											AskBudgetConstants.EDITOR_VIEW_BUDGET,
											AskBudgetConstants.ACTION_ASKBUDGET_EDIT, vo);
								}
								
								
							});
											
					}
				});
		cellTable.addColumn("操作", new HyperLinkCell(),
				new GetValue<AskBudgetClientVo, String>() {
					@Override
					public String getValue(AskBudgetClientVo order) {
						return "查看详细";
					}
				}, new FieldUpdater<AskBudgetClientVo, String>() {
					@Override
					public void update(int index, final AskBudgetClientVo vo,String value) {
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								AskBudgetConstants.EDITOR_VIEW_BUDGET,
								AskBudgetConstants.ACTION_ASKBUDGET_VIEW, vo);
					}
				});
											
				
			
	      }
      

    }
