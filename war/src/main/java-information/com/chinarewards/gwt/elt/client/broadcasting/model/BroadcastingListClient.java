package com.chinarewards.gwt.elt.client.broadcasting.model;

import java.io.Serializable;

import com.chinarewards.gwt.elt.client.staffList.model.StaffListCriteria.StaffStatus;

public class BroadcastingListClient implements Serializable, Comparable<BroadcastingListClient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4934837755724342679L;
	
	String staffNo;
	String staffName;
	String departmentName;
	String jobPosition;
	String phone;
	StaffStatus staffStatus;
	String staffId;

	
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
	public StaffStatus getStaffStatus() {
		return staffStatus;
	}
	public void setStaffStatus(StaffStatus staffStatus) {
		this.staffStatus = staffStatus;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public BroadcastingListClient() {

	}
	@Override
	public int compareTo(BroadcastingListClient o) {
		// TODO Auto-generated method stub
		return 0;
	}



}
