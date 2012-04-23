package com.chinarewards.elt.guice.sub;

import com.chinarewards.elt.dao.gift.GiftDao;
import com.chinarewards.elt.service.gift.GiftLogic;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.elt.service.gift.ImportGiftCodeLogic;
import com.chinarewards.elt.service.gift.ImportGiftLogic;
import com.chinarewards.elt.service.gift.ImportGiftService;
import com.chinarewards.elt.service.gift.impl.GiftLogicImpl;
import com.chinarewards.elt.service.gift.impl.GiftServiceImpl;
import com.chinarewards.elt.service.gift.impl.ImportGiftCodeLogicImpl;
import com.chinarewards.elt.service.gift.impl.ImportGiftLogicImpl;
import com.chinarewards.elt.service.gift.impl.ImportGiftServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * gift module.
 * 
 * @author nicho
 * @since 2012年1月10日 16:00:44
 */
public class GiftModule extends AbstractModule {


	@Override
	protected void configure() {
		// gift bind
		bind(GiftDao.class).in(Singleton.class);

		bind(GiftLogic.class).to(GiftLogicImpl.class).in(Singleton.class);

		bind(GiftService.class).to(GiftServiceImpl.class).in(Singleton.class);
		
		
		bind(ImportGiftLogic.class).to(ImportGiftLogicImpl.class);
		bind(ImportGiftService.class).to(ImportGiftServiceImpl.class);
		
		bind(ImportGiftCodeLogic.class).to(ImportGiftCodeLogicImpl.class);
	}

}
