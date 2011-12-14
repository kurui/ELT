package com.chinarewards.elt.service.helper;

import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.service.user.UserLogic;
import com.google.inject.Injector;

/**
 * Help us get a useful {@link SysUser}.
 * 
 * @author yanxin
 * @since 1.0
 */
public class UserHelper {

	private static SysUser defaultUser = null;

	/**
	 * Get the default user.
	 * 
	 * @return
	 */
	public static SysUser getDefaultUser(Injector injector) {
		if (defaultUser != null)
			return defaultUser;
		// require some services
		UserLogic userLogic = injector.getInstance(UserLogic.class);
		defaultUser = userLogic.getDefaultUser();

		return defaultUser;
	}
}
