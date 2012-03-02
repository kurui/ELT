package com.chinarewards.elt.model.reward.vo;

import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.person.Winner;

/**
 * The data container about RewardGrid 员工 奖励小控件 对象
 * 
 * @author yanrui
 * @since 1.5
 */
public class RewardGridVo {

	private Winner winner = new Winner();
	private Reward reward = new Reward();
	private RewardItem rewardItem = new RewardItem();

	private String winnerId = "";
	// 奖励
	private String rewardId = "";
	private String rewardName = "";
	// 奖项
	private String rewardItemId = "";
	private String rewardItemName = "";
	private double awardAmt = Double.valueOf(0);// 每人积分

	private String rewardItemPhoto = "";

	public String getRewardItemPhoto() {
		return rewardItemPhoto;
	}

	public void setRewardItemPhoto(String rewardItemPhoto) {
		this.rewardItemPhoto = rewardItemPhoto;
	}

	public Winner getWinner() {
		return winner;
	}

	public void setWinner(Winner winner) {
		this.winner = winner;
	}

	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getRewardItemId() {
		return rewardItemId;
	}

	public void setRewardItemId(String rewardItemId) {
		this.rewardItemId = rewardItemId;
	}

	public String getRewardItemName() {
		return rewardItemName;
	}

	public void setRewardItemName(String rewardItemName) {
		this.rewardItemName = rewardItemName;
	}

	public double getAwardAmt() {
		return awardAmt;
	}

	public void setAwardAmt(double awardAmt) {
		this.awardAmt = awardAmt;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public RewardItem getRewardItem() {
		return rewardItem;
	}

	public void setRewardItem(RewardItem rewardItem) {
		this.rewardItem = rewardItem;
	}

}
