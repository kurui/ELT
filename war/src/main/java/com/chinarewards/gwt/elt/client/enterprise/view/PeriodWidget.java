package com.chinarewards.gwt.elt.client.enterprise.view;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.enterprise.presenter.PeriodPresenter.PeriodDisplay;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class PeriodWidget extends Composite implements PeriodDisplay {

	private static PeriodWidgetUiBinder uiBinder = GWT
			.create(PeriodWidgetUiBinder.class);

	@UiField
	ListBox period;
	@UiField
	DateBox firstTime;
	@UiField
	Button saveButton;
	@UiField
	Hidden enterpriseId;
	@UiField
	Panel breadCrumbs;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface PeriodWidgetUiBinder extends UiBinder<Widget, PeriodWidget> {
	}

	public PeriodWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		firstTime.setFormat(new DateBox.DefaultFormat(dateFormat));
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public void initEditPeriod(EnterpriseVo enterpriseVo) {	
		initPeriodSelect("");
	}
	
	private void initPeriodSelect(String selectedValue) {
		period.clear();
		period.addItem("1年", "1");
		period.addItem("半年", "0.5");
		period.addItem("两年", "2");
		if (StringUtil.trim(selectedValue) != "") {
			period.setValue(0, selectedValue);
		}
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
	public ListBox getPeriod() {
		return period;
	}

	@Override
	public DateBox getFirstTime() {
		return firstTime;
	}
}
