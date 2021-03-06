/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.order.editor.OrderViewEditorDescriptor;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author lw
 * @since 2012年2月1日 13:37:23
 */
public class OrderViewPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final OrderViewPlugin OrderViewPlugin;
	final OrderViewEditorDescriptor orderViewEditorDescriptor;

	@Inject
	public OrderViewPluginDescriptor(
			final OrderViewEditorDescriptor orderViewEditorDescriptor) {
		this.orderViewEditorDescriptor = orderViewEditorDescriptor;
		OrderViewPlugin = new OrderViewPlugin(this);

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
						return MenuConstants.MENU_ORDER_ORDERVIEW_SEARCH;
					}

					@Override
					public String getMenuId() {
						return OrderViewConstants.MENU_ORDERVIEW_SEARCH;
					}

					@Override
					public String getParentMenuId() {
						return null;
					}

					@Override
					public String getTitle() {
						return "订单详细";
					}

					@Override
					public void execute() {

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										OrderViewConstants.EDITOR_ORDERVIEW_SEARCH,
										"EDITOR_ORDERVIEW_SEARCH_DO_ID", null);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderViewPluginDescriptor.this;
			}

		});


		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return orderViewEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderViewPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return OrderViewConstants.PLUGIN_ORDERVIEW;
	}

	@Override
	public Plugin getInstance() {
		return OrderViewPlugin;
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
