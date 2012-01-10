/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.request;

import java.util.List;

import com.chinarewards.gwt.elt.client.gift.module.GiftClient;
import com.chinarewards.gwt.elt.client.gift.request.AddGiftResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.support.UserSession;

import net.customware.gwt.dispatch.shared.Action;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 */
public class AddGiftRequest implements Action<AddGiftResponse> {

	String giftId;
	String nowUserId;
	private GiftClient gift;
	private UserSession userSession;

	List<String> staffIds;

	public AddGiftRequest(GiftClient gift, UserSession userSession) {
		this.gift = gift;
		this.userSession = userSession;
	}

	/**
	 * For serialization
	 */
	public AddGiftRequest() {
	}

	public AddGiftRequest(List<String> staffIds, String giftId, String nowUserId) {
		this.staffIds = staffIds;
		this.nowUserId = nowUserId;
		this.giftId = giftId;
	}

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public GiftClient getGift() {
		return gift;
	}

	public void setGift(GiftClient gift) {
		this.gift = gift;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	public String getRewardId() {
		return giftId;
	}

	public void setRewardId(String giftId) {
		this.giftId = giftId;
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

}
