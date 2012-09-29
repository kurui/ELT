/**
 * 
 */
package com.chinarewards.gwt.elt.client.awardRewardDetermine.plugin;

import com.chinarewards.gwt.elt.client.awardRewardDetermine.editor.AwardRewardDetermineEditor;
import com.chinarewards.gwt.elt.client.awardRewardDetermine.editor.AwardRewardDetermineEditorDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * @author NIcho
 * @since 2011年12月9日 
 */
public class AwardRewardDeterminePluginModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(AwardRewardDeterminePluginDescriptor.class).in(Singleton.class);

		bind(AwardRewardDetermineEditorDescriptor.class).in(Singleton.class);
		bind(AwardRewardDetermineEditor.class);
	}

}
