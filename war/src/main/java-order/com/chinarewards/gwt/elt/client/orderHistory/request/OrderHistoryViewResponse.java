package com.chinarewards.gwt.elt.client.orderHistory.request;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.order.model.OrderVo;

/**
 * @author yanrui
 */
public class OrderHistoryViewResponse implements Result {

	private OrderVo orderVo;
	private GiftVo giftVo;
	private double staffBalance;

	public OrderVo getOrderVo() {
		return orderVo;
	}

	public void setOrderVo(OrderVo orderVo) {
		this.orderVo = orderVo;
	}

	public GiftVo getGiftVo() {
		return giftVo;
	}

	public void setGiftVo(GiftVo giftVo) {
		this.giftVo = giftVo;
	}

	public double getStaffBalance() {
		return staffBalance;
	}

	public void setStaffBalance(double staffBalance) {
		this.staffBalance = staffBalance;
	}

}
