package com.chinarewards.gwt.elt.client;

import com.chinarewards.gwt.elt.client.awardReward.module.AwardRewardModule;
import com.chinarewards.gwt.elt.client.chooseStaff.module.ChooseStaffModule;
import com.chinarewards.gwt.elt.client.core.presenter.DockModule;
import com.chinarewards.gwt.elt.client.detailsOfAward.module.DetailsOfAwardModule;
import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisesModule;
import com.chinarewards.gwt.elt.client.gift.module.GiftListModule;
import com.chinarewards.gwt.elt.client.gift.module.GiftModule;
import com.chinarewards.gwt.elt.client.nominate.module.NominateModule;
import com.chinarewards.gwt.elt.client.rewardItem.module.RewardsItemModule;
import com.chinarewards.gwt.elt.client.rewards.module.RewardsListModule;
import com.chinarewards.gwt.elt.client.staff.module.HrRegisterModule;
import com.chinarewards.gwt.elt.client.user.module.UserModule;
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
	}

}
