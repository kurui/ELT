package com.chinarewards.gwt.elt.server.rewardItem;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.reward.RewardItemService;
import com.chinarewards.gwt.elt.client.rewardItem.request.DeleteRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.DeleteRewardsItemResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

public class DeleteRewardsItemHandler extends	BaseActionHandler<DeleteRewardsItemRequest, DeleteRewardsItemResponse> {

	@InjectLogger
	Logger logger;
	RewardItemService rewardItemService;

	@Inject
	public DeleteRewardsItemHandler(RewardItemService rewardItemService) {
		this.rewardItemService = rewardItemService;
	}
	@Override
	public Class<DeleteRewardsItemRequest> getActionType() {
		return DeleteRewardsItemRequest.class;
	}

	@Override
	public DeleteRewardsItemResponse execute(DeleteRewardsItemRequest action,
			ExecutionContext context) throws DispatchException {
		UserContext uc=new UserContext();
		uc.setUserId(action.getNowUserId());
		String name=rewardItemService.deleteRewardItem(uc, action.getRewardsItemId().toString());

		return new DeleteRewardsItemResponse(name);
	}



	@Override
	public void rollback(DeleteRewardsItemRequest action,
			DeleteRewardsItemResponse result, ExecutionContext context)
			throws DispatchException {

	}

}
