package com.chinarewards.elt.model.reward.search;

import java.util.Date;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.reward.base.RewardStatus;

/**
 * It is used to query for Reward Grid
 * 
 * @author yanrui
 * @since 1.0
 */
public class RewardGridSearchVo {

	private PaginationDetail paginationDetail;

	private SortingDetail sortingDetail;

	/**
	 * The staff who win a reward
	 */
	private String winnerStaffId;
	private String winnerStaffName;

	private String rewardId;

	private RewardStatus status;

	private String rewardItemId;

	private String corporationId;

	private Date lastMonth;// 上个月是

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public Date getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(Date lastMonth) {
		this.lastMonth = lastMonth;
	}

	public PaginationDetail getPaginationDetail() {
		return paginationDetail;
	}

	public void setPaginationDetail(PaginationDetail paginationDetail) {
		this.paginationDetail = paginationDetail;
	}

	public SortingDetail getSortingDetail() {
		return sortingDetail;
	}

	public void setSortingDetail(SortingDetail sortingDetail) {
		this.sortingDetail = sortingDetail;
	}

	public String getWinnerStaffName() {
		return winnerStaffName;
	}

	public void setWinnerStaffName(String winnerStaffName) {
		this.winnerStaffName = winnerStaffName;
	}

	public RewardStatus getStatus() {
		return status;
	}

	public void setStatus(RewardStatus status) {
		this.status = status;
	}

	public String getRewardItemId() {
		return rewardItemId;
	}

	public void setRewardItemId(String rewardItemId) {
		this.rewardItemId = rewardItemId;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getWinnerStaffId() {
		return winnerStaffId;
	}

	public void setWinnerStaffId(String winnerStaffId) {
		this.winnerStaffId = winnerStaffId;
	}
}
