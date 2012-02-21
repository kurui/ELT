package com.chinarewards.gwt.elt.client.register.presenter;


import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.EltGinjector;
import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.register.presenter.RegisterPresenter.RegisterDisplay;
import com.chinarewards.gwt.elt.client.register.request.RegisterInitRequest;
import com.chinarewards.gwt.elt.client.register.request.RegisterInitResponse;
import com.chinarewards.gwt.elt.client.register.request.RegisterRequest;
import com.chinarewards.gwt.elt.client.register.request.RegisterResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
/**
 * 
 * @author lw
 * 企业注册
 */
public class RegisterPresenterImpl extends BasePresenter<RegisterDisplay> implements
		RegisterPresenter {

	private final EltGinjector injector = GWT.create(EltGinjector.class);
	final DispatchAsync dispatchAsync;
	final Win  win;
	private final SessionManager sessionManager;
	EnterpriseVo enterpriseVo = new EnterpriseVo();
	
	@Inject
	public RegisterPresenterImpl(final EventBus eventBus, RegisterDisplay display,
			DispatchAsync dispatchAsync,SessionManager sessionManager,Win  win) {
		super(eventBus, display);
		this.dispatchAsync = dispatchAsync;
		this.sessionManager = sessionManager;
		this.win =win;
	}

	@Override
	public void bind() {
		initialization();
		registerHandler(display.getSaveClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
							doSaveEnterprise();
					}
				}));
		
		
	}

	protected void doSaveEnterprise() {
		EnterpriseVo enterprise = getEnterprise();
	    sendService(enterprise);
	}
	/**
	 * 得到客户端传来的信息放在VO
	 * @return
	 */
    public EnterpriseVo getEnterprise(){
    	Window.alert("d"+display.getAddress().getValue());
    	enterpriseVo.setAddress(display.getAddress().getValue());
    	enterpriseVo.setCellphone(display.getCellphone().getValue());
    	enterpriseVo.setCorporation(display.getCorporation().getValue());
    	enterpriseVo.setEmailAddress(display.getEmail().getValue());
    	enterpriseVo.setName(display.getEnterpriseName().getValue());
    	enterpriseVo.setFax(display.getFax().getValue());
    	enterpriseVo.setLinkman(display.getLinkman().getValue());
    	enterpriseVo.setDescription(display.getRemark().getValue());
    	enterpriseVo.setTell(display.getTell().getValue());
    	enterpriseVo.setWeb(display.getWeb().getValue());
    	enterpriseVo.setId(display.getEnterpriseId().trim());
    	return enterpriseVo;
    }
    
    public void sendService(EnterpriseVo enterprise) {

		if (null == enterprise.getName() || enterprise.getName() .trim().equals("")) {
			Window.alert("企业名称不能为空!");
			return;
		}
		
		RegisterRequest req = new RegisterRequest(enterprise);
		dispatchAsync.execute(req, new AsyncCallback<RegisterResponse>() {
					public void onFailure(Throwable caught) {
						
						Window.alert("创建失败");
					}
					@Override
					public void onSuccess(RegisterResponse arg0) {
						Window.alert("创建成功");
						
					}
				});
	}
    
    /**
	 * 加载初始化数据
	 */
	private void initialization() {		
		dispatchAsync.execute(new RegisterInitRequest(), new AsyncCallback<RegisterInitResponse>() {
			public void onFailure(Throwable caught) {

				Window.alert("初始化失败");
			}

			@Override
			public void onSuccess(RegisterInitResponse response) {
				
					int sum= response.getCount();
					if(sum>0){
						injector.getMain().init(RootLayoutPanel.get());
					}
						
				
			}

		});
	}
}
