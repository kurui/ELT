package com.chinarewards.gwt.elt.client.integralManagement.model;

import java.io.Serializable;

public class LetterCount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 String DepartmentsName;
	 String budgetpoints;
	 String hasawardedpoints;
	 String departmentId;
	 int count;
	public LetterCount() {
	}

	public LetterCount(String DepartmentsName,
			String budgetpoints, String hasawardedpoints, String departmentId) {
		this.DepartmentsName = DepartmentsName;
		this.budgetpoints = budgetpoints;
		this.hasawardedpoints = hasawardedpoints;
		this.departmentId = departmentId;
	}


	public String getDepartmentsName() {
		return DepartmentsName;
	}

	public void setDepartmentsName(String departmentsName) {
		DepartmentsName = departmentsName;
	}

	public String getBudgetpoints() {
		return budgetpoints;
	}

	public void setBudgetpoints(String budgetpoints) {
		this.budgetpoints = budgetpoints;
	}

	public String getHasawardedpoints() {
		return hasawardedpoints;
	}

	public void setHasawardedpoints(String hasawardedpoints) {
		this.hasawardedpoints = hasawardedpoints;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}
