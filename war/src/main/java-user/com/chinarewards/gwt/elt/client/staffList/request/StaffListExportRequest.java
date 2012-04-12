/**
 * 
 */
package com.chinarewards.gwt.elt.client.staffList.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author lw
 * @since 2012年2月14日 10:35:43
 */
public class StaffListExportRequest implements Action<StaffListExportResponse> {

	private StaffListCriteria criteria;
	private UserSession session;


	public StaffListExportRequest() {
	}

	public StaffListCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(StaffListCriteria criteria) {
		this.criteria = criteria;
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
	public StaffListExportRequest(StaffListCriteria criteria,UserSession session) {
		this.criteria = criteria;
		this.session=session;
	}


}
