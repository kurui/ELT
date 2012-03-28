package com.chinarewards.gwt.elt.client.enterprise.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.LicensePresenter.LicenseDisplay;
import com.chinarewards.gwt.elt.client.enterprise.request.EnterpriseInitRequest;
import com.chinarewards.gwt.elt.client.enterprise.request.EnterpriseInitResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * 
 * @author yanrui 授权信息
 */
public class LicensePresenterImpl extends BasePresenter<LicenseDisplay>
		implements LicensePresenter {

	final DispatchAsync dispatcher;
	final Win win;
	private final SessionManager sessionManager;
	List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();
	private final BreadCrumbsPresenter breadCrumbs;

	@Inject
	public LicensePresenterImpl(final EventBus eventBus,
			LicenseDisplay display, BreadCrumbsPresenter breadCrumbs,
			DispatchAsync dispatcher, SessionManager sessionManager, Win win) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("授权信息");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

		initialization();

	}

	/**
	 * 得到客户端传来的信息放在VO
	 * 
	 * @return
	 */
	public EnterpriseVo getEnterprise() {
		EnterpriseVo enterpriseVo = new EnterpriseVo();
		enterpriseVo.setId(display.getEnterpriseId().trim());
		// enterpriseVo.setLicense(Double.valueOf(display.getLicense()
		// .getValue()));

		// int selectedIndex = display.getMoneyType().getSelectedIndex();
		// enterpriseVo.setMoneyType(display.getMoneyType().getValue(selectedIndex));
		// enterpriseVo.setMoneyType(display.getMoneyType().getItemText(selectedIndex));

		return enterpriseVo;

	}

	/**
	 * 加载初始化数据
	 */
	private void initialization() {
		String corporationId = sessionManager.getSession().getCorporationId();

		dispatcher.execute(
				new EnterpriseInitRequest(sessionManager.getSession()),
				new AsyncCallback<EnterpriseInitResponse>() {
					public void onFailure(Throwable caught) {

						win.alert("初始化失败");
					}

					@Override
					public void onSuccess(EnterpriseInitResponse response) {
						if (response != null) {
							EnterpriseVo enterpriseVo = response
									.getEnterprise();
							clear();
							display.initEditLicense(enterpriseVo);
						}
					}
				});

	}

	private void clear() {
		display.clear();
	}

	@Override
	public void initEditor(String id) {

	}
}
