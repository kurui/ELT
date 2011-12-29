package com.chinarewards.gwt.elt.client.login;

import com.chinarewards.gwt.elt.model.user.UserRoleVo;

import net.customware.gwt.dispatch.shared.Result;

public class TokenValidResponse implements Result {
	private String token;
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

	public TokenValidResponse() {

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
