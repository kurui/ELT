package com.chinarewards.gwt.elt.client.department.model;

import java.io.Serializable;

public class DepartmentVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String corporationId;
	private String name; // 部门名称
	private String leader;
	private String parentId;
	private String parentName;
	private String childdeparmentIds;
	private String childdeparmentNames;
	private String peopleNumber;
	private String yearintegral;
	private String issueintegral;
	
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

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
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

	public String getChilddeparmentIds() {
		return childdeparmentIds;
	}

	public void setChilddeparmentIds(String childdeparmentIds) {
		this.childdeparmentIds = childdeparmentIds;
	}

	public String getChilddeparmentNames() {
		return childdeparmentNames;
	}

	public void setChilddeparmentNames(String childdeparmentNames) {
		this.childdeparmentNames = childdeparmentNames;
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

}
