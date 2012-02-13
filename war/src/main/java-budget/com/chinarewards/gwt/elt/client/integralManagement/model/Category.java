package com.chinarewards.gwt.elt.client.integralManagement.model;

import java.io.Serializable;

public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String displayName;
	private String budgetpoints;
	private String hasawardedpoints;
	private String departmentId;
	public Category()
	{}
	public Category(String displayName, String budgetpoints,
			String hasawardedpoints, String departmentId) {
		this.displayName = displayName;
		this.budgetpoints = budgetpoints;
		this.hasawardedpoints = hasawardedpoints;
		this.departmentId = departmentId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getBudgetpoints() {
		return budgetpoints;
	}

	public String getHasawardedpoints() {
		return hasawardedpoints;
	}

	public String getDepartmentId() {
		return departmentId;
	}
}
