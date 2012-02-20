package com.chinarewards.elt.model.broadcast;

import java.io.Serializable;
import java.util.Date;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.information.BroadcastingStatus;

/**
 * This class is designed to wrap the parameter to search main-accounts using
 * paging.
 * 
 * @author nicho
 * @since 2012年2月14日 14:18:46
 */
public class BroadcastQueryListCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8038695352233973821L;
	private PaginationDetail paginationDetail = new PaginationDetail();

	private SortingDetail sortingDetail = new SortingDetail();

	/**
	 * 公司ID
	 */
	private String corporationId;
	/**
	 * 状态
	 */
	private BroadcastingStatus status;
	/**
	 * 创建人ID
	 */
	private String createdByUserId;
	/**
	 * 开始时间
	 */
	private Date broadcastingTimeStart;
	/**
	 * 结束时间
	 */
	private Date broadcastingTimeEnd;
	

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

	public String getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;
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

}
