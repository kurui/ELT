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
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
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
	Image giftImage;

	// 保存或修改
	@UiField
	Button save;

	// FrequencyClient frequency;
	// String rewardsUnit;

	// @UiField
	// FlowPanel uploadPanel;
	@UiField
	FormPanel photoForm;
	@UiField
	FileUpload photoUpload;
	@UiField
	Button photoUploadBtn;
	
//	<g:Button ui:field="photoUploadBtn"></g:Button>

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

		initAddWidget();

	}

	private void initAddWidget() {
		type.addItem("实物", "1");
		type.addItem("虚拟", "2");
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
	public String getType() {
		String typeValue = "";
		if (type != null) {
			if (type.getItemCount() > 0) {
				typeValue = type.getValue(0);
			}
		}
		return typeValue;
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

	@Override
	public Image getGiftImage() {
		return giftImage;
	}

	@Override
	public HasValue<String> getIntegral() {
		return integral;
	}

}
