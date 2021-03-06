/**
 * 
 */
package com.chinarewards.gwt.elt.client.orderConfirmation.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.orderConfirmation.editor.OrderConfirmationEditorDescriptor;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author nicho
 * @since 2012年1月31日 11:39:18
 */
public class OrderConfirmationPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final OrderConfirmationPlugin OrderConfirmationPlugin;
	final OrderConfirmationEditorDescriptor orderConfirmationEditorDescriptor;

	@Inject
	public OrderConfirmationPluginDescriptor(
			final OrderConfirmationEditorDescriptor orderConfirmationEditorDescriptor) {
		this.orderConfirmationEditorDescriptor = orderConfirmationEditorDescriptor;
		OrderConfirmationPlugin = new OrderConfirmationPlugin(this);

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
						return MenuConstants.MENU_ORDER_ORDERCONFIRMATION_SEARCH;
					}

					@Override
					public String getMenuId() {
						return OrderConfirmationConstants.MENU_ORDERCONFIRMATION_SEARCH;
					}

					@Override
					public String getParentMenuId() {
						return null;
					}

					@Override
					public String getTitle() {
						return "订单确认";
					}

					@Override
					public void execute() {

						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										OrderConfirmationConstants.EDITOR_ORDERCONFIRMATION_SEARCH,
										"EDITOR_ORDERCONFIRMATION_SEARCH_DO_ID", null);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderConfirmationPluginDescriptor.this;
			}

		});


		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return orderConfirmationEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderConfirmationPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return OrderConfirmationConstants.PLUGIN_ORDERCONFIRMATION;
	}

	@Override
	public Plugin getInstance() {
		return OrderConfirmationPlugin;
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
