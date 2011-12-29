package com.chinarewards.gwt.elt.client.support;

import java.io.Serializable;

import com.chinarewards.gwt.elt.model.user.UserRoleVo;

public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -359488248052873568L;

	String token;
	String loginName;
	String corporationId;
	String departmentId;
	UserRoleVo[] userRoles;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public UserRoleVo[] getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(UserRoleVo[] userRoles) {
		this.userRoles = userRoles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
