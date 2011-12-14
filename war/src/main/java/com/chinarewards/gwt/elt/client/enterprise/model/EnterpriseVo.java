package com.chinarewards.gwt.elt.client.enterprise.model;

import java.io.Serializable;

import java.util.Date;

public class EnterpriseVo implements Serializable {
	
	private String id;

	private String address;
 
	private Date begindate;

	private String cellphone;

	private String corporation;

	private String currency;

	private String email;

	private String enterpriseName;

	private String fax;

	private String integralName;

	private double integralPrice;

	private String linkman;

	private String period;

	private String remark;

	private String subdom;

	private String tell;

	private String web;

    public EnterpriseVo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBegindate() {
		return this.begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCorporation() {
		return this.corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnterpriseName() {
		return this.enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIntegralName() {
		return this.integralName;
	}

	public void setIntegralName(String integralName) {
		this.integralName = integralName;
	}

	public double getIntegralPrice() {
		return this.integralPrice;
	}

	public void setIntegralPrice(double integralPrice) {
		this.integralPrice = integralPrice;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSubdom() {
		return this.subdom;
	}

	public void setSubdom(String subdom) {
		this.subdom = subdom;
	}

	public String getTell() {
		return this.tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

}