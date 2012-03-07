package com.chinarewards.gwt.elt.client.mail.module;


import com.chinarewards.gwt.elt.client.mail.presenter.MailSendPresenter;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendPresenter.MailSendDisplay;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendPresenterImpl;
import com.chinarewards.gwt.elt.client.mail.view.MailSendWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class MailSendModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MailSendPresenter.class).to(MailSendPresenterImpl.class);
		bind(MailSendDisplay.class).to(MailSendWidget.class);
	}

}
