/**
 * 
 */
package com.chinarewards.gwt.elt.client.staffList.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * StaffGenerateUser
 * 
 * @author nicho
 * @since 2012年2月15日 17:08:24
 */
public class DeleteStaffRequest implements Action<DeleteStaffResponse> {

	private String staffId;

	private UserSession session;

	private String fal;
	
	public String getFal() {
		return fal;
	}


	public void setFal(String fal) {
		this.fal = fal;
	}


	public UserSession getSession() {
		return session;
	}


	public void setSession(UserSession session) {
		this.session = session;
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public DeleteStaffRequest() {
	}


	public DeleteStaffRequest(String staffId,UserSession session,String fal) {
		this.staffId = staffId;
		this.session=session;
		this.fal=fal;
	}


}
