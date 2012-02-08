package com.chinarewards.gwt.elt.client.budget.util;

import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.presenter.CorpBudgetPresenter.CorpBudgetDisplay;

/**
 * @author yanrui
 * */
public class CorpBudgetAdapterClient {
	/**
	 * 封装表单属性
	 * */
	public static CorpBudgetVo adapterDisplay(CorpBudgetDisplay display) {
		CorpBudgetVo giftVo = new CorpBudgetVo();

		// // 基本信息
//		giftVo.setName(display.getName().getValue().trim());
//
//		giftVo.setIndate(display.getIndate().getValue());

		return giftVo;
	}
}
