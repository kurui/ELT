package com.chinarewards.gwt.elt.server.order;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.slf4j.Logger;
import com.chinarewards.elt.domain.order.Orders;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.request.EditOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.EditOrderResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author YanRui
 * */
public class EditOrderHandler extends
		BaseActionHandler<EditOrderRequest, EditOrderResponse> {

	@InjectLogger
	Logger logger;
	OrderService orderService;

	@Inject
	public EditOrderHandler(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public Class<EditOrderRequest> getActionType() {
		return EditOrderRequest.class;
	}

	@Override
	public EditOrderResponse execute(EditOrderRequest action,
			ExecutionContext context) throws DispatchException {
		logger.debug("AddOrderResponse , order:" + action.getOrderVo().toString());
		logger.debug("AddOrderResponse ,rewardId=" + action.getOrderVo().getId());

		OrderVo orderVo = action.getOrderVo();
		
		Orders order = assembleOrder(orderVo);

		UserContext uc = new UserContext();
		uc.setCorporationId(action.getUserSession().getCorporationId());
		uc.setLoginName(action.getUserSession().getLoginName());
		uc.setUserId(action.getUserSession().getToken());
		uc.setUserRoles(UserRoleTool.adaptToRole(action.getUserSession()
				.getUserRoles()));
		Orders AdddItem = orderService.save(uc, order);

		return new EditOrderResponse(AdddItem.getId());
	}
	
	/**
	 * Convert from OrderVo to GeneratorOrderModel.
	 */
	public static Orders assembleOrder(OrderVo orderVo) {
		Orders order = new Orders();
		order.setId(orderVo.getId());
		order.setName(orderVo.getName());

//		order.setExplains(orderVo.getExplains().trim());
//		order.setType(orderVo.getType().trim());
//		order.setSource(orderVo.getSource().trim());
//		order.setBusiness(orderVo.getBusiness().trim());
//		order.setAddress(orderVo.getAddress().trim());
//		order.setTell(orderVo.getTell().trim());
//		order.setIntegral(orderVo.getIntegral());
//		order.setStock(orderVo.getStock());
//		order.setPhoto(orderVo.getPhoto());
		// order.setOrderStatus();
		// order.setIndate(getIndate());
		// private OrderStatus status; //状态（上下架）
		// private boolean deleted; //删除状态
		// private Date indate ; //有效截止期

		return order;
	}


	@Override
	public void rollback(EditOrderRequest action, EditOrderResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
