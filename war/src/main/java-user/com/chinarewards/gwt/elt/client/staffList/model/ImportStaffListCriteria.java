/**
 * 
 */
package com.chinarewards.gwt.elt.client.staffList.model;

import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.chinarewards.gwt.elt.model.SortingDetailClient;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author nicho
 * @since 2012年2月14日 11:29:59
 */
public class ImportStaffListCriteria implements IsSerializable {

	

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
