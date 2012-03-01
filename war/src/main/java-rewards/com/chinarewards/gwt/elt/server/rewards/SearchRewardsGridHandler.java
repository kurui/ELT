package com.chinarewards.gwt.elt.server.rewards;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.reward.base.RewardStatus;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.reward.RewardGridService;
import com.chinarewards.gwt.elt.adapter.rewards.RewardsAdapter;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsGridRequest;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsGridResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * 加载奖励小控件
 * 
 * @author yanrui
 */
public class SearchRewardsGridHandler extends
		BaseActionHandler<SearchRewardsGridRequest, SearchRewardsGridResponse> {

	@InjectLogger
	Logger logger;

	RewardGridService rewardGridService;

	@Inject
	public SearchRewardsGridHandler(RewardGridService rewardGridService) {
		this.rewardGridService = rewardGridService;

	}

	@Override
	public SearchRewardsGridResponse execute(SearchRewardsGridRequest request,
			ExecutionContext context) throws DispatchException {

		SearchRewardsGridResponse resp = new SearchRewardsGridResponse();

//		RewardsCriteria rewards = request.getRewards();
//		rewards.setGridId(request.getSession().getGridId());
//		RewardSearchVo criteria = adapter(rewards);
		PageStore<RewardVo> rewardsPage = null;

		UserContext uc = new UserContext();
		uc.setCorporationId(request.getSession().getCorporationId());
		uc.setUserId(request.getSession().getToken());
		uc.setLoginName(request.getSession().getLoginName());
		uc.setUserRoles(UserRoleTool.adaptToRole(request.getSession()
				.getUserRoles()));

//		rewardsPage = rewardGridService.fetchRewards_ALL(uc, criteria);
		if (rewardsPage != null) {
			resp.setTotal(rewardsPage.getResultCount());
			resp.setResult(RewardsAdapter.adapter(rewardsPage.getResultList()));
		}

		return resp;
	}

	private RewardSearchVo adapter(RewardsCriteria rewards) {
		RewardSearchVo criteria = new RewardSearchVo();

		if (rewards == null) {
			return criteria;
		}
		criteria.setRewardId(rewards.getId());
		criteria.setBuilderDeptId(rewards.getBuilderDeptId());
		criteria.setAccountDeptId(rewards.getAccountDeptId());
		criteria.setRewardItemId(rewards.getRewardsItemId());
		criteria.setName(rewards.getName());
		criteria.setDefinition(rewards.getDefinition());
		if (rewards.getJudgeUserId() != null){
			criteria.setJudgeUserId(rewards.getJudgeUserId());
		}
		if (rewards.getStatus() != null){
			criteria.setStatus(RewardStatus.valueOf(rewards.getStatus()
					.toString()));
		}
		
//		 criteria.setWinnerGridId(rewards.getGridId());//获奖人
//		 criteria.setWinnerGridName(rewards.getGridName());
		 criteria.setRewardItemId(rewards.getRewardsItemId());
		 criteria.setRewardsTime(rewards.getRewardsTime());
		 
		if (rewards.getPagination() != null) {
			PaginationDetail detail = new PaginationDetail();
			detail.setLimit(rewards.getPagination().getLimit());
			detail.setStart(rewards.getPagination().getStart());

			criteria.setPaginationDetail(detail);
		}

		if (rewards.getSorting() != null) {
			SortingDetail sortingDetail = new SortingDetail();
			sortingDetail.setSort(rewards.getSorting().getSort());
			sortingDetail.setDirection(rewards.getSorting().getDirection());
			criteria.setSortingDetail(sortingDetail);
		}
		return criteria;
	}

	@Override
	public Class<SearchRewardsGridRequest> getActionType() {
		return SearchRewardsGridRequest.class;
	}

	@Override
	public void rollback(SearchRewardsGridRequest req, SearchRewardsGridResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
