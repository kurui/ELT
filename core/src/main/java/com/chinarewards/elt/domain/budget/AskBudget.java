package com.chinarewards.elt.domain.budget;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.domain.org.Staff;

/**
 * The persistent class for the gift database table.
 * 
 * 
 */
@Entity
public class AskBudget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, updatable = false, length = 50)
	private String id;
	private String corpBudgetId ;   //企业财年预算ID
	private String departmentId;    //申请部门ID	
	private double askIntegral;     //申请预算积分
    private double approveIntegeral;    //审批积分
    private String reason;         //申请原因
    @ManyToOne
    private Staff recorduser;     //申请人
    private Date   recorddate;     //申请时间
    @ManyToOne
    private Staff approveuser;    //审批批人
    private Date   approvedate;    //审批时间
   
	private String view;    //审批意见
    private int    status;         //是否审批   0没有审批 1审批通过,2审批不能过
    private int    deleted   ;     //是否删除 
    public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public Date getRecorddate() {
		return recorddate;
	}
	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getId() {
		return id;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorpBudgetId() {
		return corpBudgetId;
	}
	public void setCorpBudgetId(String corpBudgetId) {
		this.corpBudgetId = corpBudgetId;
	}
	
	public double getAskIntegral() {
		return askIntegral;
	}
	public void setAskIntegral(double askIntegral) {
		this.askIntegral = askIntegral;
	}
	public double getApproveIntegeral() {
		return approveIntegeral;
	}
	public void setApproveIntegeral(double approveIntegeral) {
		this.approveIntegeral = approveIntegeral;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Staff getRecorduser() {
		return recorduser;
	}
	public void setRecorduser(Staff recorduser) {
		this.recorduser = recorduser;
	}
	public Staff getApproveuser() {
		return approveuser;
	}
	public void setApproveuser(Staff approveuser) {
		this.approveuser = approveuser;
	}
	public Date getApprovedate() {
		return approvedate;
	}
	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}