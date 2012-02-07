package com.chinarewards.gwt.elt.client.enterprise.view;

import com.chinarewards.gwt.elt.client.enterprise.presenter.IntegralPricePresenter.IntegralPriceDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hidden;
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
	Button saveButton;
	@UiField
	Hidden enterpriseId;
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
	public HasValue<String> getIntegralPrice() {
		return integralPrice;
	}
}
