package com.chinarewards.elt.guice;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.chinarewards.elt.guice.sub.EnterpriseModule;
import com.chinarewards.elt.guice.sub.OrgModule;
import com.chinarewards.elt.guice.sub.RewardModule;
import com.chinarewards.elt.guice.sub.StaffModule;
import com.chinarewards.elt.guice.sub.UserModule;
import com.chinarewards.elt.tx.guice.TxModule;
import com.google.inject.AbstractModule;

public class EltModule extends AbstractModule {

	@Override
	protected void configure() {
		EntityManager em = Persistence.createEntityManagerFactory("elt")
				.createEntityManager();
		install(new CommonModule(em));

		install(new RewardModule());

		install(new UserModule());
		install(new OrgModule());
		install(new StaffModule());
        install(new EnterpriseModule());
		install(new TxModule());
	}

}
