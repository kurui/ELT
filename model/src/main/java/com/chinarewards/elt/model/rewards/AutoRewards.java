package com.chinarewards.elt.model.rewards;

/**
 * 标识这个奖项是否自动发奖,发奖到那个步骤
 * 
 * @author roger
 * 
 */
public enum AutoRewards {

	/**
	 * 不是自动颁奖
	 */
	NOTAUTO,

	/**
	 * 自动生成到审核状态(这期不做这个状态的)
	 */
	AUTO_TONOMINEE,

	/**
	 * 自动生成到交易
	 */
	AUTOTOTRANSACTION;
}
