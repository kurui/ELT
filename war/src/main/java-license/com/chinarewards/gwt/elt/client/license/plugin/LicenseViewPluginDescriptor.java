/**
 * 
 */
package com.chinarewards.gwt.elt.client.license.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.license.editor.LicenseViewEditorDescriptor;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 2012-1-16
 */
public class LicenseViewPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final LicenseViewPlugin licenseViewPlugin;
	final LicenseViewEditorDescriptor licenseViewEditorDescriptor;

	@Inject
	public LicenseViewPluginDescriptor(
			final LicenseViewEditorDescriptor licenseViewEditorDescriptor) {
		this.licenseViewEditorDescriptor = licenseViewEditorDescriptor;
		licenseViewPlugin = new LicenseViewPlugin(this);

		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return licenseViewEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return LicenseViewPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return LicenseConstants.PLUGIN_LICENSE;
	}

	@Override
	public Plugin getInstance() {
		return licenseViewPlugin;
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
