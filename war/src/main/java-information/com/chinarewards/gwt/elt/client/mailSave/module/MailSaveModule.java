package com.chinarewards.gwt.elt.client.mailSave.module;


import com.chinarewards.gwt.elt.client.mailSave.presenter.MailSavePresenter;
import com.chinarewards.gwt.elt.client.mailSave.presenter.MailSavePresenter.MailSaveDisplay;
import com.chinarewards.gwt.elt.client.mailSave.presenter.MailSavePresenterImpl;
import com.chinarewards.gwt.elt.client.mailSave.view.MailSaveWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class MailSaveModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MailSavePresenter.class).to(MailSavePresenterImpl.class);
		bind(MailSaveDisplay.class).to(MailSaveWidget.class);
	}

}
