package com.chinarewards.elt.dao.enterprise;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.enterprise.Enterprise;

public class EnterpriseDao extends BaseDao<Enterprise> {

	// FIXME Here invoke itself?
	@SuppressWarnings("unchecked")
	public Enterprise findFirstOne() {

		// return (Enterprise)findFirstOne("from enterprise");
		return null;
	}

	public String createEnterprise(Enterprise enterprise) {
		enterprise = this.save(enterprise);
		return enterprise.getId();
	}
}
