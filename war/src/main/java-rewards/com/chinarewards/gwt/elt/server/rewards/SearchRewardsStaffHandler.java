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
import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.gwt.elt.adapter.rewards.RewardsAdapter;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsStaffRequest;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsStaffResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class SearchRewardsStaffHandler extends
		BaseActionHandler<SearchRewardsStaffRequest, SearchRewardsStaffResponse> {

	@InjectLogger
	Logger logger;

	RewardService rewardService;

	@Inject
	public SearchRewardsStaffHandler(RewardService rewardService) {
		this.rewardService = rewardService;

	}

	@Override
	public SearchRewardsStaffResponse execute(SearchRewardsStaffRequest request,
			ExecutionContext context) throws DispatchException {

		SearchRewardsStaffResponse resp = new SearchRewardsStaffResponse();

		RewardsCriteria rewards = request.getRewards();
		rewards.setStaffId(request.getSession().getStaffId());
		RewardSearchVo criteria = adapter(rewards);
		PageStore<RewardVo> rewardsPage = null;

		UserContext uc = new UserContext();
		uc.setCorporationId(request.getSession().getCorporationId());
		uc.setUserId(request.getSession().getToken());
		uc.setLoginName(request.getSession().getLoginName());
		uc.setUserRoles(UserRoleTool.adaptToRole(request.getSession()
				.getUserRoles()));

		rewardsPage = rewardService.fetchRewardsStaff(uc, criteria);
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
		
		 criteria.setWinnerStaffId(rewards.getStaffId());//获奖人
		 criteria.setWinnerStaffName(rewards.getStaffName());
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
	public Class<SearchRewardsStaffRequest> getActionType() {
		return SearchRewardsStaffRequest.class;
	}

	@Override
	public void rollback(SearchRewardsStaffRequest req, SearchRewardsStaffResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
