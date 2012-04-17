package com.chinarewards.gwt.elt.client.staffList.model;

import java.io.Serializable;

public class ImportStaffListClient implements Serializable,
		Comparable<ImportStaffListClient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4934837755724342679L;
	private String id;
	private String staffNo;
	private String staffName;
	private String email;
	private String departmentName;
	private String jobPosition;
	private String phone;
	private String dob;
	private String Leadership;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getLeadership() {
		return Leadership;
	}

	public void setLeadership(String leadership) {
		Leadership = leadership;
	}

	public ImportStaffListClient() {

	}

	@Override
	public int compareTo(ImportStaffListClient o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
