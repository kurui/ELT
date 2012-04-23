package com.chinarewards.gwt.elt.client.gift.model;

import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.chinarewards.gwt.elt.model.SortingDetailClient;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author yanrui
 * @since 1.5.2
 */
public class ImportGiftListCriteria implements IsSerializable {

	

	private PaginationDetailClient pagination;

	private SortingDetailClient sorting;
	private String batchId;
	private boolean titlefal;
	private boolean importfal;
	
	
	public boolean isImportfal() {
		return importfal;
	}
	public void setImportfal(boolean importfal) {
		this.importfal = importfal;
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
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public boolean isTitlefal() {
		return titlefal;
	}
	public void setTitlefal(boolean titlefal) {
		this.titlefal = titlefal;
	}
	
}
