package com.chinarewards.elt.dao.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.vo.WinnersRecordQueryVo;
import com.chinarewards.elt.util.StringUtil;

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
	@SuppressWarnings("unchecked")
	public SysUser findUserById(String id) {
		SysUser user = (SysUser) getEm().createQuery("FROM SysUser u WHERE u.id = :id").setParameter("id", id).getSingleResult();
		return user;
	}
	
	
}
