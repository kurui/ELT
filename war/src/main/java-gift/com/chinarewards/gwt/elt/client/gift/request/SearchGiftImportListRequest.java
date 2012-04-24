package com.chinarewards.gwt.elt.client.gift.request;

import net.customware.gwt.dispatch.shared.Action;

import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListCriteria;
import com.chinarewards.gwt.elt.client.support.UserSession;

/**
 * An action which perform request to search user.
 * 
 * @author yanrui
 * @since 1.5.2
 */
public class SearchGiftImportListRequest implements Action<SearchGiftImportListResponse> {

	private ImportGiftListCriteria criteria;
	private UserSession session;


	public SearchGiftImportListRequest() {
	}

	public ImportGiftListCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(ImportGiftListCriteria criteria) {
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
	 * @param GiftListVo
	 */
	public SearchGiftImportListRequest(ImportGiftListCriteria criteria,UserSession session) {
		this.criteria = criteria;
		this.session=session;
	}


}
