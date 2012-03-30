package com.chinarewards.gwt.elt.client.enterprise.view;

import com.chinarewards.gwt.elt.client.enterprise.model.LicenseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.LicensePresenter.LicenseDisplay;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class LicenseWidget extends Composite implements LicenseDisplay {

	private static LicenseWidgetUiBinder uiBinder = GWT
			.create(LicenseWidgetUiBinder.class);

	@UiField
	Label corporationName;// 企业名称
	@UiField
	Label licenseType;// 授权类型 TRIAL试用 OFFICIAL正式
	@UiField
	Label macaddress;// 网卡地址
	@UiField
	Label localmacaddress;// 本地网卡地址
	@UiField
	Label notafter;// 截止时间
	@UiField
	Label issued;// 授权时间
	@UiField
	Label description;// 备注说明
	@UiField
	Label staffNumber;// 最大授权用户
	

	@UiField
	Panel breadCrumbs;

	interface LicenseWidgetUiBinder extends UiBinder<Widget, LicenseWidget> {
	}

	public LicenseWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public void initEditLicense(LicenseVo licenseVo) {

		corporationName.setText(licenseVo.getCorporationName());
		licenseType.setText(licenseVo.getLicenseTypeName());
		macaddress.setText(licenseVo.getMacaddress());
		localmacaddress.setText(licenseVo.getLocalmacaddress());
		notafter.setText(DateTool.dateToString(licenseVo.getNotafter()));
		issued.setText(DateTool.dateToString(licenseVo.getIssued()));
		description.setText(licenseVo.getDescription());
		staffNumber.setText(licenseVo.getStaffNumber()+"");
	}

	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

	@Override
	public void clear() {

	}

}
