package com.chinarewards.gwt.elt.client.enterprise.view;

import java.util.Map.Entry;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.enterprise.model.MoneyType;
import com.chinarewards.gwt.elt.client.enterprise.presenter.IntegralPricePresenter.IntegralPriceDisplay;
import com.chinarewards.gwt.elt.util.StringUtil;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class IntegralPriceWidget extends Composite implements
		IntegralPriceDisplay {

	private static IntegralPriceWidgetUiBinder uiBinder = GWT
			.create(IntegralPriceWidgetUiBinder.class);
	@UiField
	TextBox integralPrice;
	@UiField
	Label integralPriceLabel;
	

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

	interface IntegralPriceWidgetUiBinder extends
			UiBinder<Widget, IntegralPriceWidget> {
	}

	public IntegralPriceWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public void initEditIntegralPrice(EnterpriseVo enterpriseVo) {
		enterpriseId.setValue(enterpriseVo.getId());

		integralPrice.setText(enterpriseVo.getIntegralPrice() + "");
		integralPriceLabel.setText(enterpriseVo.getIntegralPrice()+"");
		
		initMoneyTypeSelect(enterpriseVo.getMoneyType());
	}

	private void initMoneyTypeSelect(String selectedValue) {
		moneyType.clear();
		int selectIndex = 0;
		int i = 0;
		for (Entry<String, String> entry : MoneyType.map.entrySet()) {
			String keyValue = entry.getKey();			
			// System.out.println(entry.getKey() + ": " + entry.getValue());
			moneyType.addItem(entry.getValue(), entry.getKey());
			if (selectedValue != null && StringUtil.trim(selectedValue) != ""
					&& StringUtil.trim(keyValue) != "") {
				if (selectedValue.equals(keyValue)) {
					selectIndex = i;
					moneyTypeLabel.setText(entry.getValue());
				}
			}
			i++;
		}
		moneyType.setSelectedIndex(selectIndex);
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
	public TextBox getIntegralPrice() {
		return integralPrice;
	}

	@Override
	public ListBox getMoneyType() {
		return moneyType;
	}

	@Override
	public void clear() {

	}


	@Override
	public void setSaveVisible(boolean flag) {
		saveButton.setVisible(flag);
	}

	@Override
	public Label getIntegralPriceLabel() {
		return integralPriceLabel;
	}
	
	@Override
	public Label getMoneyTypeLabel() {
		return moneyTypeLabel;
	}
	
}
