package com.chinarewards.gwt.elt.client.enterprise.model;

import java.io.Serializable;

public class EnterpriseClient implements Serializable,
		Comparable<EnterpriseClient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnterpriseClient() {
	}

	protected String thisAction;
	protected boolean fromMenu;

	public String getThisAction() {
		return thisAction;
	}

	public void setThisAction(String thisAction) {
		this.thisAction = thisAction;
	}

	public boolean isFromMenu() {
		return fromMenu;
	}

	public void setFromMenu(boolean fromMenu) {
		this.fromMenu = fromMenu;
	}

	@Override
	public int compareTo(EnterpriseClient o) {
		return 0;
	}

}
