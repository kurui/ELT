/**
 * 
 */
package com.chinarewards.gwt.elt.client.broadcastSave.request;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:43
 */
public class BroadcastSaveRequest implements Action<BroadcastSaveResponse> {
	private UserSession session;
	String broadcastId;
	Date broadcastingTimeStart;
	Date broadcastingTimeEnd;
	boolean allowreplies;
	

	public UserSession getSession() {
		return session;
	}


	public void setSession(UserSession session) {
		this.session = session;
	}




	public String getBroadcastId() {
		return broadcastId;
	}


	public void setBroadcastId(String broadcastId) {
		this.broadcastId = broadcastId;
	}


	public Date getBroadcastingTimeStart() {
		return broadcastingTimeStart;
	}


	public void setBroadcastingTimeStart(Date broadcastingTimeStart) {
		this.broadcastingTimeStart = broadcastingTimeStart;
	}


	public Date getBroadcastingTimeEnd() {
		return broadcastingTimeEnd;
	}


	public void setBroadcastingTimeEnd(Date broadcastingTimeEnd) {
		this.broadcastingTimeEnd = broadcastingTimeEnd;
	}


	public boolean isAllowreplies() {
		return allowreplies;
	}


	public void setAllowreplies(boolean allowreplies) {
		this.allowreplies = allowreplies;
	}


	public BroadcastSaveRequest() {
	}

	


}
