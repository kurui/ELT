package com.chinarewards.gwt.elt.server.orderHistory;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.gift.search.GiftListVo;
import com.chinarewards.elt.model.order.search.OrderListVo;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.elt.service.user.UserService;
import com.chinarewards.gwt.elt.adapter.order.OrderAdapter;
import com.chinarewards.gwt.elt.client.order.model.OrderSearchVo;
import com.chinarewards.gwt.elt.client.orderHistory.request.SearchOrderHistoryStaffRequest;
import com.chinarewards.gwt.elt.client.orderHistory.request.SearchOrderHistoryStaffResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 2012年1月22日 
 */
public class SearchOrderHistoryStaffHandler extends
		BaseActionHandler<SearchOrderHistoryStaffRequest, SearchOrderHistoryStaffResponse> {

	@InjectLogger
	Logger logger;

	OrderService orderService;
	UserService userServie;

	@Inject
	public SearchOrderHistoryStaffHandler(OrderService orderService,UserService userServie) {
		this.orderService = orderService;
		this.userServie=userServie;
	}

	@Override
	public SearchOrderHistoryStaffResponse execute(SearchOrderHistoryStaffRequest request,
			ExecutionContext context) throws DispatchException {

		SearchOrderHistoryStaffResponse resp = new SearchOrderHistoryStaffResponse();

		OrderSearchVo orderSeacherVo = request.getOrderSearchVo();
		OrderListVo orderListVo = adapter(orderSeacherVo);//从客户端转到model		
		SysUser sysUser=userServie.findUserByStaffId(orderSeacherVo.getStaffId());
		if(sysUser!=null){
			orderListVo.setUserId(sysUser.getId());
		}
		
		
		PageStore<OrderListVo> orderPage = null;

		UserContext uc = new UserContext();
		uc.setCorporationId(request.getUserSession().getCorporationId());
		uc.setLoginName(request.getUserSession().getLoginName());
		//uc.setUserRoles(UserRoleTool.adaptToRole(request.getUserSession().getUserRoles()));
		uc.setUserId(request.getUserSession().getToken());
		
		
		
		orderPage = orderService.OrderHistoryListByStaff(uc, orderListVo);
		
		resp.setTotal(orderPage.getResultCount());
		resp.setResult(OrderAdapter.adapterToClient(orderPage.getResultList()));//从服务端转为客户端

		return resp;
	}

	private OrderListVo adapter(OrderSearchVo criteria) {
		OrderListVo vo = new OrderListVo();
		GiftListVo giftvo = new GiftListVo();
		if (criteria.getName() != null) {
			vo.setName(criteria.getName());
		}
		if (criteria.getExchangeDate() != null&&criteria.getExchangeDateEnd() != null) {
			vo.setExchangeDate(criteria.getExchangeDate());
			vo.setExchangeDateEnd(criteria.getExchangeDateEnd());
		}
		if (criteria.getStatus() != null) {
			vo.setStatus(OrderStatus.valueOf(criteria.getStatus().toString()));
		}
		
		if(criteria.getGiftVo()!=null){
			if(!StringUtil.isEmpty(criteria.getGiftVo().getSource())){
				giftvo.setSource(criteria.getGiftVo().getSource());
			}
		}
		
	
		vo.setGiftvo(giftvo);
		
		if (criteria.getPagination() != null) {
			PaginationDetail detail = new PaginationDetail();
			detail.setLimit(criteria.getPagination().getLimit());
			detail.setStart(criteria.getPagination().getStart());

			vo.setPaginationDetail(detail);
		}

		if (criteria.getSorting() != null) {
			SortingDetail sortingDetail = new SortingDetail();
			sortingDetail.setSort(criteria.getSorting().getSort());
			sortingDetail.setDirection(criteria.getSorting().getDirection());
			vo.setSortingDetail(sortingDetail);
		}
		return vo;
	}
	 
	@Override
	public Class<SearchOrderHistoryStaffRequest> getActionType() {
		return SearchOrderHistoryStaffRequest.class;
	}

	@Override
	public void rollback(SearchOrderHistoryStaffRequest req, SearchOrderHistoryStaffResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
