package com.chinarewards.gwt.elt.client.budget.presenter;
import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.request.AskBudgetAddRequest;
import com.chinarewards.gwt.elt.client.budget.request.AskBudgetAddResponse;
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

 public class AskBudgetViewPresenterImpl extends BasePresenter<AskBudgetViewPresenter.AskBudgetViewDisplay>
	implements AskBudgetViewPresenter {
	String instanceId;// 修改时传过来操作型号参数
	private String askBudgetId;
	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final SessionManager sessionManager;
	private final Win win;
	private final BreadCrumbsPresenter breadCrumbs;
	double remainCount;//剩余的总积分
	@Inject
	public AskBudgetViewPresenterImpl(EventBus eventBus, AskBudgetViewDisplay display,
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
	
	 if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_EDIT)) {
		initEdit();
		breadCrumbs.loadChildPage(" 预算审批");
	} else{
		initEdit();
		breadCrumbs.loadChildPage("查看申请");	
	}
	
	display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
	}
	
	private void init() {
	    // 保存事件
	     registerHandler(display.getSaveOKClick().addClickHandler(//通过
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					if (!validateSubmit()) {
						return;
					}
					
					 AskBudgetClientVo askBudgetVo=getAskBudgetVo();//得到界面的数据
					 if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_EDIT)) {
						askBudgetVo.setStatus(1); 
						askBudgetVo.setId(askBudgetId);
						doSave(askBudgetVo);
					} 
		  }	       
						
	  }));
	     registerHandler(display.getSaveNOClick().addClickHandler(//不通过
	 			new ClickHandler() {
	 				@Override
	 				public void onClick(ClickEvent arg0) {
	 					if (!validateSubmit()) {
	 						return;
	 					}
	 					
	 					 AskBudgetClientVo askBudgetVo=getAskBudgetVo();//得到界面的数据
	 					 if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_EDIT)) {
	 						askBudgetVo.setStatus(2);  
	 						askBudgetVo.setId(askBudgetId);
	 						doSave(askBudgetVo);
	 					} 
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
	    AskBudgetVo.setView(display.getView().getValue());
	    if(!display.getSPJF().getValue().equals(""))
	    AskBudgetVo.setApproveIntegeral(Double.parseDouble(display.getSPJF().getValue()));
	   
	 return AskBudgetVo;
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
	// 验证方法
	private boolean validateSubmit() {
	boolean flag = true;
	StringBuilder errorMsg = new StringBuilder();
	
	
	if (display.getSPJF().getValue() == null|| "".equals(display.getSPJF().getValue().trim())) {
		errorMsg.append("请填写审批额度!<br>");
		flag = false;
	}
	if (Double.parseDouble(display.getSPJF().getValue())>remainCount) {
		errorMsg.append("审批额度超过剩余预算!<br>");
		flag = false;
	}
	if (display.getView().getValue() == null|| "".equals(display.getView().getValue().trim())) {
		errorMsg.append("请填审批意见!<br>");
		flag = false;
	}
	if (display.getView().getValue().length()>200) {
		errorMsg.append("审批意见不能大于200个字!<br>");
		flag = false;
	}
	if (!flag) {
		win.alert(errorMsg.toString());
	}
	
	return flag;
	}
	
	
	private void initEdit() {
	dispatcher.execute(new SearchAskBudgetByIdRequest(askBudgetId),
			new AsyncCallback<SearchAskBudgetByIdResponse>() {
				@Override
				public void onFailure(Throwable arg0) {
					errorHandler.alert("查询出错!");
					Platform.getInstance()
					.getEditorRegistry()
					.openEditor(
							AskBudgetConstants.EDITOR_LIST_BUDGET,
							AskBudgetConstants.ACTION_ASKBUDGET_LIST,	instanceId);
		
				}
	
				@Override
				public void onSuccess(SearchAskBudgetByIdResponse response) {
					AskBudgetClientVo askBudgetVo = response.getAskBudgetVoClient();
					if(instanceId.equals(AskBudgetConstants.ACTION_ASKBUDGET_EDIT))
					   display.setUser(sessionManager.getSession().getLoginName());
					display.initEditAskBudget(askBudgetVo ,instanceId);
				}
			});
	 }
	
	
		
		
		
		@Override
		public void initEditor(String instanceId,AskBudgetClientVo vo) {
		this.instanceId = instanceId;
		this.askBudgetId = vo.getId();
		 remainCount = vo.getRemainCount();
		
	    }
	
	 }