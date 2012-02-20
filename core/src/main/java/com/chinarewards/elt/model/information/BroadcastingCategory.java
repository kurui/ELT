package com.chinarewards.elt.model.information;


/**
 * The status of account.
 * 
 * @author nicho
 * @since 2012年2月14日 14:15:45
 * 
 */
public enum BroadcastingCategory {
	/* 公司广播 */
	COMPANYBROADCAST("公司广播"),

	/*其他广播 */
	OTHERBROADCAST("其他广播");



	private final String displayName;

	BroadcastingCategory(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
