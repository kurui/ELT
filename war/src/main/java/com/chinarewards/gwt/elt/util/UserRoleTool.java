package com.chinarewards.gwt.elt.util;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;

public class UserRoleTool {
	public static UserRoleVo adapt(UserRole role) {
		String s = role.toString();
		return UserRoleVo.valueOf(s);
	}

	public static UserRoleVo[] adapt(List<UserRole> roles) {
		if (roles == null)
			return null;
		List<UserRoleVo> cli = new ArrayList<UserRoleVo>();
		for (UserRole role : roles) {
			UserRoleVo c = adapt(role);
			cli.add(c);
		}
		return cli.toArray(new UserRoleVo[0]);
	}
}
