package com.chinarewards.gwt.elt.client.staffIntegral.request;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author yanrui
 */
public class StaffIntegralResponse implements Result {

	private String staffId;
	private String totalIntegral;
	private String expenseIntegral;
	private String balanceIntegral;

	public StaffIntegralResponse() {

	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(String totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public String getExpenseIntegral() {
		return expenseIntegral;
	}

	public void setExpenseIntegral(String expenseIntegral) {
		this.expenseIntegral = expenseIntegral;
	}

	public String getBalanceIntegral() {
		return balanceIntegral;
	}

	public void setBalanceIntegral(String balanceIntegral) {
		this.balanceIntegral = balanceIntegral;
	}

}
