/**
 * 
 */
package com.chinarewards.gwt.elt.client.rewardItem.plugin;


import com.chinarewards.gwt.elt.client.rewardItem.editor.RewardsItemEditor;
import com.chinarewards.gwt.elt.client.rewardItem.editor.RewardsItemEditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * @author NIcho
 * @since 2011年12月9日 
 */
public class RewardsItemPluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(RewardsItemPluginDescriptor.class).in(Singleton.class);

		bind(RewardsItemEditorDescriptor.class).in(Singleton.class);
		bind(RewardsItemEditor.class);
	}

}
