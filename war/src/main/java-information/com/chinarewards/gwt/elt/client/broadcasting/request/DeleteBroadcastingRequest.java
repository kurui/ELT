/**
 * 
 */
package com.chinarewards.gwt.elt.client.broadcasting.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author nicho
 * @since 2012年2月14日 10:35:43
 */
public class DeleteBroadcastingRequest implements Action<DeleteBroadcastingResponse> {

	private String broadcastingId;
	private UserSession session;


	public DeleteBroadcastingRequest() {
	}



	public String getBroadcastingId() {
		return broadcastingId;
	}



	public void setBroadcastingId(String broadcastingId) {
		this.broadcastingId = broadcastingId;
	}



	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	/**
	 * 
	 * @param BroadcastingListVo
	 */
	public DeleteBroadcastingRequest(String broadcastingId,UserSession session) {
		this.broadcastingId = broadcastingId;
		this.session=session;
	}


}
