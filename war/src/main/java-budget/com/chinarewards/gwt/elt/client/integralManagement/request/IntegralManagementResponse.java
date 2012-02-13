/**
 * 
 */
package com.chinarewards.gwt.elt.client.integralManagement.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.integralManagement.model.Category;

/**
 * @author nicho
 * @since 2012年1月9日 19:00:32
 */
public class IntegralManagementResponse implements Result {

	private List<Category> result;

	public IntegralManagementResponse(List<Category> result) {
		this.result = result;
	}

	public IntegralManagementResponse() {
	}

	public List<Category> getResult() {
		return result;
	}

	public void setResult(List<Category> result) {
		this.result = result;
	}

}
