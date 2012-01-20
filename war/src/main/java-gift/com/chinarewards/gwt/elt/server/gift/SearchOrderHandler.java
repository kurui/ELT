package com.chinarewards.gwt.elt.server.gift;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.model.order.search.OrderVo;
import com.chinarewards.elt.model.reward.base.RequireAutoAward;
import com.chinarewards.elt.model.reward.base.RequireAutoGenerate;
import com.chinarewards.elt.model.reward.vo.RewardItemVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.elt.service.reward.RewardItemService;
import com.chinarewards.gwt.elt.client.gift.model.OrderSearchVo;
import com.chinarewards.gwt.elt.client.gift.request.SearchOrderRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchOrderResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author lw
 * @since 2012年1月10日 16:09:07
 */
public class SearchOrderHandler extends
		BaseActionHandler<SearchOrderRequest, SearchOrderResponse> {

	@InjectLogger
	Logger logger;

	OrderService orderService;

	@Inject
	public SearchOrderHandler(OrderService orderService) {
		this.orderService = orderService;

	}

	@Override
	public SearchOrderResponse execute(SearchOrderRequest request,
			ExecutionContext context) throws DispatchException {

		SearchOrderResponse resp = new SearchOrderResponse();

		OrderSearchVo orderSeacherVo = request.getOrderSearchVo();
		OrderVo orderVo = adapter(orderSeacherVo);
		PageStore<OrderVo> orderPage = null;

		UserContext uc = new UserContext();
		uc.setCorporationId(request.getUserSession().getCorporationId());
		uc.setLoginName(request.getUserSession().getLoginName());
		uc.setUserRoles(UserRoleTool.adaptToRole(request.getUserSession().getUserRoles()));
		uc.setUserId(request.getUserSession().getToken());
		orderPage = orderService.OrderList(uc, orderVo);
		resp.setTotal(orderPage.getResultCount());
		resp.setResult(adapterToClient(orderPage.getResultList()));

		return resp;
	}

	private OrderVo adapter(OrderSearchVo criteria) {
		OrderVo vo = new OrderVo();
		if (criteria.getName() != null) {
			vo.setName(criteria.getName());
		}
		if (criteria.getStatus() != null) {
			vo.setStatus(OrderStatus.valueOf(criteria.getStatus().toString()));
		}
		if (criteria.getPaginationDetail() != null) {
			PaginationDetail detail = new PaginationDetail();
			detail.setLimit(criteria.getPaginationDetail().getLimit());
			detail.setStart(criteria.getPaginationDetail().getStart());

			vo.setPaginationDetail(detail);
		}

		if (criteria.getSortingDetail() != null) {
			SortingDetail sortingDetail = new SortingDetail();
			sortingDetail.setSort(criteria.getSortingDetail().getSort());
			sortingDetail.setDirection(criteria.getSortingDetail().getDirection());
			vo.setSortingDetail(sortingDetail);
		}
		return vo;
	}
	 //从服务端得到的数据到客户端在列表显示的数据
		private List<OrderSearchVo> adapterToClient(List<OrderVo> service) {
			List<OrderSearchVo> resultList = new ArrayList<OrderSearchVo>();

			for (OrderVo item : service) {
				OrderSearchVo client = new OrderSearchVo();
				client.setId(item.getId());
				client.setName(item.getName());
				client.setAmount(item.getAmount());
				client.setStatus(item.getStatus());
				client.setGiftId(item.getGiftId());
				client.setIntegral(item.getIntegral());
				client.setOrderCode(item.getOrderCode());
				client.setRecorddate(item.getRecorddate());
				client.setGiftvo(item.getGiftvo());
				resultList.add(client);
			}

			return resultList;
		}

	@Override
	public Class<SearchOrderRequest> getActionType() {
		return SearchOrderRequest.class;
	}

	@Override
	public void rollback(SearchOrderRequest req, SearchOrderResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
