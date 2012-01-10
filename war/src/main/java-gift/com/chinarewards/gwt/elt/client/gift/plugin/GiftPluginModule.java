/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.plugin;

import com.chinarewards.gwt.elt.client.gift.editor.GiftEditor;
import com.chinarewards.gwt.elt.client.gift.editor.GiftEditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * @author yanrui
 * @since 2011年12月9日 
 */
public class GiftPluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(GiftPluginDescriptor.class).in(Singleton.class);

		bind(GiftEditorDescriptor.class).in(Singleton.class);
		bind(GiftEditor.class);
	}

}
