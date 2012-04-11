/**
 * 
 */
package com.chinarewards.gwt.elt.client.staffAdd.request;

import net.customware.gwt.dispatch.shared.Action;

public class StaffVaildRequest implements Action<StaffVaildResponse> {
	private String staffNo;
	private String staffEmail;

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getStaffEmail() {
		return staffEmail;
	}

	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}

	public StaffVaildRequest() {
	}

}
