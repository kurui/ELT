package com.chinarewards.gwt.elt.client;


import com.chinarewards.gwt.elt.client.awardReward.module.AwardRewardModule;
import com.chinarewards.gwt.elt.client.awardShop.module.AwardShopListModule;
import com.chinarewards.gwt.elt.client.awardShopLattice.module.AwardShopLatticeModule;
import com.chinarewards.gwt.elt.client.breadCrumbs.module.BreadCrumbsModule;
import com.chinarewards.gwt.elt.client.chooseStaff.module.ChooseStaffModule;
import com.chinarewards.gwt.elt.client.core.presenter.DockModule;
import com.chinarewards.gwt.elt.client.detailsOfAward.module.DetailsOfAwardModule;
import com.chinarewards.gwt.elt.client.detailsOfGift.module.DetailsOfGiftModule;
import com.chinarewards.gwt.elt.client.enterprise.module.EnterprisesModule;
import com.chinarewards.gwt.elt.client.gift.module.GiftListModule;
import com.chinarewards.gwt.elt.client.gift.module.GiftModule;
import com.chinarewards.gwt.elt.client.nominate.module.NominateModule;
import com.chinarewards.gwt.elt.client.order.module.OrderBoxModule;
import com.chinarewards.gwt.elt.client.order.module.OrderListModule;
import com.chinarewards.gwt.elt.client.order.module.OrderViewModule;
import com.chinarewards.gwt.elt.client.orderConfirmation.module.OrderConfirmationModule;
import com.chinarewards.gwt.elt.client.orderHistory.module.OrderHistoryModule;
import com.chinarewards.gwt.elt.client.orderSubmit.module.OrderSubmitModule;
import com.chinarewards.gwt.elt.client.rewardItem.module.RewardsItemModule;
import com.chinarewards.gwt.elt.client.rewards.module.RewardsListModule;
import com.chinarewards.gwt.elt.client.shopWindow.module.ShopWindowModule;
import com.chinarewards.gwt.elt.client.staff.module.HrRegisterModule;
import com.chinarewards.gwt.elt.client.user.module.UserModule;
import com.chinarewards.gwt.elt.client.win.WinModule;
import com.google.gwt.inject.client.AbstractGinModule;

public class PresenterModule extends AbstractGinModule {

	@Override
	protected void configure() {
		install(new DockModule());
		install(new UserModule());
		install(new HrRegisterModule());
		install(new NominateModule());
		install(new EnterprisesModule());
		install(new RewardsItemModule());
		install(new RewardsListModule());
		install(new ChooseStaffModule());
		install(new AwardRewardModule());
		install(new DetailsOfAwardModule());
		install(new GiftListModule());
		install(new GiftModule());
		install(new OrderListModule());
		install(new OrderHistoryModule());
		install(new OrderViewModule());
		install(new OrderBoxModule());
		install(new WinModule());
		install(new BreadCrumbsModule());
		install(new AwardShopListModule());
		install(new AwardShopLatticeModule());
		install(new ShopWindowModule());
		install(new OrderConfirmationModule());
		install(new OrderSubmitModule());
		install(new DetailsOfGiftModule());

	}

}
