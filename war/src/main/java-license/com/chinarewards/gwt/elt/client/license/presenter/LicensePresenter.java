package com.chinarewards.gwt.elt.client.license.presenter;

import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public interface LicensePresenter extends Presenter<LicensePresenter.LicenseDisplay> {

	public static interface LicenseDisplay extends Display {

		public HasValue<String> getName();

		public HasValue<String> getSummary();

		public HasValue<String> getDispatchcycle();

		public HasValue<String> getExplains();

		public HasValue<String> getNotes();

		public ListBox getType();

		public HasValue<String> getBrand();

		public HasValue<String> getSource();

		public RadioButton getSupplyinner();
		public RadioButton getSupplyoutter();		

		public HasValue<String> getBusiness();

		public HasValue<String> getAddress();

		public HasValue<String> getTell();

		public HasValue<String> getServicetell();

		public HasValue<String> getPhoto();


		public HasValue<String> getStock();

		public HasValue<String> getIntegral();

		public HasValue<String> getPhone();

		public HasValue<Boolean> getStatus();

		public HasValue<Boolean> getDeleted();

		public DateBox getIndate();

		public FormPanel getPhotoForm();

		public HasClickHandlers getSaveClick();

		public void clear();

		public void initAddLicense(LicenseVo licenseVo);

		public void initEditLicense(LicenseVo licenseVo);

		public HasClickHandlers getBackClick();

		void setBreadCrumbs(Widget breadCrumbs);

	}

	public void initEditor(String licenseId, String thisAction);
}
