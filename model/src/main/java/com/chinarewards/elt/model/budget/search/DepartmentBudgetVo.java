package com.chinarewards.elt.model.budget.search;

import java.util.Date;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.org.DepartmentVo;

public class DepartmentBudgetVo {
	
	
	
	
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

	
	private SortingDetail sortingDetail;
	private String  id;
	private String corpBudgetId ;   //企业财年预算ID
	private String departmentId;    //部门ID	
	private double budgetIntegral;//预算积分
    private double useIntegeral;  //已用积分
    private String recorduser;   //操作人
    private Date   recorddate;   //操作时间
    private int    deleted   ;     //是否删除 
    
    
    private String departmentName;//部门的名字
   
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
	public String getId() {
		return id;
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
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public double getBudgetIntegral() {
		return budgetIntegral;
	}
	public void setBudgetIntegral(double budgetIntegral) {
		this.budgetIntegral = budgetIntegral;
	}
	public double getUseIntegeral() {
		return useIntegeral;
	}
	public void setUseIntegeral(double useIntegeral) {
		this.useIntegeral = useIntegeral;
	}
	public String getRecorduser() {
		return recorduser;
	}
	public void setRecorduser(String recorduser) {
		this.recorduser = recorduser;
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
	
	
}
