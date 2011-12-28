package com.chinarewards.elt.service.user;

import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.user.UserSessionVo;
import com.chinarewards.elt.model.user.UserVo;

/**
 * The logic to operate {@link SysUser}.
 * 
 * @author yanxin
 * @since 1.0
 */
public interface UserLogic {

	/**
	 * Get the default user to do something which not require user login. Maybe
	 * it run by system automatic.
	 * 
	 * @return
	 */
	public SysUser getDefaultUser();
	
	/**
	 * 获取用户时.传入固定员工ID
	 * @param staff
	 * @return
	 */
	public SysUser getDefaultUserByStaff(Staff staff);

	/**
	 * Create a {@link SysUser}
	 * 
	 * @param caller
	 * @param user
	 * @return
	 */
	public SysUser createUser(SysUser caller, UserVo user);
	
	/**
	 * find User By   Id
	 * @return
	 */
	public SysUser findUserById(String id);
	/**
	 * find User By   name and pwd
	 * @return
	 */
	public UserSessionVo findUserByNameAndPwd(String userName,String pwd);
	
	/**
	 * find User By   Id
	 * @return
	 */
	public UserSessionVo tokenVaild(String token);
}
