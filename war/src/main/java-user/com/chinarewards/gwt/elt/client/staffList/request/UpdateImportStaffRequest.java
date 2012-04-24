/**
 * 
 */
package com.chinarewards.gwt.elt.client.staffList.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:43
 */
public class UpdateImportStaffRequest implements Action<UpdateImportStaffResponse> {

	private String  rawId;
	private int  fal;
	private UserSession session;


	public UpdateImportStaffRequest() {
	}



	public String getRawId() {
		return rawId;
	}



	public void setRawId(String rawId) {
		this.rawId = rawId;
	}



	public int getFal() {
		return fal;
	}



	public void setFal(int fal) {
		this.fal = fal;
	}



	public UserSession getSession() {
		return session;
	}



	public void setSession(UserSession session) {
		this.session = session;
	}



	/**
	 * 
	 * @param StaffListVo
	 */
	public UpdateImportStaffRequest(UserSession session,String rawId,int fal)  {
		this.session = session;
		this.rawId=rawId;
		this.fal=fal;
	}


}
