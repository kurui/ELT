package com.chinarewards.gwt.elt.client.dialog;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractDialog;
import com.chinarewards.gwt.elt.client.gift.presenter.GiftImportPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author yanrui
 * 
 * @since 1.5.2
 * **/
public class GiftImportDialog extends AbstractDialog {

	final Provider<GiftImportPresenter> presenterProvider;

	GiftImportPresenter presenter;

	@Inject
	public GiftImportDialog(Provider<GiftImportPresenter> presenterProvider) {
		super("Organization.choosee", "Organization.choosee");
		this.presenterProvider = presenterProvider;
		presenter = presenterProvider.get();
		presenter.setDialog(this);

		init();
	}

	public void init() {
		setTitle("导入礼品信息");
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
