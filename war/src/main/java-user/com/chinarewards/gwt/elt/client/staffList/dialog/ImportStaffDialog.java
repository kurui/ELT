package com.chinarewards.gwt.elt.client.staffList.dialog;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractDialog;
import com.chinarewards.gwt.elt.client.staffList.presenter.ImportStaffPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ImportStaffDialog extends AbstractDialog {

	final Provider<ImportStaffPresenter> presenterProvider;

	ImportStaffPresenter presenter;

	@Inject
	public ImportStaffDialog(
			Provider<ImportStaffPresenter> presenterProvider) {
		super("Organization.choosee", "Organization.choosee");
		this.presenterProvider = presenterProvider;
		presenter = presenterProvider.get();
		presenter.setDialog(this);

		init();
	}

	public void init() {
		setTitle("导入员工");
		presenter.bind();
	}

	@Override
	public Widget asWidget() {
		return presenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		presenter.unbind();
		return true;
	}

}
