/**
 * 
 */
package com.chinarewards.gwt.elt.client.staffHeavenIndex.model;

import java.util.Date;

import com.chinarewards.gwt.elt.client.broadcasting.model.BroadcastingListCriteria.BroadcastingCategory;
import com.chinarewards.gwt.elt.client.broadcasting.model.BroadcastingListCriteria.BroadcastingStatus;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.chinarewards.gwt.elt.model.SortingDetailClient;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author nicho
 * @since 2012年2月14日 11:29:59
 */
public class StaffHeavenIndexCriteria implements IsSerializable {

	
	private PaginationDetailClient pagination;

	private SortingDetailClient sorting;

	/**
	 * 公司ID
	 */
	private String corporationId;
	/**
	 * 状态
	 */
	private BroadcastingStatus status;
	/**
	 * 创建人名称(员工名称)
	 */
	private String createdByUserName;
	/**
	 * 开始时间
	 */
	private Date broadcastingTimeStart;
	/**
	 * 结束时间
	 */
	private Date broadcastingTimeEnd;
	
	/**
	 * 广播类型
	 */
	private BroadcastingCategory category;
	
	
	public BroadcastingCategory getCategory() {
		return category;
	}

	public void setCategory(BroadcastingCategory category) {
		this.category = category;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public BroadcastingStatus getStatus() {
		return status;
	}

	public void setStatus(BroadcastingStatus status) {
		this.status = status;
	}



	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	public Date getBroadcastingTimeStart() {
		return broadcastingTimeStart;
	}

	public void setBroadcastingTimeStart(Date broadcastingTimeStart) {
		this.broadcastingTimeStart = broadcastingTimeStart;
	}

	public Date getBroadcastingTimeEnd() {
		return broadcastingTimeEnd;
	}

	public void setBroadcastingTimeEnd(Date broadcastingTimeEnd) {
		this.broadcastingTimeEnd = broadcastingTimeEnd;
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



}
