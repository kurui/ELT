package com.chinarewards.elt.service.order.impl;

import java.util.Arrays;
import java.util.List;

import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.domain.order.Order;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.order.search.OrderStatus;
import com.chinarewards.elt.model.order.search.OrderListVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.service.gift.GiftLogic;
import com.chinarewards.elt.service.order.OrderLogic;
import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.tx.exception.BalanceLackException;
import com.chinarewards.elt.tx.service.TransactionService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
@Transactional
public class OrderServiceImpl implements OrderService {
	private final OrderLogic orderLogic;
	private final GiftLogic giftLogic;
	private final UserLogic userLogic;
    private final TransactionService tx;
	@Inject
	public OrderServiceImpl(OrderLogic orderLogic,UserLogic userLogic
			,GiftLogic giftLogic,TransactionService tx) {
		this.userLogic = userLogic;
		this.orderLogic = orderLogic;
		this.giftLogic = giftLogic;
		this.tx = tx;
		
	}
	@Override
	public Order save(UserContext context, Order Order) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		Order Orders = orderLogic.save(caller, Order);
		return Orders;
	}

	@Override
	public Order findOrderById(String id) {
		
		return orderLogic.findOrderById(id);
	}

	@Override
	public String deleteOrder(UserContext context,String id) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		return orderLogic.deleteOrder(caller,id);
	}

	@Override
	public PageStore<OrderListVo> OrderList(UserContext context, OrderListVo OrderVo) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		List<UserRole> roles =Arrays.asList(context.getUserRoles());
		//如果是HR或礼品管理员，可以查看所有订单，
		if (roles.contains(UserRole.CORP_ADMIN)||roles.contains(UserRole.GIFT))
			OrderVo.setUserId("");//不传订单用户的参数
		else
			OrderVo.setUserId(caller.getId());//把登录人的用户ID传过去做为条件
		return orderLogic.OrderList(caller, OrderVo);
	}

	@Override
	public String updateStatus(UserContext context,String id,OrderStatus status) {
		SysUser caller = userLogic.findUserById(context.getUserId());
		String orderId = orderLogic.updateStatus(caller,id,status);//更新状态
		String returnValue="ok";
		if(orderId!=null&&orderId.equals(id)){//如果更新执行状态成功并是当前的订单ID
		  if(status.equals(OrderStatus.NUSHIPMENTS)){//没付积分改为没发货状态，扣积分和减少库存量
			  returnValue=  updateStock( context, id);
			 
		  }
		}	
		return returnValue;
	}
	
    public String updateStock(UserContext context,String id){
    	 
    	 SysUser caller = userLogic.findUserById(context.getUserId());
		 Order order = orderLogic.findOrderById(id);//得到订单信息
		 String giftId = order.getGiftId();          //得到礼品的ID
		 Gift gift = giftLogic.findGiftById(giftId);//查找礼品的信息
		 gift.setStock(gift.getStock()-order.getAmount());
		 giftLogic.save(caller, gift);//减少库存量
		 String returnValue = transaction( context,order.getIntegral(),order.getId());//交易积分
		 return returnValue;
    }
    
    public String transaction(UserContext context,double amount,String orderId){
    	SysUser caller = userLogic.findUserById(context.getUserId());
    	String  toAccountId =caller.getCorporation().getTxAccountId();
    	String  unitCode = caller.getCorporation().getDefaultUnitCode();
    	String  fromAccountId = caller.getStaff().getTxAccountId();
    	String returnValue= "ok";
    	try {
			tx.transaction(fromAccountId, toAccountId, unitCode, amount);
		} catch (BalanceLackException e) {
			returnValue= "fail";
		}
    	return returnValue;
    }
	@Override
	public String updateStatus(String orderId, OrderStatus updateStatus) {
		// TODO Auto-generated method stub
		return null;
	}
}
