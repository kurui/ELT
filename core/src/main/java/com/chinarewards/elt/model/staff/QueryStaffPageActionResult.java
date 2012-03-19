package com.chinarewards.elt.model.staff;

import java.util.List;

import com.chinarewards.elt.domain.org.Staff;

public class QueryStaffPageActionResult {

	private int total;

	private List<Staff> resultList;

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the resultList
	 */
	public List<Staff> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList
	 *            the resultList to set
	 */
	public void setResultList(List<Staff> resultList) {
		this.resultList = resultList;
	}

}