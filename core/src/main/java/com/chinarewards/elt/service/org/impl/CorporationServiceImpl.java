package com.chinarewards.elt.service.org.impl;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.CorporationVo;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.CorporationService;
import com.google.inject.Inject;

public class CorporationServiceImpl implements CorporationService {

	CorporationLogic corporationLogic;

	@Inject
	public CorporationServiceImpl(CorporationLogic corporationLogic) {
		this.corporationLogic = corporationLogic;
	}

	@Override
	public Corporation saveCorporation(SysUser caller, CorporationVo corporation) {
		return corporationLogic.saveCorporation(caller, corporation);
	}

	@Override
	public Corporation findCorporationById(String id) {
		return corporationLogic.findCorporationById(id);
	}

	@Override
	public double callBalance(String corporationId, String unitCode) {
		return corporationLogic.callBalance(corporationId, unitCode);
	}

}
