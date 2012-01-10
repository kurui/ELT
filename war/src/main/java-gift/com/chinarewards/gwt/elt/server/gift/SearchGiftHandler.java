package com.chinarewards.gwt.elt.server.gift;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author Cream
 * @since 0.2.0 2010-12-27
 */
public class SearchGiftHandler extends
		BaseActionHandler<SearchGiftRequest, SearchGiftResponse> {

	@InjectLogger
	Logger logger;

	RewardService rewardService;

	@Inject
	public SearchGiftHandler(RewardService rewardService) {
		this.rewardService = rewardService;

	}

	@Override
	public SearchGiftResponse execute(SearchGiftRequest request,
			ExecutionContext context) throws DispatchException {

		SearchGiftResponse resp = new SearchGiftResponse();

//		GiftCriteria rewards = request.getGift();
//		RewardSearchVo criteria = adapter(rewards);
//		PageStore<RewardVo> rewardsPage = null;
//
//		UserContext uc = new UserContext();
//		uc.setCorporationId(request.getCorporationId());
//		uc.setUserRoles(UserRoleTool.adaptToRole(request.getUserRoles()));
//
//		rewardsPage = rewardService.fetchGift(uc, criteria);
//		resp.setTotal(rewardsPage.getResultCount());
//		resp.setResult(GiftAdapter.adapter(rewardsPage.getResultList()));

		return resp;
	}
//
//	private RewardSearchVo adapter(GiftCriteria rewards) {
//
//		RewardSearchVo criteria = new RewardSearchVo();
//
//		if (rewards == null) {
//			return criteria;
//		}
//		criteria.setRewardId(rewards.getId());
//		criteria.setName(rewards.getName());
//
//		if (rewards.getStatus() != null)
//			criteria.setStatus(RewardStatus.valueOf(rewards.getStatus().toString()));
//
//		
//		if (rewards.getPagination() != null) {
//			PaginationDetail detail = new PaginationDetail();
//			detail.setLimit(rewards.getPagination().getLimit());
//			detail.setStart(rewards.getPagination().getStart());
//
//			criteria.setPaginationDetail(detail);
//		}
//
//		if (rewards.getSorting() != null) {
//			SortingDetail sortingDetail = new SortingDetail();
//			sortingDetail.setSort(rewards.getSorting().getSort());
//			sortingDetail.setDirection(rewards.getSorting().getDirection());
//			criteria.setSortingDetail(sortingDetail);
//		}
//		return criteria;
//	}

	@Override
	public Class<SearchGiftRequest> getActionType() {
		return SearchGiftRequest.class;
	}

	@Override
	public void rollback(SearchGiftRequest req, SearchGiftResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
