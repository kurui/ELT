package com.chinarewards.gwt.elt.client.core;

import com.chinarewards.gwt.elt.client.awardReward.plugin.AwardRewardPluginDescriptor;
import com.chinarewards.gwt.elt.client.core.impl.CorePluginDescriptor;
import com.chinarewards.gwt.elt.client.core.impl.GinPluginManager;
import com.chinarewards.gwt.elt.client.core.impl.InMemoryMenuRoleStore;
import com.chinarewards.gwt.elt.client.core.impl.InMemoryPluginSet;
import com.chinarewards.gwt.elt.client.core.ui.EditorRegistry;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.core.ui.SiteManager;
import com.chinarewards.gwt.elt.client.core.ui.impl.SimpleEditorRegistry;
import com.chinarewards.gwt.elt.client.core.ui.impl.SimpleMenuProcessor;
import com.chinarewards.gwt.elt.client.core.ui.impl.SimpleSiteManager;
import com.chinarewards.gwt.elt.client.detailsOfAward.plugin.DetailsOfAwardPluginDescriptor;
import com.chinarewards.gwt.elt.client.nominate.plugin.NominatePluginDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemListPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemViewPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListPluginDescriptor;
import com.chinarewards.gwt.elt.client.sample.SamplePluginDescriptor;
import com.chinarewards.gwt.elt.client.sample.SamplePluginModule;
import com.chinarewards.gwt.elt.client.staff.plugin.HrRegisterPluginDescriptor;
import com.chinarewards.gwt.elt.client.user.plugin.UserPluginDescriptor;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class PlatformModule extends AbstractGinModule {

	InMemoryPluginSet pluginSet = null;

	@Override
	protected void configure() {
		bind(Platform.class).in(Singleton.class);
		bind(PluginManager.class).to(GinPluginManager.class)
				.in(Singleton.class);

		bind(MenuProcessor.class).to(SimpleMenuProcessor.class).in(
				Singleton.class);
		bind(SiteManager.class).to(SimpleSiteManager.class).in(Singleton.class);
		bind(EditorRegistry.class).to(SimpleEditorRegistry.class).in(
				Singleton.class);

		bind(MenuRoleStore.class).to(InMemoryMenuRoleStore.class).in(
				Singleton.class);

		// ---- PLUGINS DEFINE BELOW (1) ----
		bind(CorePluginDescriptor.class).in(Singleton.class);

		install(new SamplePluginModule());

	}

	@Provides
	PluginSet providePluginSet(
			// ---- PLUGINS DEFINE BELOW (2) ----
			CorePluginDescriptor core, // core

			SamplePluginDescriptor sample, UserPluginDescriptor user,
			HrRegisterPluginDescriptor hrregister,
			NominatePluginDescriptor nominate,
			RewardsItemPluginDescriptor rewardsItem,
			RewardsItemListPluginDescriptor rewardsItemList,
			RewardsItemViewPluginDescriptor rewardsItemView,
			RewardsListPluginDescriptor rewardsList,
			AwardRewardPluginDescriptor awardreward,
			DetailsOfAwardPluginDescriptor detailsAward) {

		if (pluginSet == null) {
			pluginSet = new InMemoryPluginSet();
			pluginSet.registerPlugin(core);
			pluginSet.registerPlugin(sample);
			pluginSet.registerPlugin(user);
			pluginSet.registerPlugin(hrregister);
            pluginSet.registerPlugin(rewardsItemList);
			pluginSet.registerPlugin(nominate);
			pluginSet.registerPlugin(rewardsItem);
			pluginSet.registerPlugin(rewardsList);
			pluginSet.registerPlugin(awardreward);
			pluginSet.registerPlugin(detailsAward);
			pluginSet.registerPlugin(rewardsItemView);
		}

		return pluginSet;
	}
}
