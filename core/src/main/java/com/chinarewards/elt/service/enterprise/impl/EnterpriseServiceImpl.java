package com.chinarewards.elt.service.enterprise.impl;

import java.util.List;

import com.chinarewards.elt.domain.enterprise.Enterprise;

import com.chinarewards.elt.service.enterprise.IEnterpriseService;
import com.chinarewards.elt.common.dao.BaseDao;
import com.google.inject.Inject;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EnterpriseServiceImpl  implements IEnterpriseService {
	@Inject
	private BaseDao baseDao;
	
	Enterprise  enterprise = new Enterprise();
	@Override
	public Enterprise getEnterpriseInfo() throws IllegalArgumentException {
		 enterprise = (Enterprise)baseDao.findFirstOne("from enterprise ");
		
		if (enterprise!=null) {
			return enterprise;
			
		} else {
			return null;
		}
	}
	@Override
	public String addEnterpriseInfo(Enterprise enterprise) throws IllegalArgumentException {
		//System.out.println("========"+enterprise.getId());
		baseDao.save(enterprise);
		return "企业注册成功！";
	}
	
	
}
