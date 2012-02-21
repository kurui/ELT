package com.chinarewards.gwt.elt.client.department.model;

import java.io.Serializable;
import java.util.List;

public class DepartmentVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String corporationId;
	private String name; // 部门名称
	private String leaderId;
	private String leaderName;	
	private String parentId;
	private String parentName;
	private List<String> childIds;
	private List<String> childNames;
	private String peopleNumber;//员工数
	private String yearintegral;//财年积分
	private String issueintegral;//已颁发积分
	private String procesRewarditemCount;//进行中奖项(数量)
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {		
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public List<String> getChildIds() {
		return childIds;
	}

	public void setChildIds(List<String> childIds) {
		this.childIds = childIds;
	}

	public List<String> getChildNames() {
		return childNames;
	}

	public void setChildNames(List<String> childNames) {
		this.childNames = childNames;
	}

	public String getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(String peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public String getYearintegral() {
		return yearintegral;
	}

	public void setYearintegral(String yearintegral) {
		this.yearintegral = yearintegral;
	}

	public String getIssueintegral() {
		return issueintegral;
	}

	public void setIssueintegral(String issueintegral) {
		this.issueintegral = issueintegral;
	}

	public String getProcesRewarditemCount() {
		return procesRewarditemCount;
	}

	public void setProcesRewarditemCount(String procesRewarditemCount) {
		this.procesRewarditemCount = procesRewarditemCount;
	}
	
	

}
