package com.chinarewards.gwt.elt.client.server.user;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.gwt.elt.client.user.UserSearchRequest;
import com.chinarewards.gwt.elt.client.user.UserSearchResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;

/**
 * The handler to correspond the UserSearchRequest.
 * 
 * @author yanxin
 * @since 1.0.0 2010-09-26
 */
public class UserSearchActionHandler extends
		BaseActionHandler<UserSearchRequest, UserSearchResponse> {

	@InjectLogger
	Logger logger;

	@Override
	public UserSearchResponse execute(UserSearchRequest request,
			ExecutionContext response) throws DispatchException {
		// FIXME implement this method

		return new UserSearchResponse();
	}

	// private UserVo adapterUserVo(HrUser user) {
	// UserVo vo = new UserVo();
	// vo.setCreatedAt(user.getCreatedAt());
	// vo.setEmail(user.getEmail());
	// vo.setEnterpriseId(user.getCorporation().getCorporationId());
	// vo.setId(user.getId());
	// vo.setMobile(user.getMobile());
	// vo.setName(user.getLoginName());
	// vo.setStatus(user.getStatus() == null ? "" : user.getStatus()
	// .getDisplayName());
	// return vo;
	// }

	@Override
	public Class<UserSearchRequest> getActionType() {
		return UserSearchRequest.class;
	}

	@Override
	public void rollback(UserSearchRequest request,
			UserSearchResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
