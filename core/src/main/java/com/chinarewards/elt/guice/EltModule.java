package com.chinarewards.elt.guice;

import com.chinarewards.elt.guice.sub.OrgModule;
import com.chinarewards.elt.guice.sub.RewardModule;
import com.chinarewards.elt.guice.sub.StaffModule;
import com.chinarewards.elt.guice.sub.UserModule;
import com.chinarewards.elt.tx.guice.TxModule;
import com.google.inject.AbstractModule;

public class EltModule extends AbstractModule {

	@Override
	protected void configure() {

		install(new CommonModule());

		install(new TxModule());

		install(new RewardModule());

		install(new UserModule());
		install(new OrgModule());
		install(new StaffModule());
	}

}
