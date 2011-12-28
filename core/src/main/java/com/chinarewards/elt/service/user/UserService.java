package com.chinarewards.elt.service.user;

import com.chinarewards.elt.model.user.UserSessionVo;

public interface UserService {
	public UserSessionVo authenticate(String userName,String pwd);
	public UserSessionVo tokenVaild(String token);
	
}
