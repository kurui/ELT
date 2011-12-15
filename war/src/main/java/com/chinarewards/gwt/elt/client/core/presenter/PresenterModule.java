package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.nominate.module.NominateModule;
import com.chinarewards.gwt.elt.client.enterprise.presenter.EnterprisesModule;
import com.chinarewards.gwt.elt.client.rewardItem.module.RewardsItemModule;
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
//		install(new RewardsItemModule());
	}

}
