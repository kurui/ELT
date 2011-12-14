package com.chinarewards.elt.service.enterprise;

import com.chinarewards.elt.domain.enterprise.Enterprise;


public interface IEnterpriseService {

	public Enterprise getEnterpriseInfo();
	public String addEnterpriseInfo(Enterprise enterprise);
}
