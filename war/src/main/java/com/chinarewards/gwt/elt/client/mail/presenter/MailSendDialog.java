package com.chinarewards.gwt.elt.client.mail.presenter;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractDialog;
import com.chinarewards.gwt.elt.client.mailSave.presenter.MailSavePresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MailSendDialog extends AbstractDialog {

	final Provider<MailSendPresenter> presenterProvider;

	MailSendPresenter presenter;

	@Inject
	public MailSendDialog(
			Provider<MailSendPresenter> presenterProvider) {
		super("MailSave.choosee", "MailSave.choosee");
		this.presenterProvider = presenterProvider;
		presenter = presenterProvider.get();
		presenter.setDialog(this);

		init();
	}
	public void initStaff(String staffId,String staffName) {
		presenter.initBroadcastStaff(staffId, staffName);
		setTitle("发送邮件");
	}
	public void init() {
		setTitle("发送邮件");
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
