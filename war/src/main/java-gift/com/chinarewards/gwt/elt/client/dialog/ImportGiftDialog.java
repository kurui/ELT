package com.chinarewards.gwt.elt.client.dialog;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractDialog;
import com.chinarewards.gwt.elt.client.gift.presenter.ImportGiftPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author yanrui
 * 
 * @since 1.5.2
 * **/
public class ImportGiftDialog extends AbstractDialog {

	final Provider<ImportGiftPresenter> presenterProvider;

	ImportGiftPresenter presenter;

	@Inject
	public ImportGiftDialog(Provider<ImportGiftPresenter> presenterProvider) {
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
