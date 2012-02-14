package com.chinarewards.gwt.elt.client.staffAdd.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public interface StaffAddPresenter extends
		Presenter<StaffAddPresenter.StaffAddDisplay> {

	public static interface StaffAddDisplay extends Display {

		public HasClickHandlers getAddBtnClickHandlers();

		public HasClickHandlers getImportBtnClickHandlers();

		void setBreadCrumbs(Widget breadCrumbs);

		String getStaffNo();

		String getStaffName();

		String getDepartmentId();

		String getDepartmentName();

		String getJobPosition();

		String getLeadership();

		String getPhone();

		TextBox getPhoto();

		String getEmail();

		DateBox getDob();

		RadioButton getStatus_JOB();

		RadioButton getStatus_DEPARTURE();

		Image getStaffImage();

		FormPanel getPhotoForm();

		FileUpload getPhotoUpload();

		HasClickHandlers getUploadClick();

	}
}
