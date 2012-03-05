package com.chinarewards.gwt.elt.client.rewards.model;

import java.io.Serializable;
import java.util.Date;

import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria.RewardsStatus;

public class RewardsGridClient implements Serializable,
		Comparable<RewardsGridClient> {

	private static final long serialVersionUID = 1L;

	private String rewardsId;
	private String rewardsName;

	private String AwardAmt;// 奖励积分

	// 奖项
	private String rewardsItemId;
	private String rewardsItemName;
	private String rewadsItemPhoto;

	/** 奖项积分 **/
	private double totalAmtLimit;

	private String corporationId;

	private Date rewardsDate;

	private RewardsStatus status;

	public RewardsGridClient() {

	}

	public Date getRewardsDate() {
		return rewardsDate;
	}

	public void setRewardsDate(Date rewardsDate) {
		this.rewardsDate = rewardsDate;
	}

	public String getRewadsItemPhoto() {
		return rewadsItemPhoto;
	}

	public void setRewadsItemPhoto(String rewadsItemPhoto) {
		this.rewadsItemPhoto = rewadsItemPhoto;
	}

	public String getRewardsId() {
		return rewardsId;
	}

	public void setRewardsId(String rewardsId) {
		this.rewardsId = rewardsId;
	}

	public String getRewardsName() {
		return rewardsName;
	}

	public void setRewardsName(String rewardsName) {
		this.rewardsName = rewardsName;
	}

	public String getAwardAmt() {
		return AwardAmt;
	}

	public void setAwardAmt(String awardAmt) {
		AwardAmt = awardAmt;
	}

	public String getRewardsItemName() {
		return rewardsItemName;
	}

	public void setRewardsItemName(String rewardsItemName) {
		this.rewardsItemName = rewardsItemName;
	}

	public RewardsStatus getStatus() {
		return status;
	}

	public void setStatus(RewardsStatus status) {
		this.status = status;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getRewardsItemId() {
		return rewardsItemId;
	}

	public void setRewardsItemId(String rewardsItemId) {
		this.rewardsItemId = rewardsItemId;
	}

	@Override
	public String toString() {
		return "RewardsClient [baseInfo, status=" + status + " rewardsItemId="
				+ rewardsItemId + ", rewardsItemName=" + rewardsItemName
				+ ", corporationId=" + corporationId + "]";
	}

	public double getTotalAmtLimit() {
		return totalAmtLimit;
	}

	public void setTotalAmtLimit(double totalAmtLimit) {
		this.totalAmtLimit = totalAmtLimit;
	}

	@Override
	public int compareTo(RewardsGridClient arg0) {
		return 0;
	}

}
