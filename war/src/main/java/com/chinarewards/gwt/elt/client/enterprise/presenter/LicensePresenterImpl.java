package com.chinarewards.gwt.elt.client.enterprise.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.EltGinjector;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.enterprise.model.LicenseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.LicensePresenter.LicenseDisplay;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseRequest;
import com.chinarewards.gwt.elt.client.enterprise.request.SearchLicenseResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.chinarewards.gwt.elt.util.XmlUtil_GWT;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.inject.Inject;

/**
 * 
 * @author yanrui 授权信息
 */
public class LicensePresenterImpl extends BasePresenter<LicenseDisplay>
		implements LicensePresenter {

	final DispatchAsync dispatcher;
	//final Win Window;
	private final SessionManager sessionManager;
	List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();
	private final BreadCrumbsPresenter breadCrumbs;
	
	private final EltGinjector injector = GWT.create(EltGinjector.class);

	@Inject
	public LicensePresenterImpl(final EventBus eventBus,
			LicenseDisplay display, BreadCrumbsPresenter breadCrumbs,
			DispatchAsync dispatcher,SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.sessionManager = sessionManager;
	//	this.Window = Window;
		this.breadCrumbs = breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("查看授权信息");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());

		initWidget();
		
		// 浏览即上传事件
		registerHandler(display.getLicenseUpload().addChangeHandler(
				new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent arg0) {
						String thisFileName=display.getLicenseUpload().getFilename();
//						Window.alert(thisFileName);
						if (!StringUtil.isEmpty(thisFileName)) {
							if (thisFileName.indexOf(".lic")>0) {
								display.getLicenseForm().setAction("licenseupload");
								display.getLicenseForm().submit();
							}else{
								Window.alert("授权文件后缀为.lic,请重新选择");
							}
						}else{
							Window.alert("请选择需要更新的授权文件");
						}
					}
				}));
		
		// 文件上传后回调
		display.getLicenseForm().addSubmitCompleteHandler(
				new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						String eventResults = event.getResults();
						System.out.println("submitComplete event.getResults:"
								+ eventResults);
						// Window.alert(eventResults);

						if (eventResults != null) {
							eventResults = XmlUtil_GWT
									.replaceSpecialStr(eventResults);
							try {
								String result = XmlUtil_GWT.getNormalNodeText(
										eventResults, "<result>", "</result>");
								String info = XmlUtil_GWT.getNormalNodeText(
										eventResults, "<info>", "</info>");

								if ("SUCCESS".equals(result)) {
									initWidget();
									Window.alert("更新授权成功");
								} else {
									Window.alert("上传授权文件异常<br>" + info);
								}
							} catch (Exception e) {
								e.printStackTrace();
								Window.alert("上传授权文件异常，请重试" + e.getMessage());
								return;
							}
						}
					}
				});
		
		registerHandler(display.getBackHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						if (sessionManager!=null) {
							if(sessionManager.getSession()!=null){
								breadCrumbs.getGoHistory();
							}else{
								injector.getMain().init(RootLayoutPanel.get());//登录页
							}							
						}else{
							injector.getMain().init(RootLayoutPanel.get());//登录页
						}
						
					}
				}));

	}

	/**
	 * 加载初始化数据
	 */
	private void initWidget() {

		dispatcher.execute(
				new SearchLicenseRequest(),
				new AsyncCallback<SearchLicenseResponse>() {
					public void onFailure(Throwable caught) {

						Window.alert("初始化失败");
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
