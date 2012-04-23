package com.chinarewards.gwt.elt.client.gift.model;

import java.io.Serializable;

public class ImportGiftListClient implements Serializable,
		Comparable<ImportGiftListClient> {

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
	private Integer importfal;
	
	public Integer getImportfal() {
		return importfal;
	}

	public void setImportfal(Integer importfal) {
		this.importfal = importfal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGiftNo() {
		return staffNo;
	}

	public void setGiftNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getGiftName() {
		return staffName;
	}

	public void setGiftName(String staffName) {
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

	public ImportGiftListClient() {

	}

	@Override
	public int compareTo(ImportGiftListClient o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
