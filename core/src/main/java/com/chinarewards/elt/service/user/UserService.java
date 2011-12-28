package com.chinarewards.elt.service.user;

import com.chinarewards.elt.model.user.UserSearchCriteria;
import com.chinarewards.elt.model.user.UserSearchResult;
import com.chinarewards.elt.model.user.UserSessionVo;

public interface UserService {
	public UserSessionVo authenticate(String userName,String pwd);
	public UserSessionVo tokenVaild(String token);
	/**
	 * Search user by paging.
	 * 
	 * @param criteria
	 * @return
	 */
	public UserSearchResult searchHrAdminUserPaging(UserSearchCriteria criteria);
}
