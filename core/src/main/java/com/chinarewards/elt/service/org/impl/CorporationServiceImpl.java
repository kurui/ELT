package com.chinarewards.elt.service.org.impl;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.org.CorporationVo;
import com.chinarewards.elt.model.transaction.TransactionUnit;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.CorporationService;
import com.chinarewards.elt.tx.service.TransactionService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Transactional
public class CorporationServiceImpl implements CorporationService {

	private final CorporationLogic corporationLogic;
	private final TransactionService transactionService;

	@Inject
	public CorporationServiceImpl(CorporationLogic corporationLogic,
			TransactionService transactionService) {
		this.corporationLogic = corporationLogic;
		this.transactionService = transactionService;
	}

	public Corporation saveCorporation(SysUser caller,
			CorporationVo corporationVo) {
		// if (em.getTransaction().isActive() != true) {
		// em.getTransaction().begin();
		// }
		String accountId = transactionService.createNewAccount();
		String unitCode = TransactionUnit.BEANPOINTS.toString();
		corporationVo.setTxAccountId(accountId);
		corporationVo.setUnitCode(unitCode);
		// ===============================================================
		// 设置企业积分比例暂时不用-李伟
		// try {
		// transactionService.createNewUnit("缤分", UnitCode, 0.8);
		// } catch (DuplicateUnitCodeException e) {
		// // should not be here
		// }
		// 存入的企业积分暂时不用-李伟
		// transactionService.deposit(accountId, UnitCode, 100000);
		// =======================================================================
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
