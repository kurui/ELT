package com.chinarewards.gwt.elt.client.core;

import com.chinarewards.gwt.elt.client.awardReward.plugin.AwardRewardPluginDescriptor;
import com.chinarewards.gwt.elt.client.awardShop.plugin.AwardShopListPluginDescriptor;
import com.chinarewards.gwt.elt.client.budget.plugin.BudgetPluginDescriptor;
import com.chinarewards.gwt.elt.client.budget.plugin.CorpBudgetPluginDescriptor;
import com.chinarewards.gwt.elt.client.core.impl.CorePluginDescriptor;
import com.chinarewards.gwt.elt.client.core.impl.GinPluginManager;
import com.chinarewards.gwt.elt.client.core.impl.InMemoryMenuRoleStore;
import com.chinarewards.gwt.elt.client.core.impl.InMemoryPluginSet;
import com.chinarewards.gwt.elt.client.core.ui.EditorRegistry;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.core.ui.SiteManager;
import com.chinarewards.gwt.elt.client.core.ui.impl.ButtonMenuProcessor;
import com.chinarewards.gwt.elt.client.core.ui.impl.SimpleEditorRegistry;
import com.chinarewards.gwt.elt.client.core.ui.impl.SimpleSiteManager;
import com.chinarewards.gwt.elt.client.detailsOfAward.plugin.DetailsOfAwardPluginDescriptor;
import com.chinarewards.gwt.elt.client.detailsOfGift.plugin.DetailsOfGiftPluginDescriptor;
import com.chinarewards.gwt.elt.client.enterprise.plugin.EnterprisePluginDescriptor;
import com.chinarewards.gwt.elt.client.enterprise.plugin.EnterprisePluginModule;
import com.chinarewards.gwt.elt.client.enterprise.plugin.IntegralPricePluginDescriptor;
import com.chinarewards.gwt.elt.client.enterprise.plugin.PeriodPluginDescriptor;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftListPluginDescriptor;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftPluginDescriptor;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftViewPluginDescriptor;
import com.chinarewards.gwt.elt.client.integralManagement.plugin.IntegralManagementPluginDescriptor;
import com.chinarewards.gwt.elt.client.nominate.plugin.NominatePluginDescriptor;
import com.chinarewards.gwt.elt.client.order.plugin.OrderBoxPluginDescriptor;
import com.chinarewards.gwt.elt.client.order.plugin.OrderListPluginDescriptor;
import com.chinarewards.gwt.elt.client.order.plugin.OrderViewPluginDescriptor;
import com.chinarewards.gwt.elt.client.orderConfirmation.plugin.OrderConfirmationPluginDescriptor;
import com.chinarewards.gwt.elt.client.orderHistory.plugin.OrderHistoryPluginDescriptor;
import com.chinarewards.gwt.elt.client.orderHistory.plugin.OrderHistoryViewPluginDescriptor;
import com.chinarewards.gwt.elt.client.orderSubmit.plugin.OrderSubmitPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemListPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemStoreListPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemViewPluginDescriptor;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListPluginDescriptor;
import com.chinarewards.gwt.elt.client.shopWindow.plugin.ShopWindowPluginDescriptor;
import com.chinarewards.gwt.elt.client.staff.plugin.HrRegisterPluginDescriptor;
import com.chinarewards.gwt.elt.client.staffAdd.plugin.StaffAddPluginDescriptor;
import com.chinarewards.gwt.elt.client.staffList.plugin.StaffListPluginDescriptor;
import com.chinarewards.gwt.elt.client.staffView.plugin.StaffViewPluginDescriptor;
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

		bind(MenuProcessor.class).to(ButtonMenuProcessor.class).in(
				Singleton.class);
		bind(SiteManager.class).to(SimpleSiteManager.class).in(Singleton.class);
		bind(EditorRegistry.class).to(SimpleEditorRegistry.class).in(
				Singleton.class);

		bind(MenuRoleStore.class).to(InMemoryMenuRoleStore.class).in(
				Singleton.class);

		// ---- PLUGINS DEFINE BELOW (1) ----
		bind(CorePluginDescriptor.class).in(Singleton.class);

		install(new EnterprisePluginModule());

	}

	@Provides
	PluginSet providePluginSet(
			// ---- PLUGINS DEFINE BELOW (2) ----
			CorePluginDescriptor core, // core

			EnterprisePluginDescriptor enterprise,
			IntegralPricePluginDescriptor integralPrice,
			PeriodPluginDescriptor period, UserPluginDescriptor user,
			HrRegisterPluginDescriptor hrregister,
			NominatePluginDescriptor nominate,
			RewardsItemPluginDescriptor rewardsItem,
			RewardsItemStoreListPluginDescriptor rewardsItemStoreList,
			RewardsItemListPluginDescriptor rewardsItemList,
			RewardsItemViewPluginDescriptor rewardsItemView,
			RewardsListPluginDescriptor rewardsList,
			AwardRewardPluginDescriptor awardreward,
			DetailsOfAwardPluginDescriptor detailsAward,
			GiftPluginDescriptor gift, GiftListPluginDescriptor giftList,
			GiftViewPluginDescriptor giftView,
			OrderListPluginDescriptor orderList,
			OrderViewPluginDescriptor orderView,
			OrderBoxPluginDescriptor orderBox,
			AwardShopListPluginDescriptor awardShop,
			ShopWindowPluginDescriptor shopWindow,
			OrderHistoryPluginDescriptor orderHistory,
			OrderHistoryViewPluginDescriptor orderHistoryView,
			OrderSubmitPluginDescriptor orderSubmit,
			OrderConfirmationPluginDescriptor orderConfirmation,
			DetailsOfGiftPluginDescriptor detailsOfGift,
			CorpBudgetPluginDescriptor corpBudget,
			BudgetPluginDescriptor depBudget,
			IntegralManagementPluginDescriptor integralManagement,
			StaffListPluginDescriptor staffList,
			StaffAddPluginDescriptor staffAdd,
			StaffViewPluginDescriptor staffView) {

		if (pluginSet == null) {
			pluginSet = new InMemoryPluginSet();
			pluginSet.registerPlugin(core);
			pluginSet.registerPlugin(enterprise);
			pluginSet.registerPlugin(integralPrice);
			pluginSet.registerPlugin(period);
			pluginSet.registerPlugin(user);
			pluginSet.registerPlugin(hrregister);
			pluginSet.registerPlugin(rewardsItemList);
			pluginSet.registerPlugin(rewardsItemStoreList);
			pluginSet.registerPlugin(nominate);
			pluginSet.registerPlugin(rewardsItem);
			pluginSet.registerPlugin(rewardsList);
			pluginSet.registerPlugin(awardreward);
			pluginSet.registerPlugin(detailsAward);
			pluginSet.registerPlugin(rewardsItemView);
			pluginSet.registerPlugin(giftList);
			pluginSet.registerPlugin(gift);
			pluginSet.registerPlugin(giftView);
			pluginSet.registerPlugin(orderList);
			pluginSet.registerPlugin(orderHistory);
			pluginSet.registerPlugin(orderHistoryView);
			pluginSet.registerPlugin(orderBox);
			pluginSet.registerPlugin(orderView);
			pluginSet.registerPlugin(awardShop);
			pluginSet.registerPlugin(shopWindow);
			pluginSet.registerPlugin(orderConfirmation);
			pluginSet.registerPlugin(orderSubmit);
			pluginSet.registerPlugin(detailsOfGift);
			pluginSet.registerPlugin(integralManagement);
			pluginSet.registerPlugin(depBudget);
			pluginSet.registerPlugin(corpBudget);
			pluginSet.registerPlugin(staffList);
			pluginSet.registerPlugin(staffAdd);
			pluginSet.registerPlugin(staffView);
		}

		return pluginSet;
	}
}
