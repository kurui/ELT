package com.chinarewards.gwt.elt.client.enterprise.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.enterprise.model.LicenseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.LicensePresenter.LicenseDisplay;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseRequest;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseResponse;
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
		breadCrumbs.loadChildPage("查看授权信息");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

		initWidget();

	}

	/**
	 * 加载初始化数据
	 */
	private void initWidget() {

		dispatcher.execute(
				new SearchLicenseRequest(sessionManager.getSession()),
				new AsyncCallback<SearchLicenseResponse>() {
					public void onFailure(Throwable caught) {

						win.alert("初始化失败");
					}

					@Override
					public void onSuccess(SearchLicenseResponse response) {
						if (response != null) {
							LicenseVo licenseVo = response.getLicenseVo();
							clear();
							display.initEditLicense(licenseVo);
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
