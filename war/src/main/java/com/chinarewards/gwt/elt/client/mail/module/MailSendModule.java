package com.chinarewards.gwt.elt.client.mail.module;


import com.chinarewards.gwt.elt.client.mail.presenter.MailSendAllPresenter;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendAllPresenter.MailSendAllDisplay;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendAllPresenterImpl;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendPresenter;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendPresenter.MailSendDisplay;
import com.chinarewards.gwt.elt.client.mail.presenter.MailSendPresenterImpl;
import com.chinarewards.gwt.elt.client.mail.view.MailSendAllWidget;
import com.chinarewards.gwt.elt.client.mail.view.MailSendWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class MailSendModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MailSendPresenter.class).to(MailSendPresenterImpl.class);
		bind(MailSendDisplay.class).to(MailSendWidget.class);
		
		bind(MailSendAllPresenter.class).to(MailSendAllPresenterImpl.class);
		bind(MailSendAllDisplay.class).to(MailSendAllWidget.class);
	}

}
