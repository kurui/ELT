package com.chinarewards.elt.model.gift;

import java.util.Date;
import java.util.List;

import com.chinarewards.elt.model.gift.search.GiftStatus;
import com.chinarewards.elt.model.user.UserRole;

public class GiftProcess {

	String GiftId;
	String GiftNo;
	String GiftName;
	String departmentId;
	String photo;
	String jobPosition;
	String leadership;
	String phone;
	String email;
	Date dob;
	GiftStatus status;
	String corpId;
	String userName;
	String password;
	private List<UserRole> UserRoleVos;

	public List<UserRole> getUserRoleVos() {
		return UserRoleVos;
	}


	public void setUserRoleVos(List<UserRole> userRoleVos) {
		UserRoleVos = userRoleVos;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getGiftId() {
		return GiftId;
	}
	public void setGiftId(String GiftId) {
		this.GiftId = GiftId;
	}
	public String getGiftNo() {
		return GiftNo;
	}
	public void setGiftNo(String GiftNo) {
		this.GiftNo = GiftNo;
	}
	public String getGiftName() {
		return GiftName;
	}
	public void setGiftName(String GiftName) {
		this.GiftName = GiftName;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	public String getLeadership() {
		return leadership;
	}
	public void setLeadership(String leadership) {
		this.leadership = leadership;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public GiftStatus getStatus() {
		return status;
	}
	public void setStatus(GiftStatus status) {
		this.status = status;
	}
	
}
