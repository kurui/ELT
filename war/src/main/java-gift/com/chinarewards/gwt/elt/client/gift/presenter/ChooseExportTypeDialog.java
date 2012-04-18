package com.chinarewards.gwt.elt.client.gift.presenter;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractDialog;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChooseExportTypeDialog extends AbstractDialog {

	final Provider<ChooseExportTypePresenter> presenterProvider;

	ChooseExportTypePresenter presenter;

	@Inject
	public ChooseExportTypeDialog(
			Provider<ChooseExportTypePresenter> presenterProvider) {
		super("staff.choosee", "staff.choosee");
		this.presenterProvider = presenterProvider;
		presenter = presenterProvider.get();
		presenter.setDialog(this);

		init();
	}

	public void init() {
		setTitle("礼品导出");

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
