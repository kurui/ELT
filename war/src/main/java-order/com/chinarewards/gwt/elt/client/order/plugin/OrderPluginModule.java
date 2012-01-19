/**
 * 
 */
package com.chinarewards.gwt.elt.client.order.plugin;

import com.chinarewards.gwt.elt.client.gift.plugin.GiftPluginDescriptor;
import com.chinarewards.gwt.elt.client.order.editor.OrderEditor;
import com.chinarewards.gwt.elt.client.order.editor.OrderEditorDescriptor;
import com.chinarewards.gwt.elt.client.order.editor.OrderViewEditor;
import com.chinarewards.gwt.elt.client.order.editor.OrderViewEditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * @author yanrui
 * @since
 */
public class OrderPluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(GiftPluginDescriptor.class).in(Singleton.class);

		bind(OrderEditorDescriptor.class).in(Singleton.class);		
		bind(OrderEditor.class);
		
		bind(OrderViewEditorDescriptor.class).in(Singleton.class);
		bind(OrderViewEditor.class);
	}

}
