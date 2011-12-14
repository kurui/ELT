package com.chinarewards.gwt.elt.client.enterprise;

import java.util.Date;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;

import net.customware.gwt.dispatch.shared.Action;

public class EnterpriseInitRequest implements Action<EnterpriseInitResponse> {

	private EnterpriseVo enterprise;
	
	public EnterpriseInitRequest(){
	}

  	public EnterpriseVo getEnterprise() {
		return enterprise;
	}
   
	public void setEnterprise(EnterpriseVo enterprise) {
		this.enterprise = enterprise;
	}
}
