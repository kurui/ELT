package com.chinarewards.gwt.elt.client.enterprise;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;

public class EnterpriseRequest implements Action<EnterpriseResponse> {

	private EnterpriseVo enterprisevo;
	
	public EnterpriseRequest(){
	}
	
    public EnterpriseRequest(EnterpriseVo enterprisevo){
    	this.enterprisevo = enterprisevo;
    }
	public EnterpriseVo getEnterprise() {
		return enterprisevo;
	}
   
	public void setEnterprise(EnterpriseVo enterprisevo) {
		this.enterprisevo = enterprisevo;
	}
}
