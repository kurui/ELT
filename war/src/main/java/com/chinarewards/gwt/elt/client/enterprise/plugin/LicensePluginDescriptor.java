package com.chinarewards.gwt.elt.client.enterprise.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.enterprise.editor.LicenseEditorDescriptor;
import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

public class LicensePluginDescriptor implements PluginDescriptor {

	final static Set<Extension> extensions = new HashSet<Extension>();

	final static String PLUGIN_ID = EnterpriseConstants.PLUGIN_ENTERPRISE;

	final LicensePlugin plugin;

	final LicenseEditorDescriptor descriptor;

	@Inject
	public LicensePluginDescriptor(
			final LicenseEditorDescriptor editorDesc) {
		this.descriptor = editorDesc;
		plugin = new LicensePlugin(this);
		extensions.add(new Extension() {
			public String getExtensionPointId() {
				return PluginConstants.MENU;
			}

			public Object getInstance() {
				return new MenuItem() {
					public void execute() {
						EnterpriseVo enterpriseVo = new EnterpriseVo();

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										EnterpriseConstants.EDITOR_LICENSE_EDIT,
										"LicenseInstanceID", enterpriseVo);
					}

					public Image getIcon() {
						return null;
					}

					public String getMenuId() {
						return EnterpriseConstants.MENU_LICENSE_EDIT;
					}

					public int getOrder() {
						return MenuConstants.MENU_ORDER_LICENSE_EDIT;
					}

					public String getParentMenuId() {
						return null;
					}

					public String getTitle() {
						return "授权信息";
					}

				};
			}

			public PluginDescriptor getPluginDescriptor() {
				return LicensePluginDescriptor.this;
			}

		});

		extensions.add(new Extension() {

			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			public Object getInstance() {
				return descriptor;
			}

			public PluginDescriptor getPluginDescriptor() {
				return LicensePluginDescriptor.this;
			}

		});

	}

	public Set<ExtensionPoint> getExtensionPoints() {
		return null;
	}

	public Set<Extension> getExtensions() {
		return extensions;
	}

	public Plugin getInstance() {
		return plugin;
	}

	public String getPluginId() {
		return PLUGIN_ID;
	}

}
