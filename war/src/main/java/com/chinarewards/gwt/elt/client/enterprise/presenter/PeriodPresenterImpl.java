package com.chinarewards.gwt.elt.client.enterprise.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.PeriodPresenter.PeriodDisplay;
import com.chinarewards.gwt.elt.client.enterprise.request.EnterpriseInitRequest;
import com.chinarewards.gwt.elt.client.enterprise.request.EnterpriseInitResponse;
import com.chinarewards.gwt.elt.client.enterprise.request.EnterpriseRequest;
import com.chinarewards.gwt.elt.client.enterprise.request.EnterpriseResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * 
 * @author yanrui 财年周期设置
 */
public class PeriodPresenterImpl extends BasePresenter<PeriodDisplay> implements
		PeriodPresenter {

	final DispatchAsync dispatchAsync;
	final Win win;
	private final SessionManager sessionManager;
	List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();
	private final BreadCrumbsPresenter breadCrumbs;
	EnterpriseVo enterpriseVo = new EnterpriseVo();

	@Inject
	public PeriodPresenterImpl(final EventBus eventBus, PeriodDisplay display,
			BreadCrumbsPresenter breadCrumbs, DispatchAsync dispatchAsync,
			SessionManager sessionManager, Win win) {
		super(eventBus, display);
		this.dispatchAsync = dispatchAsync;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("财年周期设置");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

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
	 * 
	 * @return
	 */
	public EnterpriseVo getEnterprise() {

		// enterpriseVo.setPeriod(display.getAddress().getValue());
		// enterpriseVo.setId(display.getEnterpriseId().trim());
		return enterpriseVo;
	}

	public void sendService(EnterpriseVo enterprise) {

		EnterpriseRequest req = new EnterpriseRequest(enterprise,
				sessionManager.getSession());
		dispatchAsync.execute(req, new AsyncCallback<EnterpriseResponse>() {
			public void onFailure(Throwable caught) {

				win.alert("操作失败");
			}

			@Override
			public void onSuccess(EnterpriseResponse arg0) {
				win.alert("操作成功");

			}
		});
	}

	/**
	 * 加载初始化数据
	 */
	private void initialization() {

		EnterpriseInitRequest req = new EnterpriseInitRequest(
				sessionManager.getSession());
		
		dispatchAsync.execute(req, new AsyncCallback<EnterpriseInitResponse>() {
			public void onFailure(Throwable caught) {
				Window.alert("初始化失败");
			}

			@Override
			public void onSuccess(EnterpriseInitResponse response) {

				if (response != null) {
					enterpriseVo = response.getEnterprise();
					// display.setAddress(enterpriseVo.getAddress());
					// display.setEnterpriseId(enterpriseVo.getId());

				}
			}

		});

	}
}
