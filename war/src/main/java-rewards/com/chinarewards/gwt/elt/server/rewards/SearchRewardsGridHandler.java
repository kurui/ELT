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
			List<RewardsGridClient> clientList=adapter(rewardsPage.getResultList());
			resp.setResult(clientList);
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
	
	
	
//	// Convert from RewardsItemSearchCriteria to GeneratorRewardsItemModel.
//		//从奖项查询的VO转为model的VO,主要是传查询的条件
//		private RewardItemSearchVo adapter(RewardsItemStaffCriteria criteria) {
//			RewardItemSearchVo model = new RewardItemSearchVo();
//			model.setAccountDeptName(criteria.getAccountDeptName());
//			model.setBuildDeptName(criteria.getBuildDeptName());
//			model.setCorporationId(criteria.getCorporationId());
//			model.setDefinition(criteria.getDefinition());
//			model.setDeptIds(criteria.getDeptIds());
//			model.setId(criteria.getId());
//			model.setName(criteria.getName());
//			model.setDepartmentId(criteria.getDepartmentId());
//	        model.setEnabled(criteria.isEnabled());
//			model.setCreateTime(criteria.getCreateTime());
//			model.setCreateTimeEnd(criteria.getCreateTimeEnd());
//			model.setStandard(criteria.getStandard());
//			model.setStartTime(criteria.getStartTime());
//			model.setTypeId(criteria.getTypeId());
//			model.setTypeName(criteria.getTypeName());
//			
//			model.setStaffId(criteria.getStaffId());
//
//			if (criteria.getPagination() != null) {
//				PaginationDetail paginationDetail = new PaginationDetail();
//				paginationDetail.setStart(criteria.getPagination().getStart());
//				paginationDetail.setLimit(criteria.getPagination().getLimit());
//				model.setPaginationDetail(paginationDetail);
//			}
//
//			if (criteria.getSorting() != null) {
//				SortingDetail sortingDetail = new SortingDetail();
//				sortingDetail.setSort(criteria.getSorting().getSort());
//				sortingDetail.setDirection(criteria.getSorting().getDirection());
//				model.setSortingDetail(sortingDetail);
//			}
//			return model;
//		}
//	    //从服务端得到的数据到客户端在列表显示的数据
//		private List<RewardsItemStaffClient> adapter(List<RewardItemVo> items,RewardItemService rewardsItemService) {
//			List<RewardsItemStaffClient> resultList = new ArrayList<RewardsItemStaffClient>();
//
//			for (RewardItemVo item : items) {
//				RewardsItemStaffClient client = new RewardsItemStaffClient();
//				client.setId(item.getId());
//				client.setName(item.getName());
//				client.setAuto(item.getAutoAward() == RequireAutoAward.requireAutoAward);//自动奖
//				client.setDegree(item.getItem().getDegree());
//				client.setPeriodEnable(item.getAutoGenerate()==RequireAutoGenerate.requireCyclic);//周期性
//				client.setStartTime(item.getItem().getStartTime());
//				client.setCreateAt(item.getItem().getCreatedAt());
//				client.setCreatedBy(item.getCreatedBy().getUserName());
//				client.setNextPublishTime(item.getExpectAwardDate());
//				client.setEnabled(item.isEnabled());
//				
//				client.setAwardAmt(item.getAwardAmt());
////				client.setNominateName(item.getNominateName());
//				// 提名人员
//				List<Judge> judges = item.getJudgeList();
//				ParticipateInfoClient participate = null;
//				List<OrganicationClient> orgs = getOrgsFromJudges(judges);
//				participate = new SomeoneClient(orgs);
//				client.setTmInfo(participate);
//				
//				client.setNominateCount(item.getNominateCount());
//				resultList.add(client);
//			}
//
//			return resultList;
//		}
//		
//		private List<OrganicationClient> getOrgsFromJudges(List<Judge> judge) {
//			List<OrganicationClient> orgs = new ArrayList<OrganicationClient>();
//			for (Judge p : judge) {
//				orgs.add(new OrganicationClient(p.getStaff().getId(), p.getStaff().getName()));
//			}
//			return orgs;
//		}

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
