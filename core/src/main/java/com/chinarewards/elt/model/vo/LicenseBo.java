package com.chinarewards.elt.model.vo;

import java.io.Serializable;
import java.util.Date;

public class LicenseBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String licenseId;
	private String corporationId;// 客户企业ID
	private String corporationName;// 企业名称

	private String licenseType;// 授权类型 TRIAL试用 OFFICIAL正式

	private int staffNumber = 0;// 最大员工数

	private String macaddress;// 授权网卡地址
	private Date notafter;// 截止时间

	private Date issued;// 授权时间

	private String description;// 备注说明

	// ================
	private String localMACAddress;// 本地MAC


	public String getLocalMACAddress() {
		return localMACAddress;
	}

	public void setLocalMACAddress(String localMACAddress) {
		this.localMACAddress = localMACAddress;
	}

	public int getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public Date getNotafter() {
		return notafter;
	}

	public void setNotafter(Date notafter) {
		this.notafter = notafter;
	}

	public Date getIssued() {
		return issued;
	}

	public void setIssued(Date issued) {
		this.issued = issued;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
