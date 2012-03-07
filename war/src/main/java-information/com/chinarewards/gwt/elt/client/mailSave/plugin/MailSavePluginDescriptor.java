/**
 * 
 */
package com.chinarewards.gwt.elt.client.mailSave.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.mailSave.editor.MailSaveEditorDescriptor;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.inject.Inject;

/**
 * @author nicho
 * @since 2012年2月14日 10:32:10
 */
public class MailSavePluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final MailSavePlugin userPlugin;
	final MailSaveEditorDescriptor mailSaveRegisterEditorDescriptor;

	@Inject
	public MailSavePluginDescriptor(
			final MailSaveEditorDescriptor mailSaveRegisterEditorDescriptor) {
		this.mailSaveRegisterEditorDescriptor = mailSaveRegisterEditorDescriptor;
		userPlugin = new MailSavePlugin(this);


		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return mailSaveRegisterEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return MailSavePluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return MailSaveConstants.PLUGIN_MAILSAVE;
	}

	@Override
	public Plugin getInstance() {
		return userPlugin;
	}

	@Override
	public Set<ExtensionPoint> getExtensionPoints() {
		return null;
	}

	@Override
	public Set<Extension> getExtensions() {
		return ext;
	}

}
