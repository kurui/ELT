/**
 * 
 */
package com.chinarewards.gwt.elt.client.license.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.license.editor.LicenseListEditorDescriptor;
import com.chinarewards.gwt.elt.client.license.plugin.LicenseListConstants;
import com.chinarewards.gwt.elt.client.license.plugin.LicenseListPlugin;
import com.chinarewards.gwt.elt.client.license.plugin.LicenseListPluginDescriptor;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author YANRUI
 */
public class LicenseListPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final LicenseListPlugin LicenseListPlugin;
	final LicenseListEditorDescriptor licenseListEditorDescriptor;

	@Inject
	public LicenseListPluginDescriptor(
			final LicenseListEditorDescriptor licenseListEditorDescriptor) {
		this.licenseListEditorDescriptor = licenseListEditorDescriptor;
		LicenseListPlugin = new LicenseListPlugin(this);

		/**
		 * Search user menu
		 */
		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.MENU;
			}

			@Override
			public Object getInstance() {
				return new MenuItem() {

					@Override
					public int getOrder() {
						return MenuConstants.MENU_ORDER_LICENSELIST_SEARCH;
					}

					@Override
					public String getMenuId() {
						return LicenseListConstants.MENU_LICENSELIST_SEARCH;
					}

					@Override
					public String getParentMenuId() {
						return null;
					}

					@Override
					public String getTitle() {
						return "礼品列表";
					}

					@Override
					public void execute() {

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										LicenseListConstants.EDITOR_LICENSELIST_SEARCH,
										"EDITOR_REWARDSLIST_SEARCH_DO_ID", null);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return LicenseListPluginDescriptor.this;
			}

		});


		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return licenseListEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return LicenseListPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return LicenseListConstants.PLUGIN_LICENSELIST;
	}

	@Override
	public Plugin getInstance() {
		return LicenseListPlugin;
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
