package com.chinarewards.gwt.elt.client.staffInfo.view;

import com.chinarewards.gwt.elt.client.staffInfo.presenter.StaffInfoPresenter.StaffInfoDisplay;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria.StaffStatus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class StaffInfoWidget extends Composite implements StaffInfoDisplay {

	@UiField
	InlineLabel staffNo;
	@UiField
	InlineLabel staffName;

	@UiField
	InlineLabel departmentName;
	@UiField
	InlineLabel jobPosition;
	@UiField
	InlineLabel leadership;
	@UiField
	InlineLabel phone;
	@UiField
	InlineLabel email;
	@UiField
	InlineLabel dob;
	@UiField
	InlineLabel staffStatus;
	
	@UiField
	Button updateBtn;


	@UiField
	Image staffImage;
	
	
	private static StaffInfoWidgetUiBinder uiBinder = GWT
			.create(StaffInfoWidgetUiBinder.class);

	interface StaffInfoWidgetUiBinder extends UiBinder<Widget, StaffInfoWidget> {
	}

	public StaffInfoWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	@Override
	public HasClickHandlers getupadateBtnClickHandlers() {
		return updateBtn;
	}

	@Override
	public void setStaffNo(String text) {
		this.staffNo.setText(text);
	}

	@Override
	public void setStaffName(String text) {
		this.staffName.setText(text);
	}

	@Override
	public void setDepartmentName(String text) {
		this.departmentName.setText(text);
	}

	@Override
	public void setJobPosition(String text) {
		this.jobPosition.setText(text);
	}

	@Override
	public void setLeadership(String text) {
		this.leadership.setText(text);
	}

	@Override
	public void setPhone(String text) {
		this.phone.setText(text);
	}

	@Override
	public void setEmail(String text) {
		this.email.setText(text);
	}

	@Override
	public void setDob(String text) {
		this.dob.setText(text);
	}

	@Override
	public void setStaffImage(String url) {
		this.staffImage.setUrl("imageshow?imageName=" + url);
	}

	
	@Override
	public void setStaffStatus(String text) {
		this.staffStatus.setText(StaffStatus.valueOf(text).getDisplayName());
	}

}
