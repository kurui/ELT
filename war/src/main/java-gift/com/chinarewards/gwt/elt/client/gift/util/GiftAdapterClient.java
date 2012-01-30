package com.chinarewards.gwt.elt.client.gift.util;

import com.chinarewards.gwt.elt.client.gift.presenter.GiftPresenter.GiftDisplay;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.util.StringUtil;

/**
 * client convert util
 * 
 * @author yanrui
 * */
public class GiftAdapterClient {
	/**
	 * 封装表单属性
	 * */
	public static GiftVo adapterDisplay(GiftDisplay display) {
		GiftVo giftVo = new GiftVo();

		// // 基本信息
		giftVo.setName(display.getName().getValue().trim());
		giftVo.setSummary(display.getSummary().getValue().trim());
		giftVo.setDispatchcycle(display.getDispatchcycle().getValue().trim());
		giftVo.setExplains(display.getExplains().getValue().trim());
		giftVo.setNotes(display.getNotes().getValue().trim());

		int selectedIndex = display.getType().getSelectedIndex();
		giftVo.setType(display.getType().getValue(selectedIndex));
		// giftVo.setSource(display.getSource().getValue().trim());
		giftVo.setBrand(display.getBrand().getValue().trim());

		giftVo.setSource("合作商家");

		giftVo.setPhoto(display.getPhoto().getValue());
		giftVo.setStock(StringUtil.valueOf(display.getStock().getValue()));
		giftVo.setIntegral(StringUtil.valueOf(display.getIntegral().getValue()));

		System.out.println("-------adapter----display supply:"+display.getSupplyinner());
		System.out.println("-----------display supply:"+display.getSupplyoutter());
		
		
		giftVo.setBusiness(display.getBusiness().getValue().trim());
		giftVo.setAddress(display.getAddress().getValue().trim());
		giftVo.setTell(display.getTell().getValue().trim());
		giftVo.setServicetell(display.getServicetell().getValue().trim());

		// giftVo.setGiftStatus();
		// giftVo.setDeleted(false);
		// giftVo.setIndate(display.getIndate());

		return giftVo;
	}
}
