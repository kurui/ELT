/**
 * 
 */
package com.chinarewards.gwt.elt.client.mailSave.editor;

import com.chinarewards.gwt.elt.client.core.ui.Editor;
import com.chinarewards.gwt.elt.client.core.ui.EditorDescriptor;
import com.chinarewards.gwt.elt.client.mailSave.plugin.MailSaveConstants;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author nicho
 * @since 2012年2月20日 11:01:54
 */
public class MailSaveEditorDescriptor implements EditorDescriptor {

	final Provider<MailSaveEditor> editProvider;

	@Inject
	MailSaveEditorDescriptor(Provider<MailSaveEditor> editProvider) {
		this.editProvider = editProvider;
	}

	@Override
	public String getEditorId() {
		return MailSaveConstants.EDITOR_MAILSAVE_SEARCH;
	}

	@Override
	public Editor createEditor(String instanceId, Object model) {
		MailSaveEditor e = editProvider.get();
		e.setInstanceId(instanceId);
		e.setTitle("添加信息");
		e.setModel(model);
		return e;
	}

}
