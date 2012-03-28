package com.chinarewards.gwt.elt.client.enterprise.view;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.LicensePresenter.LicenseDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class LicenseWidget extends Composite implements
		LicenseDisplay {

	private static LicenseWidgetUiBinder uiBinder = GWT
			.create(LicenseWidgetUiBinder.class);

	

	@UiField
	Button saveButton;
	@UiField
	Hidden enterpriseId;

	@UiField
	ListBox moneyType;
	@UiField
	Label moneyTypeLabel;

	@UiField
	Panel breadCrumbs;

	interface LicenseWidgetUiBinder extends
			UiBinder<Widget, LicenseWidget> {
	}

	public LicenseWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public void initEditLicense(EnterpriseVo enterpriseVo) {
		enterpriseId.setValue(enterpriseVo.getId());

//		licenseLabel.setText(enterpriseVo.getLicense()+"");
		
		
	}


	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

	public HasClickHandlers getSaveClickHandlers() {
		return saveButton;
	}

	@Override
	public String getEnterpriseId() {
		return this.enterpriseId.getValue();
	}


	@Override
	public void clear() {

	}


	@Override
	public void setSaveVisible(boolean flag) {
		saveButton.setVisible(flag);
	}

	
	@Override
	public Label getMoneyTypeLabel() {
		return moneyTypeLabel;
	}
}
