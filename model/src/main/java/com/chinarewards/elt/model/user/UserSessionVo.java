package com.chinarewards.elt.model.user;

import java.util.List;

public class UserSessionVo {
	private String id;
	private String username;
	private String corporationId;
	private List<UserRole> userRoles;
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
