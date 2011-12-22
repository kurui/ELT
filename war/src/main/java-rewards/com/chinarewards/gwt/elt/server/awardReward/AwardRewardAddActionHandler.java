package com.chinarewards.gwt.elt.server.awardReward;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the NominateAddActionHandler
 * 
 * @author nicho
 * @since 2011年12月21日 16:12:47
 */
public class AwardRewardAddActionHandler extends
		BaseActionHandler<AwardRewardAddRequest, AwardRewardAddResponse> {

	@InjectLogger
	Logger logger;

	RewardService rewardService;

	@Inject
	public AwardRewardAddActionHandler(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@Override
	public AwardRewardAddResponse execute(AwardRewardAddRequest request,
			ExecutionContext response) throws DispatchException {
		AwardRewardAddResponse awardresponse = new AwardRewardAddResponse();

		String lot = rewardService.awardReward(null, request.getRewardId(),
				request.getStaffIds());
		awardresponse.setLotId(lot);

		return awardresponse;
	}

	@Override
	public Class<AwardRewardAddRequest> getActionType() {
		return AwardRewardAddRequest.class;
	}

	@Override
	public void rollback(AwardRewardAddRequest request,
			AwardRewardAddResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
