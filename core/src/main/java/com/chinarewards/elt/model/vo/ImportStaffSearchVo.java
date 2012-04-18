package com.chinarewards.elt.model.vo;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;

public class ImportStaffSearchVo {

	private PaginationDetail paginationDetail;

	private SortingDetail sortingDetail;
	
	private String batchId;
	private boolean titlefal;
	private boolean importfal;

	public boolean isImportfal() {
		return importfal;
	}

	public void setImportfal(boolean importfal) {
		this.importfal = importfal;
	}

	public boolean isTitlefal() {
		return titlefal;
	}

	public void setTitlefal(boolean titlefal) {
		this.titlefal = titlefal;
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

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}


}
