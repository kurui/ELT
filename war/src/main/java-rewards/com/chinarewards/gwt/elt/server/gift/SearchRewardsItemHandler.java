///**
// * 
// */
//package com.chinarewards.gwt.elt.server.gift;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import net.customware.gwt.dispatch.server.ExecutionContext;
//import net.customware.gwt.dispatch.shared.DispatchException;
//
//import org.slf4j.Logger;
//
//import com.chinarewards.elt.model.common.PageStore;
//import com.chinarewards.elt.model.common.PaginationDetail;
//import com.chinarewards.elt.model.common.SortingDetail;
//import com.chinarewards.elt.model.reward.base.RequireAutoAward;
//import com.chinarewards.elt.model.reward.base.RequireAutoGenerate;
//import com.chinarewards.elt.model.reward.search.GiftSearchVo;
//import com.chinarewards.elt.model.reward.vo.GiftVo;
//import com.chinarewards.elt.model.user.UserContext;
//import com.chinarewards.elt.service.gift.GiftService;
//import com.chinarewards.gwt.elt.client.gift.request.SearchGiftRequest;
//import com.chinarewards.gwt.elt.client.gift.request.SearchGiftResponse;
//import com.chinarewards.gwt.elt.client.rewards.model.GiftClient;
//import com.chinarewards.gwt.elt.client.rewards.model.GiftCriteria;
//import com.chinarewards.gwt.elt.server.BaseActionHandler;
//import com.chinarewards.gwt.elt.server.logger.InjectLogger;
//import com.chinarewards.gwt.elt.util.UserRoleTool;
//import com.google.inject.Inject;
//
///**
// * @author yanrui
// * @since 
// */
//public class SearchGiftHandler extends	BaseActionHandler<SearchGiftRequest, SearchGiftResponse> {
//
//	@InjectLogger
//	Logger logger;
//	GiftService giftService;
//
//	@Inject
//	public SearchGiftHandler(GiftService giftService) {
//		this.giftService = giftService;
//	}
//	
//
////	@Override
//	public SearchGiftResponse execute(SearchGiftRequest request,
//			ExecutionContext context) throws DispatchException {
////		logger.debug(" request parameters: {}", request);
////
//		SearchGiftResponse resp = new SearchGiftResponse();
////     
////		GiftCriteria rewardsItemClient = request.getGift();
////		GiftSearchVo model = adapter(rewardsItemClient);
////		UserContext uc = new UserContext();
////		uc.setCorporationId(request.getUserSession().getCorporationId());
////		uc.setLoginName(request.getUserSession().getLoginName());
////		uc.setUserRoles(UserRoleTool.adaptToRole(request.getUserSession().getUserRoles()));
////		PageStore<GiftVo> items = giftService.fetchGifts(uc,model);
////
////		resp.setTotal(items.getResultCount());
////		resp.setResult(adapter(items.getResultList(), giftService));
////
//		return resp;
//	}
////
////	// Convert from GiftSearchCriteria to GeneratorGiftModel.
////	//从奖项查询的VO转为model的VO,主要是传查询的条件
////	private GiftSearchVo adapter(GiftCriteria criteria) {
////		GiftSearchVo model = new GiftSearchVo();
////		model.setAccountDeptName(criteria.getAccountDeptName());
////		model.setBuildDeptName(criteria.getBuildDeptName());
////		model.setCorporationId(criteria.getCorporationId());
////		model.setDefinition(criteria.getDefinition());
////		model.setDeptIds(criteria.getDeptIds());
////		model.setId(criteria.getId());
////		model.setName(criteria.getName());
////		model.setDepartmentId(criteria.getDepartmentId());
//////		model.setSubDepartmentChoose(criteria.isSubDepartmentChoose());
//////		model.setRewardFrom(criteria.getRewardFrom());
////		model.setStandard(criteria.getStandard());
////		model.setStartTime(criteria.getStartTime());
////		model.setTypeId(criteria.getTypeId());
////		model.setTypeName(criteria.getTypeName());
////
////		if (criteria.getPagination() != null) {
////			PaginationDetail paginationDetail = new PaginationDetail();
////			paginationDetail.setStart(criteria.getPagination().getStart());
////			paginationDetail.setLimit(criteria.getPagination().getLimit());
////			model.setPaginationDetail(paginationDetail);
////		}
////
////		if (criteria.getSorting() != null) {
////			SortingDetail sortingDetail = new SortingDetail();
////			sortingDetail.setSort(criteria.getSorting().getSort());
////			sortingDetail.setDirection(criteria.getSorting().getDirection());
////			model.setSortingDetail(sortingDetail);
////		}
////		return model;
////	}
////	
////	
////    //从服务端得到的数据到客户端在列表显示的数据
////	private List<GiftClient> adapter(List<GiftVo> items,GiftService rewardsItemService) {
////		List<GiftClient> resultList = new ArrayList<GiftClient>();
////
////		for (GiftVo item : items) {
////			GiftClient client = new GiftClient();
////			client.setId(item.getId());
////			client.setName(item.getName());
////			client.setAuto(item.getAutoAward() == RequireAutoAward.requireAutoAward);//自动奖
////			client.setDegree(item.getItem().getDegree());
////			client.setPeriodEnable(item.getAutoGenerate()==RequireAutoGenerate.requireCyclic);//周期性
////			client.setStartTime(item.getItem().getStartTime());
////			client.setCreateAt(item.getItem().getCreatedAt());
////			client.setNextPublishTime(item.getExpectAwardDate());
////			client.setEnabled(item.isEnabled());
////			resultList.add(client);
////		}
////
////		return resultList;
////	}
////
////	@Override
////	public Class<SearchGiftRequest> getActionType() {
////		return SearchGiftRequest.class;
////	}
////
////	@Override
////	public void rollback(SearchGiftRequest arg0,
////			SearchGiftResponse arg1, ExecutionContext arg2)
////			throws DispatchException {
////	}
//
//}
