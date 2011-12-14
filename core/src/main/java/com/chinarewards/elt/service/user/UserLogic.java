package com.chinarewards.elt.service.user;

import com.chinarewards.elt.domain.user.SysUser;
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
	 * Create a {@link SysUser}
	 * 
	 * @param caller
	 * @param user
	 * @return
	 */
	public SysUser createUser(SysUser caller, UserVo user);
}
