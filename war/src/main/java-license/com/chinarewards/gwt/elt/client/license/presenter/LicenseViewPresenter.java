package com.chinarewards.gwt.elt.client.license.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.license.presenter.LicenseViewPresenter;
import com.chinarewards.gwt.elt.client.license.model.LicenseClient;
import com.chinarewards.gwt.elt.client.license.model.LicenseVo;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

public interface LicenseViewPresenter extends
		Presenter<LicenseViewPresenter.LicenseViewDisplay> {
	void initInstanceId(String instanceId, LicenseClient item);

	public static interface LicenseViewDisplay extends Display {


		public HasClickHandlers getBackClick();

		public HasClickHandlers getUpdateClick();

		public void showLicense(LicenseVo licenseVo);

		void setBreadCrumbs(Widget breadCrumbs);

	}
}
