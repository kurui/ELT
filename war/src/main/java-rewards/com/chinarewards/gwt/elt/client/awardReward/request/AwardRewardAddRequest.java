/**
 * 
 */
package com.chinarewards.gwt.elt.client.awardReward.request;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

/**
 * An action which perform request to search user.
 * 
 * @author nicho
 * @since 2011年12月12日
 */
public class AwardRewardAddRequest implements Action<AwardRewardAddResponse> {

	List<String> staffIds;

	String rewardId;
	String nowUserId;
	String fal;
	private boolean emailBox;
	private boolean messageBox;
	private List<String> messageStaffId;
	
	public boolean isEmailBox() {
		return emailBox;
	}

	public void setEmailBox(boolean emailBox) {
		this.emailBox = emailBox;
	}

	public boolean isMessageBox() {
		return messageBox;
	}

	public void setMessageBox(boolean messageBox) {
		this.messageBox = messageBox;
	}

	public List<String> getMessageStaffId() {
		return messageStaffId;
	}

	public void setMessageStaffId(List<String> messageStaffId) {
		this.messageStaffId = messageStaffId;
	}

	public String getFal() {
		return fal;
	}

	public void setFal(String fal) {
		this.fal = fal;
	}

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	/**
	 * For serialization
	 */
	public AwardRewardAddRequest() {
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

	public AwardRewardAddRequest(List<String> staffIds, String rewardId,
			String nowUserId, String fal) {
		this.staffIds = staffIds;
		this.nowUserId = nowUserId;
		this.rewardId = rewardId;
		this.fal = fal;
	}

}
