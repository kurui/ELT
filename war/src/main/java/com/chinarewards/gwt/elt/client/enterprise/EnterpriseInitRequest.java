package com.chinarewards.gwt.elt.client.enterprise;

import java.util.Date;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.client.support.UserSession;

import net.customware.gwt.dispatch.shared.Action;

public class EnterpriseInitRequest implements Action<EnterpriseInitResponse> {

	
	private UserSession userSession;
	public EnterpriseInitRequest(){
	}
   
	public EnterpriseInitRequest(UserSession userSession){
    	this.userSession = userSession;
    }
  	

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
