/**
 * 
 */
package com.chinarewards.gwt.elt.client.staffList.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.staff.model.StaffVo;

/**
 * An action which perform request to search user.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:43
 */
public class StaffListRequest implements Action<StaffListResponse> {

	private StaffVo staffvo;


	public StaffVo getStaffvo() {
		return staffvo;
	}

	public void setStaffvo(StaffVo staffvo) {
		this.staffvo = staffvo;
	}

	public StaffListRequest() {
	}

	/**
	 * 
	 * @param StaffListVo
	 */
	public StaffListRequest(StaffVo staffvo) {
		this.staffvo = staffvo;
	}


}
