package com.chinarewards.gwt.elt.client.enterprise.presenter;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public interface LicensePresenter extends
		Presenter<LicensePresenter.LicenseDisplay> {

	public static interface LicenseDisplay extends Display {

		public HasClickHandlers getSaveClickHandlers();



		public String getEnterpriseId();


		void setBreadCrumbs(Widget breadCrumbs);

		void initEditLicense(EnterpriseVo enterpriseVo);

		public void clear();

		public void setSaveVisible(boolean flag);

		public Label getMoneyTypeLabel();

	}

	void initEditor(String id);

}
