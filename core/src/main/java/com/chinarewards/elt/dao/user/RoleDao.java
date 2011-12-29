package com.chinarewards.elt.dao.user;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.user.SysRole;
import com.chinarewards.elt.model.user.UserRole;

public class RoleDao extends BaseDao<SysRole> {


	public SysRole findRoleByRoleName(UserRole roleName) {
		return (SysRole) getEm()
				.createQuery("FROM SysRole u WHERE u.name = :roleName")
				.setParameter("roleName", roleName).getSingleResult();
	}
	


	

	
}
