package com.chinarewards.elt.model.reward.base;

public enum RewardStatus {
	/* 确定获奖人 */
	DETERMINE_WINNER("确定获奖人"),
	/* 待颁奖 */
	NEW("待颁奖"),

	/* 待提名 */
	PENDING_NOMINATE("待提名"),

	/* 待审批 */
	PENDING_APPLICATION("待审批"),
	
	/* 已完成 */
	REWARDED("已完成"),

	/* 已否决 */
	DENIED(" 已否决 ");
	
	private RewardStatus(String displayName) {
		this.displayName = displayName;
	}

	String displayName;

	public String getDisplayName() {
		return this.displayName;
	}
}
