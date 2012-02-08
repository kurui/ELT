package com.chinarewards.gwt.elt.adapter.budget;
//package com.chinarewards.gwt.elt.adapter.budget;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.chinarewards.elt.model.gift.search.CorpBudgetListVo;
//import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetClient;
//import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetCriteria.CorpBudgetStatus;
//
///**
// * This utility class use to adapter EJB entity to WAR domain.
// * 
// * @author nicho
// * @since 0.2.0
// */
//public class CorpBudgetAdapter {
//
//	public static CorpBudgetClient adapter(CorpBudgetListVo gift) {
//		if (null == gift) {
//			return null;
//		}
//
//		CorpBudgetClient result = new CorpBudgetClient();
//
//		result.setId(gift.getId());
//		result.setName(gift.getName());
//		result.setSource(gift.getSource());
//		result.setInventory(gift.getStock() + "");
//		result.setIntegral(gift.getIntegral());
//		result.setIndate(gift.getIndate());
//		result.setPhoto(gift.getPhoto());
//
//		if (gift.getStatus() != null) {
//			result.setStatus(CorpBudgetStatus.valueOf(gift.getStatus().toString()));
//		}
//		return result;
//	}
//
//	public static List<CorpBudgetClient> adapter(List<CorpBudgetListVo> giftList) {
//		if (null == giftList) {
//			return null;
//		}
//
//		List<CorpBudgetClient> resultList = new ArrayList<CorpBudgetClient>();
//		for (CorpBudgetListVo gift : giftList) {
//			resultList.add(adapter(gift));
//		}
//		return resultList;
//	}
//	
//}
