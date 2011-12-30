package com.chinarewards.elt.service.org.impl;

import javax.persistence.EntityManager;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.CorporationVo;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.CorporationService;
import com.chinarewards.elt.tx.service.TransactionService;
import com.google.inject.Inject;

public class CorporationServiceImpl implements CorporationService {

	private final CorporationLogic corporationLogic;
	private final EntityManager em;
	private final TransactionService transactionService;
	@Inject
	public CorporationServiceImpl(CorporationLogic corporationLogic,EntityManager em,TransactionService transactionService) {
		this.corporationLogic = corporationLogic;
		this.em = em;
        this. transactionService = transactionService;
	}

	@Override
	public Corporation saveCorporation(SysUser caller, CorporationVo corporationVo) {
		if (em.getTransaction().isActive() != true) {
			em.getTransaction().begin();
		}
		String accountId = transactionService.createNewAccount();
		corporationVo.setTxAccountId(accountId);
		Corporation corporation =  corporationLogic.saveCorporation(caller, corporationVo);
		em.getTransaction().commit();
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
