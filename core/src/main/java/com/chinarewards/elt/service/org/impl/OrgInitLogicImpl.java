package com.chinarewards.elt.service.org.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.org.OrgInitDao;
import com.chinarewards.elt.domain.org.OrgInit;
import com.chinarewards.elt.service.org.OrgInitLogic;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * 
 * 
 * @author lw
 * @since 1.0
 */
public class OrgInitLogicImpl implements OrgInitLogic {

	Logger logger = LoggerFactory.getLogger(getClass());

	OrgInitDao orgInitDao;
	

	@Inject
	public OrgInitLogicImpl(OrgInitDao orgInitDao) {
			this.orgInitDao = orgInitDao;
			
	}


	@Override
	public OrgInit save( OrgInit gift) {
		if (StringUtil.isEmptyString(gift.getId())) {
			
			orgInitDao.save(gift);
		} else {
			
			orgInitDao.update(gift);
		}

		return gift;
	}


	@Override
	public OrgInit getOrgInit() {
		// TODO Auto-generated method stub
		return orgInitDao.getOrgInit();
	}

	
}
