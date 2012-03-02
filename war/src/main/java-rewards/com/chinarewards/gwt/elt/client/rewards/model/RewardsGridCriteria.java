package com.chinarewards.gwt.elt.client.rewards.model;

import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.chinarewards.gwt.elt.model.SortingDetailClient;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author yanrui
 * @since 1.5
 */
public class RewardsGridCriteria implements IsSerializable {

	private PaginationDetailClient pagination;

	private SortingDetailClient sorting;

	private String corporationId;

	private String staffId;

	private String thisAction;

	@Override
	public String toString() {
		return "RewardsCriteria [pagination=" + pagination + ", sorting="
				+ sorting + ", thisAction=" + thisAction + "]";
	}

	public PaginationDetailClient getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDetailClient pagination) {
		this.pagination = pagination;
	}

	public SortingDetailClient getSorting() {
		return sorting;
	}

	public void setSorting(SortingDetailClient sorting) {
		this.sorting = sorting;
	}



	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getThisAction() {
		return thisAction;
	}

	public void setThisAction(String thisAction) {
		this.thisAction = thisAction;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	
}
