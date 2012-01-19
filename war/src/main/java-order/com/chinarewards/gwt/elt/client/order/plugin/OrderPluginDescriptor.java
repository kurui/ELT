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
import com.chinarewards.gwt.elt.client.order.plugin.OrderConstants;
import com.chinarewards.gwt.elt.client.order.plugin.OrderPlugin;
import com.chinarewards.gwt.elt.client.order.plugin.OrderPluginDescriptor;
import com.chinarewards.gwt.elt.client.order.editor.OrderEditorDescriptor;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.plugin.MenuConstants;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since
 */
public class OrderPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final OrderPlugin OrderPlugin;
	final OrderEditorDescriptor orderEditorDescriptor;

	@Inject
	public OrderPluginDescriptor(final OrderEditorDescriptor orderEditorDescriptor) {
		this.orderEditorDescriptor = orderEditorDescriptor;
		OrderPlugin = new OrderPlugin(this);

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
						return OrderConstants.MENU_GIFT_ADD;
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
						OrderVo orderClient = new OrderVo();
						orderClient.setThisAction(OrderConstants.ACTION_GIFT_ADD);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(OrderConstants.EDITOR_GIFT_EDIT,
										OrderConstants.ACTION_GIFT_ADD,
										orderClient);
					}

					@Override
					public Image getIcon() {
						return null;
					}

				};
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderPluginDescriptor.this;
			}
		});

		
		ext.add(new Extension() {

			@Override
			public String getExtensionPointId() {
				return PluginConstants.EDITOR;
			}

			@Override
			public Object getInstance() {
				return orderEditorDescriptor;
			}

			@Override
			public PluginDescriptor getPluginDescriptor() {
				return OrderPluginDescriptor.this;
			}

		});
	}

	@Override
	public String getPluginId() {
		return OrderConstants.PLUGIN_GIFT;
	}

	@Override
	public Plugin getInstance() {
		return OrderPlugin;
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
