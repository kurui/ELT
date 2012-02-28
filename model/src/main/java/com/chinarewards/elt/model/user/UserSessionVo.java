package com.chinarewards.elt.model.user;

import java.util.List;

public class UserSessionVo {
	private String id;
	private String username;
	private String corporationId;
	private String departmentId;
	private String staffId;
	private UserRole lastLoginRole;
	private String photo;
	private String corporationName;

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public UserRole getLastLoginRole() {
		return lastLoginRole;
	}

	public void setLastLoginRole(UserRole lastLoginRole) {
		this.lastLoginRole = lastLoginRole;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	private List<UserRole> userRoles;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
