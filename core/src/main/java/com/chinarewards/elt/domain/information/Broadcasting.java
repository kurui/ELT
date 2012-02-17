package com.chinarewards.elt.domain.information;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.information.BroadcastingCategory;
import com.chinarewards.elt.model.information.BroadcastingStatus;
@Entity
public class Broadcasting implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, updatable = false, length = 50)
	private String id;
	/**
	 * 编号
	 */
	private String number;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 状态
	 */
	private BroadcastingStatus status;

	@ManyToOne
	private SysUser createdBy;	
	/**
	 * 类别
	 */
	private BroadcastingCategory category;
	/**
	 * 开始时间
	 */
	private Date broadcastingTimeStart;
	/**
	 * 结束时间
	 */
	private Date broadcastingTimeEnd;
	@ManyToOne
	private SysUser lastModifiedBy;
	
	private Date createdAt;

	private Date lastModifiedAt;
	/**
	 * 是否允许回复
	 */
	private boolean isAllowreplies;
	
	/**
	 * 接收对象
	 */
	private List<ReceivingObject> receivingObject;
	
	
	
	
	public List<ReceivingObject> getReceivingObject() {
		return receivingObject;
	}
	public void setReceivingObject(List<ReceivingObject> receivingObject) {
		this.receivingObject = receivingObject;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BroadcastingStatus getStatus() {
		return status;
	}
	public void setStatus(BroadcastingStatus status) {
		this.status = status;
	}
	public SysUser getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(SysUser createdBy) {
		this.createdBy = createdBy;
	}
	public BroadcastingCategory getCategory() {
		return category;
	}
	public void setCategory(BroadcastingCategory category) {
		this.category = category;
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
	public SysUser getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(SysUser lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	public boolean isAllowreplies() {
		return isAllowreplies;
	}
	public void setAllowreplies(boolean isAllowreplies) {
		this.isAllowreplies = isAllowreplies;
	}
	
	
	
}
