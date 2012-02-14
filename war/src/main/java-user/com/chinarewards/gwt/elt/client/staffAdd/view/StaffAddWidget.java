package com.chinarewards.gwt.elt.client.staffAdd.view;


import com.chinarewards.gwt.elt.client.staffAdd.presenter.StaffAddPresenter.StaffAddDisplay;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class StaffAddWidget extends Composite implements StaffAddDisplay {

	@UiField
	TextBox staffNo;
	@UiField
	TextBox staffName;
	@UiField
	TextBox departmentId;
	@UiField
	TextBox departmentName;
	@UiField
	TextBox jobPosition;
	@UiField
	TextBox leadership;
	@UiField
	TextBox phone;
	@UiField
	TextBox email;
	@UiField
	DateBox dob;
	@UiField
	Label dobError;
	@UiField
	RadioButton status_JOB;
	@UiField
	RadioButton status_DEPARTURE;


	@UiField
	Button addBtn;
	@UiField
	Button importBtn;

	@UiField
	Panel breadCrumbs;
	@UiField
	Image staffImage;
	@UiField
	FormPanel photoForm;
	@UiField
	FileUpload photoUpload;
	@UiField
	Button photoUploadBtn;
	@UiField
	TextBox photo;
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static StaffAddWidgetUiBinder uiBinder = GWT
			.create(StaffAddWidgetUiBinder.class);

	interface StaffAddWidgetUiBinder extends
			UiBinder<Widget, StaffAddWidget> {
	}

	public StaffAddWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		dob.setFormat(new DateBox.DefaultFormat(dateFormat));
	}


	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
		
	}


	@Override
	public HasClickHandlers getAddBtnClickHandlers() {
		return addBtn;
	}


	@Override
	public HasClickHandlers getImportBtnClickHandlers() {
		return importBtn;
	}


	@Override
	public String getStaffNo() {
		return staffNo.getValue();
	}


	@Override
	public String getStaffName() {
		return this.staffName.getValue();
	}


	@Override
	public String getDepartmentId() {
		return this.departmentId.getValue();
	}


	@Override
	public String getDepartmentName() {
		return this.departmentName.getValue();
	}


	@Override
	public String getJobPosition() {
		return this.jobPosition.getValue();
	}


	@Override
	public String getLeadership() {
		return this.leadership.getValue();
	}


	@Override
	public String getPhone() {
		return this.phone.getValue();
	}


	@Override
	public String getEmail() {
		return this.email.getValue();
	}


	@Override
	public DateBox getDob() {
		return this.dob;
	}


	@Override
	public RadioButton getStatus_JOB() {
		return this.status_JOB;
	}


	@Override
	public RadioButton getStatus_DEPARTURE() {
		return this.status_DEPARTURE;
	}


	@Override
	public Image getStaffImage() {
		return this.staffImage;
	}


	@Override
	public FormPanel getPhotoForm() {
		return this.photoForm;
	}


	@Override
	public FileUpload getPhotoUpload() {
		return this.photoUpload;
	}


	@Override
	public HasClickHandlers getUploadClick() {
		return this.photoUploadBtn;
	}


	@Override
	public TextBox getPhoto() {
		return photo;
	}




}
