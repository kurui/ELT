/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.gift.editor.GiftEditorDescriptor;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since
 */
public class GiftPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final GiftPlugin GiftPlugin;
	final GiftEditorDescriptor giftEditorDescriptor;

	@Inject
	public GiftPluginDescriptor(final GiftEditorDescriptor giftEditorDescriptor) {
		this.giftEditorDescriptor = giftEditorDescriptor;
		GiftPlugin = new GiftPlugin(this);

		/**
		 * 新建礼品
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
						return MenuConstants.MENU_ORDER_GIFT_ADD;
					}

					@Override
					public String getMenuId() {
						return GiftConstants.MENU_GIFT_ADD;
					}

					@Override
					public String getParentMenuId() {
						return null;
					}

					@Override
					public String getTitle() {
						return "新建礼品";
					}

					@Override
					public void execute() {

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(GiftConstants.EDITOR_GIFT_ADD,
										"EDITOR_GIFT_ADD_ID", null);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return GiftPluginDescriptor.this;
			}
		});

		/**
		 * 编辑礼品
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
						return MenuConstants.MENU_ORDER_GIFT_EDIT;
					}

					@Override
					public String getMenuId() {
						return GiftConstants.MENU_GIFT_EDIT;
					}

					@Override
					public String getParentMenuId() {
						return null;
					}

					@Override
					public String getTitle() {
						return "编辑礼品";
					}

					@Override
					public void execute() {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(GiftConstants.EDITOR_GIFT_EDIT,
										"EDITOR_GIFTEDIT_SEARCH_DO_ID", null);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return GiftPluginDescriptor.this;
			}

		});

		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return giftEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return GiftPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return GiftConstants.PLUGIN_GIFTEDIT;
	}

	@Override
	public Plugin getInstance() {
		return GiftPlugin;
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
