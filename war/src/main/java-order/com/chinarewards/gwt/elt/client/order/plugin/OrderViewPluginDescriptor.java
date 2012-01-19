/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.plugin;

import java.util.HashSet;
import java.util.Set;

import com.chinarewards.gwt.elt.client.core.Extension;
import com.chinarewards.gwt.elt.client.core.ExtensionPoint;
import com.chinarewards.gwt.elt.client.core.Plugin;
import com.chinarewards.gwt.elt.client.core.PluginDescriptor;
import com.chinarewards.gwt.elt.client.order.editor.OrderViewEditorDescriptor;
import com.chinarewards.gwt.elt.client.plugin.PluginConstants;
import com.chinarewards.gwt.elt.client.order.plugin.OrderConstants;
import com.chinarewards.gwt.elt.client.order.plugin.OrderViewPlugin;
import com.chinarewards.gwt.elt.client.order.plugin.OrderViewPluginDescriptor;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 2012-1-16
 */
public class OrderViewPluginDescriptor implements PluginDescriptor {

	final static Set<Extension> ext = new HashSet<Extension>();
	final OrderViewPlugin orderViewPlugin;
	final OrderViewEditorDescriptor orderViewEditorDescriptor;

	@Inject
	public OrderViewPluginDescriptor(
			final OrderViewEditorDescriptor orderViewEditorDescriptor) {
		this.orderViewEditorDescriptor = orderViewEditorDescriptor;
		orderViewPlugin = new OrderViewPlugin(this);

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
		return OrderConstants.PLUGIN_GIFT;
	}

	@Override
	public Plugin getInstance() {
		return orderViewPlugin;
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
