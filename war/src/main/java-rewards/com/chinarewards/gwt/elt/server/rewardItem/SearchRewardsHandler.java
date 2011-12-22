package com.chinarewards.gwt.elt.server.rewardItem;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.gwt.elt.adapter.rewards.RewardsAdapter;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author Cream
 * @since 0.2.0 2010-12-27
 */
public class SearchRewardsHandler extends
		BaseActionHandler<SearchRewardsRequest, SearchRewardsResponse> {

	@InjectLogger
	Logger logger;

	RewardService rewardService;

	@Inject
	public SearchRewardsHandler(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@Override
	public SearchRewardsResponse execute(SearchRewardsRequest request,
			ExecutionContext context) throws DispatchException {

		SearchRewardsResponse resp = new SearchRewardsResponse();

		RewardsCriteria rewards = request.getRewards();
		RewardSearchVo criteria = adapter(rewards);
		PageStore<RewardVo> rewardsPage = null;

		// 模拟一条用户数据------------------
		UserContext uc = new UserContext();
		uc.setCorporationId("8a83834534544f870134544f8bdc0006");
		UserRole[] ur = new UserRole[1];
		ur[0] = UserRole.CORP_ADMIN;
		uc.setUserRoles(ur);
		// ---------------------------------------
		rewardsPage = rewardService.fetchRewards(uc, criteria);
		resp.setTotal(rewardsPage.getResultCount());
		resp.setResult(RewardsAdapter.adapter(rewardsPage.getResultList()));

		return resp;
	}

	private RewardSearchVo adapter(RewardsCriteria rewards) {

		RewardSearchVo criteria = new RewardSearchVo();

		if (rewards == null) {
			return criteria;
		}
		criteria.setRewardId(rewards.getId());
		criteria.setBuilderDeptId(rewards.getBuilderDeptId());
		// criteria.setSubDepartmentChoose(rewards.isSubDepartmentChoose());
		criteria.setAccountDeptId(rewards.getAccountDeptId());
		criteria.setRewardItemId(rewards.getRewardsItemId());
		criteria.setName(rewards.getName());
		// criteria.setStaffId(rewards.getStaffId());
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
	public Class<SearchRewardsRequest> getActionType() {
		return SearchRewardsRequest.class;
	}

	@Override
	public void rollback(SearchRewardsRequest req, SearchRewardsResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
