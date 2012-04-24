package com.chinarewards.gwt.elt.client.budget.presenter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.request.AskBudgetAddRequest;
import com.chinarewards.gwt.elt.client.budget.request.AskBudgetAddResponse;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetResponse;
import com.chinarewards.gwt.elt.client.budget.request.InitManageDepRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitManageDepResponse;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetByIdRequest;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetByIdResponse;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

 public class AskBudgetAddPresenterImpl extends BasePresenter<AskBudgetAddPresenter.AskBudgetAddDisplay>
	implements AskBudgetAddPresenter {
	String instanceId;// 修改时传过来操作型号参数
	private String askBudgetId;
	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final SessionManager sessionManager;
	private final Win win;
	private final BreadCrumbsPresenter breadCrumbs;
	
	@Inject
	public AskBudgetAddPresenterImpl(EventBus eventBus, AskBudgetAddDisplay display,
		DispatchAsync dispatcher, ErrorHandler errorHandler,
		SessionManager sessionManager, Win win,
		BreadCrumbsPresenter breadCrumbs) {
	super(eventBus, display);
	this.dispatcher = dispatcher;
	this.errorHandler = errorHandler;
	this.sessionManager = sessionManager;
	this.win = win;
	this.breadCrumbs = breadCrumbs;
	
	}
	
	@Override
	public void bind() {
	// 绑定事件
	init();
	initYear();
	initDeparts();
	if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_ADD))
	{
		breadCrumbs.loadChildPage("新建预算申请");
		
	} else if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_EDIT)) {
		initEdit();
		breadCrumbs.loadChildPage("修改预算申请");
	} else{
		initEdit();
		breadCrumbs.loadChildPage("查看预算申请");	
	}
	
	display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
	}
	
	private void init() {
	// 保存事件
	display.setName(sessionManager.getSession().getLoginName());
	registerHandler(display.getSaveClick().addClickHandler(
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					if (!validateSubmit()) {
						return;
					}
					
					 AskBudgetClientVo askBudgetVo=getAskBudgetVo();//得到界面的数据
					if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_ADD)) {
						askBudgetVo.setId(null);
						doSave(askBudgetVo);
					} if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_EDIT)) {
						askBudgetVo.setId(askBudgetId);
						doSave(askBudgetVo);
					} 
				}
	           
		private void doSave(AskBudgetClientVo AskBudgetVo) {
				dispatcher.execute(new AskBudgetAddRequest(AskBudgetVo,sessionManager.getSession()),
						new AsyncCallback<AskBudgetAddResponse>() {
							@Override
							public void onFailure(Throwable t) {
								errorHandler.alert(t.toString());
							}
	
							@Override
							public void onSuccess(	AskBudgetAddResponse response) {
								if(response.getMessage().equals("success"))
								    win.alert("操作成功");
								else
									 win.alert("操作失败");
								// if(instanceId!=null||!instanceId.equals(""))
								Platform.getInstance()
										.getEditorRegistry()
										.openEditor(
										AskBudgetConstants.EDITOR_LIST_BUDGET,
										AskBudgetConstants.ACTION_ASKBUDGET_LIST,	instanceId);
						}
					});
			}
	
				
	}));
	
		
	registerHandler(display.getBackClick().addClickHandler(
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					Platform.getInstance()
							.getEditorRegistry()
							.openEditor(
									AskBudgetConstants.EDITOR_LIST_BUDGET,
									AskBudgetConstants.ACTION_ASKBUDGET_LIST,	instanceId);
				}
			}));		
	
	}
	private AskBudgetClientVo getAskBudgetVo(){
		AskBudgetClientVo AskBudgetVo =new AskBudgetClientVo();
	    AskBudgetVo.setReason(display.getReason().getValue());
	    if(!display.getJF().getValue().equals(""))
	    AskBudgetVo.setAskIntegral(Double.parseDouble(display.getJF().getValue()));
	    AskBudgetVo.setDepartmentId(display.getDepart());
	    AskBudgetVo.setCorpBudgetId(display.getYear());
	 return AskBudgetVo;
	}
	
	// 验证方法
	private boolean validateSubmit() {
	boolean flag = true;
	StringBuilder errorMsg = new StringBuilder();
	
	if (display.getDepart() == null|| "".equals(display.getDepart().trim())) {
		errorMsg.append("请选择预算部门!<br>");
		flag = false;
	}
	
	if (display.getJF().getValue() == null|| "".equals(display.getJF().getValue().trim())) {
		errorMsg.append("请填写申请预算额度!<br>");
		flag = false;
	}
	if (display.getReason().getValue() == null|| "".equals(display.getReason().getValue().trim())) {
		errorMsg.append("请填申请原因!<br>");
		flag = false;
	}
	if (display.getReason().getValue().length()>200) {
		errorMsg.append("申请原因不能大于200个字!<br>");
		flag = false;
	}
	if (!flag) {
		win.alert(errorMsg.toString());
	}
	
	return flag;
	}
	 private void initYear(){
		 dispatcher.execute(new InitCorpBudgetRequest(sessionManager.getSession()),
					new AsyncCallback<InitCorpBudgetResponse>() {
			          	@Override
						public void onFailure(Throwable arg0) {
							errorHandler.alert("查询财年周期出错!");
							
						}

						@Override
						public void onSuccess(InitCorpBudgetResponse response) {
							 List<CorpBudgetVo> list = response.getResult();
							 Map<String, String> map = new HashMap<String, String>();
							 map.clear();
							 CorpBudgetVo vo = new CorpBudgetVo();
							 if(list.size()>0){
								 for(int i=0;i<list.size();i++){
									   vo = list.get(i);
									   map.put(vo.getId(), vo.getBudgetTitle());
								 }
																
							 }else{
								
								 map.put(vo.getId(), "");
								
							 }
							 display.initYear(map);	
							
						}

					});
		 
	   }
	 private void initDeparts(){
		 dispatcher.execute(new InitManageDepRequest(sessionManager.getSession()),
					new AsyncCallback<InitManageDepResponse>() {
			          	@Override
						public void onFailure(Throwable arg0) {
							errorHandler.alert("查询部门出错!");
							
						}

						@Override
						public void onSuccess(InitManageDepResponse response) {
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
	private void initEdit() {
	dispatcher.execute(new SearchAskBudgetByIdRequest(askBudgetId),
			new AsyncCallback<SearchAskBudgetByIdResponse>() {
				@Override
				public void onFailure(Throwable arg0) {
					errorHandler.alert("查询出错!");
					Platform.getInstance()
							.getEditorRegistry()
							.closeEditor(AskBudgetConstants.EDITOR_ADD_BUDGET,instanceId);
				}
	
				@Override
				public void onSuccess(SearchAskBudgetByIdResponse response) {
					AskBudgetClientVo askBudgetVo = response.getAskBudgetVoClient();
					clear();
					display.initEditAskBudget(askBudgetVo ,instanceId);
				}
			});
	 }
	
	
	
		private void clear() {
		display.clear();
		}
		
		
		@Override
		public void initEditor(String instanceId,AskBudgetClientVo vo) {
		this.instanceId = instanceId;
		this.askBudgetId = vo.getId();
	
	    }
	
	 }