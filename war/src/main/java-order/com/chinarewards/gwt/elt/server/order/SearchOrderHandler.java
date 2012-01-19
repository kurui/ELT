package com.chinarewards.gwt.elt.server.order;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.order.search.OrderListVo;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.gwt.elt.adapter.order.OrderAdapter;
import com.chinarewards.gwt.elt.client.order.model.OrderSeacherVo;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author yanrui
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

		OrderSeacherVo order = request.getOrder();
		OrderListVo criteria = adapter(order);
		PageStore<OrderListVo> orderPage = null;

		UserContext uc = new UserContext();
		uc.setCorporationId(request.getCorporationId());
		uc.setUserRoles(UserRoleTool.adaptToRole(request.getUserRoles()));
		uc.setUserId(request.getUserId());

		orderPage = orderService.OrderList(uc, criteria);
		resp.setTotal(orderPage.getResultCount());
		resp.setResult(OrderAdapter.adapter(orderPage.getResultList()));

		return resp;
	}

	private OrderListVo adapter(OrderSeacherVo criteria) {
		OrderListVo vo = new OrderListVo();
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

	@Override
	public Class<SearchOrderRequest> getActionType() {
		return SearchOrderRequest.class;
	}

	@Override
	public void rollback(SearchOrderRequest req, SearchOrderResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
