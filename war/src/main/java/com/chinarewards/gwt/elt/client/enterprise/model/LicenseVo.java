package com.chinarewards.gwt.elt.client.enterprise.model;

import java.io.Serializable;
import java.util.Date;

import com.chinarewards.gwt.elt.util.StringUtil;

public class LicenseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String licenseId;
	private String corporationId;// 客户企业ID
	private String corporationName;// 企业名称
	private String licenseType;//授权类型 TRIAL试用  OFFICIAL正式
	
	private String macaddress;//网卡地址
	private Date notafter;//截止时间	
	
	private Date issued;//授权时间
	private String description;// 备注说明
	private int staffNumber;//授权最大用户数
	//============
	private String localmacaddress;//本地当前网卡
	
	public String getLicenseTypeName(){
		String name="";
		if (!StringUtil.isEmpty(licenseType)) {
			if ("TRIAL".equals(licenseType)) {
				name="试用版";
			}else if("OFFICIAL".equals(licenseType)){
				name="正式版";
			}
		}
		return name;
	}
	
	

	public int getStaffNumber() {
		return staffNumber;
	}



	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}



	public String getLocalmacaddress() {
		return localmacaddress;
	}



	public void setLocalmacaddress(String localmacaddress) {
		this.localmacaddress = localmacaddress;
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