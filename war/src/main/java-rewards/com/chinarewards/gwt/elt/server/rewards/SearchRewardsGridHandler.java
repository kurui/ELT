package com.chinarewards.gwt.elt.server.rewards;

import java.util.ArrayList;
import java.util.List;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.slf4j.Logger;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.search.RewardGridSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardGridVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.reward.RewardGridService;
import com.chinarewards.elt.util.StringUtil;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsGridClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsGridCriteria;
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

		RewardsGridCriteria criteria = request.getCriteria();

		UserContext uc = new UserContext();
		uc.setCorporationId(request.getSession().getCorporationId());
		uc.setUserId(request.getSession().getToken());
		uc.setLoginName(request.getSession().getLoginName());
		uc.setUserRoles(UserRoleTool.adaptToRole(request.getSession()
				.getUserRoles()));

		PageStore<RewardGridVo> rewardsPage = executeQuery(uc, criteria);

		if (rewardsPage != null) {
			resp.setTotal(rewardsPage.getResultCount());
			resp.setResult(adapter(rewardsPage.getResultList()));
		}

		return resp;
	}

	private PageStore<RewardGridVo> executeQuery(UserContext uc,
			RewardsGridCriteria criteria) {
		PageStore<RewardGridVo> rewardsPage = null;

		RewardGridSearchVo searchVo = adapterQuery(criteria);

		String thisAction = searchVo.getThisAction();

		if (StringUtil.isEmptyString(thisAction)) {
			rewardsPage = new PageStore<RewardGridVo>();
		} else {
			if ("Rewards_STAFF".equals(thisAction)) {
				rewardsPage = rewardGridService
						.fetchRewards_STAFF(uc, searchVo);
			} else if ("Rewards_ALL".equals(thisAction)) {
				rewardsPage = rewardGridService.fetchRewards_ALL(uc, searchVo);
			} else if ("RewardsItem_STAFF".equals(thisAction)) {
				rewardsPage = rewardGridService.fetchRewardsItem_STAFF(uc,
						searchVo);
			} else if ("RewardsItem_ALL".equals(thisAction)) {
				rewardsPage = rewardGridService.fetchRewardsItem_ALL(uc,
						searchVo);
			}
		}

		return rewardsPage;
	}

	public static List<RewardsGridClient> adapter(List<RewardGridVo> rewardsList) {
		if (null == rewardsList) {
			return null;
		}

		List<RewardsGridClient> clientList = new ArrayList<RewardsGridClient>();

		for (int i = 0; i < rewardsList.size(); i++) {
			RewardGridVo rewardGridVo = rewardsList.get(i);
			if (rewardGridVo != null) {
				RewardsGridClient client = new RewardsGridClient();
				client.setRewardsId(rewardGridVo.getRewardId());
				client.setRewardsName(rewardGridVo.getRewardName());
				client.setRewardsItemId(rewardGridVo.getRewardItemId());
				client.setRewardsItemName(rewardGridVo.getRewardItemName());
				client.setAwardAmt(rewardGridVo.getAwardAmt() + "");

				// client.setCorporationId(rewardGridVo.getCorporationId());

				clientList.add(client);
			}
		}
		return clientList;
	}

	public static RewardGridSearchVo adapterQuery(RewardsGridCriteria criteria) {
		RewardGridSearchVo searchVo = new RewardGridSearchVo();
		
		searchVo.setThisAction(criteria.getThisAction());
		
		searchVo.setCorporationId(criteria.getCorporationId());
		searchVo.setStaffId(criteria.getStaffId());
		

		return searchVo;
	}

	@Override
	public Class<SearchRewardsGridRequest> getActionType() {
		return SearchRewardsGridRequest.class;
	}

	@Override
	public void rollback(SearchRewardsGridRequest req,
			SearchRewardsGridResponse resp, ExecutionContext cxt)
			throws DispatchException {
	}

}
