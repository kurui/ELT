package com.chinarewards.gwt.elt.client.enterprise.presenter;

import com.chinarewards.gwt.elt.client.enterprise.model.LicenseVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.user.client.ui.Widget;

public interface LicensePresenter extends
		Presenter<LicensePresenter.LicenseDisplay> {

	public static interface LicenseDisplay extends Display {


		void setBreadCrumbs(Widget breadCrumbs);

		void initEditLicense(LicenseVo licenseVo);

		public void clear();


	}

	void initEditor(String id);

}
