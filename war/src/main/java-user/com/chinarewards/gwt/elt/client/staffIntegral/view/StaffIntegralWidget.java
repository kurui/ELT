package com.chinarewards.gwt.elt.client.staffIntegral.view;

import com.chinarewards.gwt.elt.client.staffIntegral.presenter.StaffIntegralPresenter.StaffIntegralDisplay;
import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria.StaffStatus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class StaffIntegralWidget extends Composite implements StaffIntegralDisplay {

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
	Panel breadCrumbs;
	@UiField
	Image staffImage;
	@UiField
	InlineLabel dataCount;
	@UiField
	Panel resultPanel;
	@UiField
	Panel resultpage;
	private static StaffIntegralWidgetUiBinder uiBinder = GWT
			.create(StaffIntegralWidgetUiBinder.class);

	interface StaffIntegralWidgetUiBinder extends UiBinder<Widget, StaffIntegralWidget> {
	}

	public StaffIntegralWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);

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
	public void setDataCount(String text) {
		this.dataCount.setText(text);
		
	}

	@Override
	public Panel getResultPanel() {
		return this.resultPanel;
	}

	@Override
	public Panel getResultpage() {
		return this.resultpage;
	}

	@Override
	public void setStaffStatus(String text) {
		this.staffStatus.setText(StaffStatus.valueOf(text).getDisplayName());
	}

}