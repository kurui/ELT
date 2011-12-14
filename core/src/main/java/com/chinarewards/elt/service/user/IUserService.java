package com.chinarewards.elt.service.user;

import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.gwt.elt.model.staff.StaffUserProcess;

public interface IUserService {

	public SysUser authenticate(String userName, String password);
	public String createUser(StaffUserProcess staffUserProcess);
}
