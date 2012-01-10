/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.model.gift.GiftCriteria;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;

/**
 * @author nicho
 * @since 2012年1月9日 19:00:40
 */
public class SearchGiftRequest implements Action<SearchGiftResponse> {

	private GiftCriteria gift;
	private String corporationId;
	private UserRoleVo[] userRoles;


	public SearchGiftRequest() {
	}

	public SearchGiftRequest(GiftCriteria criteria,String corporationId,UserRoleVo[] userRoles) {
		this.gift = criteria;
		this.corporationId=corporationId;
		this.userRoles=userRoles;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public UserRoleVo[] getUserRoles() {
		return userRoles;
	}

	@Override
	public String toString() {
		return "SearchGiftRequest [gift=" + gift + "]";
	}

	// ---- getter ----
	/**
	 * @return the rewards
	 */
	public GiftCriteria getGift() {
		return gift;
	}

}
