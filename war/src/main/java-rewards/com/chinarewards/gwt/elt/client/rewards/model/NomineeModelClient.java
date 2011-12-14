package com.chinarewards.gwt.elt.client.rewards.model;

import java.io.Serializable;

/**
 * 奖励 --选择员工部分的model
 * 
 * @author yanxin
 * 
 */
public class NomineeModelClient implements Serializable,
		Comparable<NomineeModelClient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4815988602631452935L;

	/**
	 * 员工Id
	 */
	private String staffId;

	/**
	 * 员工姓名
	 */
	private String name;

	/**
	 * 员工部门
	 */
	private String dept;

	/**
	 * 金额
	 */
	private Double amount;

	/**
	 * 原因
	 */
	private String reason;

	/**
	 * 员工等级
	 */
	private String staffLevelId;

	public NomineeModelClient() {

	}

	public NomineeModelClient(String staffId, String name, String dept) {
		this(staffId, name, dept, 0d, "");
	}

	public NomineeModelClient(String staffId, String name, String dept,
			String staffLevelId) {
		this(staffId, name, dept, 0d, "", staffLevelId);
	}

	public NomineeModelClient(String staffId, String name, String dept,
			Double amount, String reason) {
		this(staffId, name, dept, amount, reason, "");
	}

	public NomineeModelClient(String staffId, String name, String dept,
			Double amount, String reason, String staffLevelId) {
		this.staffId = staffId;
		this.name = name;
		this.dept = dept;
		this.amount = amount;
		this.reason = reason;
		this.staffLevelId = staffLevelId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStaffLevelId() {
		return staffLevelId;
	}

	public void setStaffLevelId(String staffLevelId) {
		this.staffLevelId = staffLevelId;
	}

	@Override
	public int compareTo(NomineeModelClient o) {
		return o == null ? -1 : o.staffId.compareTo(staffId);
	}

}
