package com.chinarewards.gwt.elt.client.order.view;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderViewPresenter.OrderViewDisplay;
import com.chinarewards.gwt.elt.client.order.view.OrderViewWidget;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class OrderViewWidget extends Composite implements OrderViewDisplay {

	// --------vo
	@UiField
	Label name;
	@UiField
	Label explains;
	@UiField
	Label typeText;
	// @UiField
	// Label source;
	@UiField
	Label business;
	@UiField
	Label address;
	@UiField
	Label tell;
	@UiField
	Label integral;
	@UiField
	Label stock;
	// @UiField
	Label phone;
	// @UiField
	// Label status;// boolean
	// @UiField
	// Label deleted;// boolean
	// @UiField
	// DateBox indate;
	// @UiField
	// DateBox recorddate;
	// @UiField
	// Label recorduser;
	// @UiField
	// DateBox updatetime;
	// ---end vo

	@UiField
	Image giftImage;

	@UiField
	Button back;

	@UiField
	Button update;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface OrderWidgetBinder extends UiBinder<Widget, OrderViewWidget> {

	}

	private static OrderWidgetBinder uiBinder = GWT
			.create(OrderWidgetBinder.class);

	@Inject
	public OrderViewWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getBackClick() {
		return back;
	}

	@Override
	public HasClickHandlers getUpdateClick() {
		return update;
	}

	@Override
	public void showOrder(OrderVo orderVo) {
//		System.out.println("showOrder ----------" + orderVo.getName() + "--"
//				+ orderVo.getPhoto());
		name.setText(orderVo.getName());
//		explains.setText(orderVo.getExplains());
//		typeText.setText(orderVo.getTypeText());
//		business.setText(orderVo.getBusiness());
//		address.setText(orderVo.getAddress());
//		tell.setText(orderVo.getTell());
//		// photo.setText(orderVo.getPhoto());
//		orderImage.setUrl("/imageshow?imageName=" + orderVo.getPhoto());
//		orderImage.setVisible(true);
//
//		integral.setText(orderVo.getIntegral() + "");
//		stock.setText(orderVo.getStock() + "");
//		// phone.setText(orderVo.getPhone());


	}
}
