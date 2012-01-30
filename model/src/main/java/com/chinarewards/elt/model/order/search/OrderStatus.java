package com.chinarewards.elt.model.order.search;


//public  enum OrderStatus {
//
//	/* 初始时，为没付积分 */
//	 INITIAL,
//	  
//	/* 待发货 */	
//	NUSHIPMENTS,
//	
//	/* 已发货 */	
//	SHIPMENTS,
//	
//	/* 确认发货 */	
//	AFFIRM,
//	
//	/* 问题订单 */
//	ERRORORDER;
//}

public  enum OrderStatus {
	/* 初始时，为没付积分 */
   	 INITIAL("未付积分"),
   	  
   	/* 待发货 */	
   	NUSHIPMENTS("待发货"),
   	
   	/* 已发货 */	
   	SHIPMENTS("已发货"),
   	
   	/* 确认发货 */	
   	AFFIRM("确认发货"),
   	
   	/* 问题订单 */
   	ERRORORDER("问题定单");
	private final String displayName;

	OrderStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
