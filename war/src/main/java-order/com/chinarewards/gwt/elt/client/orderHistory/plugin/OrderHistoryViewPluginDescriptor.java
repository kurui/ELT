/**
 * 
 */
package com.chinarewards.gwt.elt.client.orderHistory.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.orderHistory.editor.OrderHistoryEditorDescriptor;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class OrderHistoryViewPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final OrderHistoryViewPlugin orderHistoryViewPlugin;
	final OrderHistoryEditorDescriptor orderHistoryEditorDescriptor;

	@Inject
	public OrderHistoryViewPluginDescriptor(
			final OrderHistoryEditorDescriptor orderHistoryEditorDescriptor) {
		this.orderHistoryEditorDescriptor = orderHistoryEditorDescriptor;
		orderHistoryViewPlugin = new OrderHistoryViewPlugin(this);

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
						return MenuConstants.MENU_ORDER_ORDERHISTORY_SEARCH;
					}

					@Override
					public String getMenuId() {
						return OrderHistoryConstants.MENU_ORDERHISTORY_SEARCH;
					}

					@Override
					public String getParentMenuId() {
						return null;
					}

					@Override
					public String getTitle() {
						return "兑换历史";
					}

					@Override
					public void execute() {

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										OrderHistoryConstants.EDITOR_ORDERHISTORY_SEARCH,
										"EDITOR_HISTORY_SEARCH_DO_ID", null);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderHistoryViewPluginDescriptor.this;
			}

		});

		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return orderHistoryEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderHistoryViewPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return OrderHistoryConstants.PLUGIN_ORDERHISTORY;
	}

	@Override
	public Plugin getInstance() {
		return orderHistoryViewPlugin;
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
