package com.chinarewards.gwt.elt.client.order.view;

import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenter.OrderDisplay;
import com.chinarewards.gwt.elt.client.order.view.OrderWidget;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class OrderWidget extends Composite implements OrderDisplay {

	// --------vo
	@UiField
	TextBox name;
	@UiField
	TextBox explains;
	@UiField
	ListBox type;
	// @UiField
	// TextBox source;
	@UiField
	TextBox business;
	@UiField
	TextBox address;
	@UiField
	TextBox tell;
	@UiField
	TextBox photo;
	@UiField
	TextBox integral;
	@UiField
	TextBox stock;
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

	@UiField
	Button back;

	@UiField
	Button save;

//	@UiField
//	Image orderImage;
	@UiField
	FormPanel photoForm;
	@UiField
	FileUpload photoUpload;
	@UiField
	Button photoUploadBtn;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface OrderWidgetBinder extends UiBinder<Widget, OrderWidget> {

	}

	private static OrderWidgetBinder uiBinder = GWT
			.create(OrderWidgetBinder.class);

	@Inject
	public OrderWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
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
	public void clear() {

	}

	@Override
	public HasValue<String> getExplains() {
		return explains;
	}

	@Override
	public ListBox getType() {
		return type;
	}

	@Override
	public HasValue<String> getSource() {
		// return source;
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
	public HasValue<String> getStock() {
		return stock;
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

	@Override
	public FileUpload getPhotoUpload() {
		return photoUpload;
	}

	@Override
	public FormPanel getPhotoForm() {
		return photoForm;
	}

	@Override
	public HasClickHandlers getSaveClick() {
		return save;
	}

	@Override
	public HasClickHandlers getUploadClick() {
		return photoUploadBtn;
	}

	@Override
	public HasValue<String> getPhoto() {
		return photo;
	}

//	@Override
//	public Image getOrderImage() {
//		return orderImage;
//	}

	@Override
	public HasValue<String> getIntegral() {
		return integral;
	}

	@Override
	public void initEditOrder(OrderVo orderVo) {
		name.setText(orderVo.getName());
//		explains.setText(orderVo.getExplains());
//		initTypeSelect(orderVo.getType());
//		business.setText(orderVo.getBusiness());
//		address.setText(orderVo.getAddress());
//		tell.setText(orderVo.getTell());
//		photo.setText(orderVo.getPhoto());
//		if (orderVo.getPhoto().indexOf(".") > 0) {
//			orderImage.setUrl("/imageshow?imageName=" + orderVo.getPhoto());
//			orderImage.setVisible(true);
//		}
//
//		integral.setText(orderVo.getIntegral() + "");
//		stock.setText(orderVo.getStock() + "");

	}

	@Override
	public HasClickHandlers getBackClick() {
		return back;
	}

	@Override
	public void initAddOrder(OrderVo orderVo) {
		initTypeSelect("");
	}

	private void initTypeSelect(String selectedValue) {
//		type.addItem("实物", OrderVo.TYPE_1);
//		type.addItem("虚拟", OrderVo.TYPE_2);

		if (StringUtil.trim(selectedValue) != "") {
			type.setValue(0, selectedValue);
		}
	}



}
