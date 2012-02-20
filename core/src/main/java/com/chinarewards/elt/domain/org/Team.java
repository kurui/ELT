package com.chinarewards.elt.domain.org;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * It defines the departments of a corporation. e.g. IT department, Sales
 * Department.
 * 
 * @author lw
 * @since 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "team")
public class Team extends Organization {
		
	private String  corpId;
    private String   departId;
	private String code;//编号
	 
	private int deleted;//删除状态0存在,1删除
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "";
	}

	


	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getCode() {
		return code;
	}





	public void setCode(String code) {
		this.code = code;
	}
}
