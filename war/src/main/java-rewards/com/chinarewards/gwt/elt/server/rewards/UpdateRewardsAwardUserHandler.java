package com.chinarewards.gwt.elt.server.rewards;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.gwt.elt.client.rewards.request.UpdateRewardsAwardUserRequest;
import com.chinarewards.gwt.elt.client.rewards.request.UpdateRewardsAwardUserResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author Cream
 * @since 0.2.0 2010-12-27
 */
public class UpdateRewardsAwardUserHandler extends
		BaseActionHandler<UpdateRewardsAwardUserRequest, UpdateRewardsAwardUserResponse> {

	@InjectLogger
	Logger logger;

	RewardService rewardService;

	@Inject
	public UpdateRewardsAwardUserHandler(RewardService rewardService) {
		this.rewardService = rewardService;

	}

	@Override
	public UpdateRewardsAwardUserResponse execute(UpdateRewardsAwardUserRequest request,
			ExecutionContext context) throws DispatchException {

		UserContext uc = new UserContext();
		uc.setUserId(request.getNowUserId());

		String rewardid = rewardService.updateRewardAwardUser(request.getRewardId(), uc, request.getUpdateStaffId());

		return new UpdateRewardsAwardUserResponse(rewardid);
	}

	@Override
	public Class<UpdateRewardsAwardUserRequest> getActionType() {
		return UpdateRewardsAwardUserRequest.class;
	}

	@Override
	public void rollback(UpdateRewardsAwardUserRequest req, UpdateRewardsAwardUserResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
