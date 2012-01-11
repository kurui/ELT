package com.chinarewards.gwt.elt.client.gift.view;

import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftPresenter.GiftDisplay;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class GiftWidget extends Composite implements GiftDisplay {

	// --------vo
	@UiField
	TextBox name;
	@UiField
	TextBox explains;
	@UiField
	TextBox type;
	// @UiField
	// TextBox source;
	@UiField
	TextBox business;
	@UiField
	TextBox address;
	@UiField
	TextBox tell;
	@UiField
	TextBox stock;// int
	// @UiField
	// TextBox phone;
	// @UiField
	// TextBox status;// boolean
	// @UiField
	// TextBox deleted;// boolean
	// @UiField
	// DateBox indate;
	// @UiField
	// DateBox recorddate;
	// @UiField
	// TextBox recorduser;
	// @UiField
	// DateBox updatetime;
	// ---end vo

	// 保存或修改
	@UiField
	Button save;

//	FrequencyClient frequency;
//	String rewardsUnit;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface GiftWidgetBinder extends UiBinder<Widget, GiftWidget> {

	}

	private static GiftWidgetBinder uiBinder = GWT
			.create(GiftWidgetBinder.class);

	@Inject
	public GiftWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasValue<String> getName() {
		return name;
	}

	@Override
	public HasClickHandlers getSaveClick() {
		return save;
	}

	@Override
	public void clear() {
		// return null;
	}

	@Override
	public HasValue<String> getExplains() {
		return explains;
	}

	@Override
	public HasValue<String> getType() {
		return type;
	}

	@Override
	public HasValue<String> getSource() {
//		 return source;
		return null;
	}

	@Override
	public HasValue<String> getBusiness() {
		// return null;
		return business;
	}

	@Override
	public HasValue<String> getAddress() {
		// return null;
		return address;
	}

	@Override
	public HasValue<String> getTell() {
		// return null;
		return tell;
	}

	@Override
	public Integer getStock() {
			if (stock.getText() == null
					|| "".equals(stock.getText().trim())) {
				return null;
			} else {
				try {
					int d = Integer.parseInt(stock.getText().trim());
					return d;
				} catch (Exception e) {
					return new Integer(-1);
				}
			}
	}

	@Override
	public HasValue<String> getPhone() {
		return null;
		// return phone;
	}

	@Override
	public HasValue<Boolean> getStatus() {
		return null;
		// return status;
	}

	@Override
	public HasValue<Boolean> getDeleted() {
		return null;
		// return deleted;
	}

	@Override
	public HasValueChangeHandlers<Date> getIndate() {
		return null;
		// return indate;
	}

	@Override
	public HasValueChangeHandlers<Date> getRecorddate() {
		return null;
		// return recorddate;
	}

	@Override
	public HasValue<String> getRecoduser() {
		return null;
		// return recoduser;
	}

	@Override
	public HasValueChangeHandlers<Date> getUpdatetime() {
		return null;
		// return updatetime;
	}

}
