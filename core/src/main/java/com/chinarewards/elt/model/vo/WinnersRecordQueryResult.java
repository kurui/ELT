package com.chinarewards.elt.model.vo;

import java.util.Date;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.staff.Staff;

public class WinnersRecordQueryResult {

	private Staff staff;

	private Department department;

	private int count;

	private Date lastWinnerTime;

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getLastWinnerTime() {
		return lastWinnerTime;
	}

	public void setLastWinnerTime(Date lastWinnerTime) {
		this.lastWinnerTime = lastWinnerTime;
	}

}
