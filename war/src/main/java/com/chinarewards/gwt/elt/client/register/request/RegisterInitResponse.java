package com.chinarewards.gwt.elt.client.register.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;

public class RegisterInitResponse implements Result {

	private int count;

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public RegisterInitResponse(int count) {
      this.count = count;
	}
	public RegisterInitResponse() {
	      
	
	}
}
