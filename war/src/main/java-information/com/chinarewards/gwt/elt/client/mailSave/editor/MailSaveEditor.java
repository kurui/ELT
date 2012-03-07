package com.chinarewards.gwt.elt.client.mailSave.editor;

import com.chinarewards.gwt.elt.client.core.ui.impl.AbstractEditor;
import com.chinarewards.gwt.elt.client.mailSave.presenter.MailSavePresenter;
import com.chinarewards.gwt.elt.client.mailSave.editor.MailSaveEditorDescriptor;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author nicho
 * @since 2012年2月20日 11:01:49
 */
public class MailSaveEditor extends AbstractEditor {

	final MailSavePresenter mailSavePresenter;
	Object model;

	@Inject
	protected MailSaveEditor(MailSaveEditorDescriptor editorDescriptor,
			MailSavePresenter mailSavePresenter) {
		super(editorDescriptor);
		this.mailSavePresenter = mailSavePresenter;
	}

	@Override
	public Widget asWidget() {
		return mailSavePresenter.getDisplay().asWidget();
	}

	@Override
	public boolean beforeClose() {
		mailSavePresenter.unbind();
		return true;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void save() {

	}

	public void setModel(Object model) {
		this.model = model;
//		if(model!=null)
//			mailSavePresenter.initBroadcastUpdate((String)model);
		mailSavePresenter.bind();
	}
}
