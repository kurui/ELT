package com.chinarewards.gwt.elt.client.license.view;

import java.util.Map.Entry;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.license.model.LicenseType;
import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.chinarewards.gwt.elt.client.license.presenter.LicensePresenter.LicenseDisplay;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;

public class LicenseWidget extends Composite implements LicenseDisplay {

	// --------vo
	@UiField
	TextBox name;
	@UiField
	TextArea summary;
	@UiField
	TextArea dispatchcycle;
	@UiField
	TextArea explains;
	@UiField
	TextArea notes;
	@UiField
	ListBox type;
	@UiField
	TextBox brand;
	@UiField
	TextBox photo;
	@UiField
	TextBox integral;
	@UiField
	TextBox stock;
	@UiField
	RadioButton supplyinner;
	@UiField
	RadioButton supplyoutter;
	@UiField
	TextBox business;
	@UiField
	TextBox address;
	@UiField
	TextBox tell;
	@UiField
	TextBox servicetell;
	@UiField
	DateBox indate;

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

	// @UiField
	// Label nameError;
	@UiField
	Label integralError;
	@UiField
	Label stockError;
	@UiField
	Label indateError;

	@UiField
	Button back;

	@UiField
	Button save;

	@UiField
	FormPanel photoForm;
	@UiField
	FileUpload photoUpload;
	@UiField
	Button photoUploadBtn;

	@UiField
	Panel breadCrumbs;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface LicenseWidgetBinder extends UiBinder<Widget, LicenseWidget> {

	}

	private static LicenseWidgetBinder uiBinder = GWT
			.create(LicenseWidgetBinder.class);

	@Inject
	public LicenseWidget(DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		initWidget(uiBinder.createAndBindUi(this));
		indate.setFormat(new DateBox.DefaultFormat(dateFormat));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void initEditLicense(LicenseVo licenseVo) {
		name.setText(licenseVo.getName());
		summary.setText(licenseVo.getSummary());
		dispatchcycle.setText(licenseVo.getDispatchcycle());
		explains.setText(licenseVo.getExplains());
		notes.setText(licenseVo.getNotes());
		initTypeSelect(licenseVo.getType());
		brand.setText(licenseVo.getBrand());
		photo.setText(licenseVo.getPhoto());
	
		integral.setText(licenseVo.getIntegral() + "");
		stock.setText(licenseVo.getStock() + "");

		business.setText(licenseVo.getBusiness());
		address.setText(licenseVo.getAddress());
		tell.setText(licenseVo.getTell());
		servicetell.setText(licenseVo.getServicetell());

		if (licenseVo.getSource() != null) {
			if (StringUtil.trim(licenseVo.getSource()).equals("inner")) {
				supplyinner.setValue(true);
				business.setEnabled(false);
				address.setEnabled(false);
				tell.setEnabled(false);
			}
			if (StringUtil.trim(licenseVo.getSource()).equals("outter")) {
				supplyoutter.setValue(true);
			}
		}

		indate.setValue(licenseVo.getIndate());

	}

	@Override
	public void initAddLicense(LicenseVo licenseVo) {
		initTypeSelect("");
		supplyinner.setValue(false);
		supplyoutter.setValue(true);

		registerSource();
	}

	private void initTypeSelect(String selectedValue) {
		type.clear();
		int selectIndex = 0;
		int i = 0;
		for (Entry<String, String> entry : LicenseType.map.entrySet()) {
			String keyValue = entry.getKey();
			// System.out.println(entry.getKey() + ": " + entry.getValue());
			type.addItem(entry.getValue(), entry.getKey());
			if (selectedValue != null && StringUtil.trim(selectedValue) != ""
					&& StringUtil.trim(keyValue) != "") {
				if (selectedValue.equals(keyValue)) {
					selectIndex = i;
				}
			}
			i++;
		}
		type.setSelectedIndex(selectIndex);
	}

	private void registerSource() {
		supplyinner.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					business.setEnabled(false);
					address.setEnabled(false);
					tell.setEnabled(false);

					business.setValue("");
					address.setValue("");
					tell.setValue("");
				}
			}
		});

		supplyoutter.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					business.setEnabled(true);
					address.setEnabled(true);
					tell.setEnabled(true);
				}
			}
		});

		// name.addValueChangeHandler(new ValueChangeHandler<String>() {
		// @Override
		// public void onValueChange(ValueChangeEvent<String> arg0) {
		// if (name.getValue() == null
		// || "".equals(name.getValue().trim())) {
		// nameError.setText("请填写礼品名称!<br>");
		// // win.alert("222");
		// }
		//
		// }
		// });
	}

	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
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
	public DateBox getIndate() {
		return indate;
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
	public HasValue<String> getPhoto() {
		return photo;
	}


	@Override
	public HasValue<String> getIntegral() {
		return integral;
	}

	@Override
	public HasClickHandlers getBackClick() {
		return back;
	}

	@Override
	public HasValue<String> getSummary() {
		return summary;
	}

	@Override
	public HasValue<String> getDispatchcycle() {
		return dispatchcycle;
	}

	@Override
	public HasValue<String> getNotes() {
		return notes;
	}

	@Override
	public HasValue<String> getBrand() {
		return brand;
	}

	@Override
	public HasValue<String> getServicetell() {
		return servicetell;
	}

	@Override
	public RadioButton getSupplyinner() {
		return supplyinner;
	}

	@Override
	public RadioButton getSupplyoutter() {
		return supplyoutter;
	}


}
