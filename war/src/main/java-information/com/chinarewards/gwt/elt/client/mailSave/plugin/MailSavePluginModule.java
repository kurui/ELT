/**
 * 
 */
package com.chinarewards.gwt.elt.client.mailSave.plugin;

import com.chinarewards.gwt.elt.client.mailSave.editor.MailSaveEditor;
import com.chinarewards.gwt.elt.client.mailSave.editor.MailSaveEditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * @author NICHO
 * @since 2012年2月14日 10:32:00
 */
public class MailSavePluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MailSavePluginDescriptor.class).in(Singleton.class);

		bind(MailSaveEditorDescriptor.class).in(Singleton.class);
		bind(MailSaveEditor.class);
	}

}
