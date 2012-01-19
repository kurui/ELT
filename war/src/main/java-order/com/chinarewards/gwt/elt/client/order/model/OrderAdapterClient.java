package com.chinarewards.gwt.elt.client.order.model;

import com.chinarewards.gwt.elt.client.order.model.OrderVo;
import com.chinarewards.gwt.elt.client.order.presenter.OrderPresenter.OrderDisplay;
import com.chinarewards.gwt.elt.util.StringUtil;
/**
 * client convert util
 * @author yanrui
 * */
public class OrderAdapterClient {
	/**
	 * 封装表单属性
	 * */
	public static OrderVo adapterDisplay(OrderDisplay display) {
		OrderVo orderVo = new OrderVo();

		// // 基本信息
		orderVo.setName(display.getName().getValue().trim());
//		orderVo.setExplains(display.getExplains().getValue().trim());
//		int selectedIndex=display.getType().getSelectedIndex();
//		orderVo.setType(display.getType().getValue(selectedIndex));
//		// orderVo.setSource(display.getSource().getValue().trim());
//		orderVo.setSource("合作商家");
//		orderVo.setBusiness(display.getBusiness().getValue().trim());
//		orderVo.setAddress(display.getAddress().getValue().trim());
//		orderVo.setTell(display.getTell().getValue().trim());
//		orderVo.setPhoto(display.getPhoto().getValue().trim());
//		orderVo.setStock(StringUtil.valueOf(display.getStock().getValue()));
//		orderVo.setIntegral(StringUtil.valueOf(display.getIntegral().getValue()));
//		orderVo.setPhoto(display.getPhoto().getValue());
		// orderVo.setOrderStatus();
		// orderVo.setDeleted(false);
		// orderVo.setIndate(display.getIndate());

		return orderVo;
	}

}
