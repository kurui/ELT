package com.chinarewards.elt.domain.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.model.order.search.OrderStatus;

/**
 * The persistent class for the gift database table.
 * 
 * 
 */
@Entity
public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, updatable = false, length = 50)
	private String  id;
	private String  orderCode;  //订单编号
    private String    giftId;   //礼品ID
    private String  userId;      //订单用户
    private String  name;      //订单用户姓名
   
	private int     amount;     //数量
    private double integral;   //积分
     @Enumerated(EnumType.STRING)
	private OrderStatus status;//订单执行状态
    private int deleted;   //删除状态(0 存在,1已删删除)
    private Date    exchangeDate;////交易时间
    private Date    recorddate;   //最后更新记录时间
    private String    recorduser;   //最后更新记录的人
   
    public Date getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

	public String getRecorduser() {
		return recorduser;
	}

	public void setRecorduser(String recorduser) {
		this.recorduser = recorduser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	 public String getName() {
			return name;
		}

	public void setName(String name) {
		this.name = name;
	}

	public int getDeleted() {
		return deleted;
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

	public int isDeleted() {
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

	
	
	
	
	

}