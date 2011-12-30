package com.chinarewards.elt.service.org.impl;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.CorporationVo;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.CorporationService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class CorporationServiceImpl implements CorporationService {

	private final CorporationLogic corporationLogic;

	@Inject
	public CorporationServiceImpl(CorporationLogic corporationLogic) {
		this.corporationLogic = corporationLogic;
	}

	@Override
	public Corporation saveCorporation(SysUser caller,
			CorporationVo corporationVo) {
		// if (em.getTransaction().isActive() != true) {
		// em.getTransaction().begin();
		// }
		Corporation corporation = corporationLogic.saveCorporation(caller,
				corporationVo);
		// em.getTransaction().commit();
		return corporation;
	}

	@Override
	public Corporation findCorporationById(String id) {
		return corporationLogic.findCorporationById(id);
	}

	@Override
	public double callBalance(String corporationId) {
		return corporationLogic.callBalance(corporationId);
	}

}
