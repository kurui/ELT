package com.chinarewards.gwt.elt.client.order.model;

import java.io.Serializable;
import java.util.Date;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.gift.search.GiftListVo;
import com.chinarewards.elt.model.order.search.OrderStatus;

public class OrderSearchVo implements Serializable, Comparable<OrderSearchVo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4934837755724342679L;

	public OrderSearchVo() {
	}
	
	private PaginationDetail paginationDetail;
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
	/**
	 * Sorting detail.
	 */
	
	private SortingDetail sortingDetail;
	private String  id;
	private String  orderCode;       //订单编号
    private String    giftId;   //礼品ID
    private String  userId;      //订单用户
    private int     amount;     //数量
    private double integral;   //积分
    private String  name;      //订单用户姓名
	private OrderStatus status;//订单执行状态
	private int deleted;   //删除状态(0 存在,1已删删除)
    private Date    recorddate;   //交易时间
    
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getGiftId() {
		return giftId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getIntegral() {
		return integral;
	}
	public void setIntegral(double integral) {
		this.integral = integral;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public Date getRecorddate() {
		return recorddate;
	}
	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}
	private GiftListVo giftvo;//订单的VO
	public GiftListVo getGiftvo() {
		return giftvo;
	}
	public void setGiftvo(GiftListVo giftvo) {
		this.giftvo = giftvo;
	}
	@Override
	public int compareTo(OrderSearchVo o) {
		return o == null ? -1 : o.getId().compareTo(id);

	}
}
