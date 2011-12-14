package com.chinarewards.elt.dao.user;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.user.SysUser;

public class UserDao extends BaseDao<SysUser> {

	@SuppressWarnings("unchecked")
	public List<SysUser> findUserByUserName(String userName) {
		return getEm()
				.createQuery("FROM SysUser u WHERE u.userName = :userName")
				.setParameter("userName", userName).getResultList();
	}
	public String createUser(SysUser user) {
		this.save(user);	
		return user.getId();
	}
}
