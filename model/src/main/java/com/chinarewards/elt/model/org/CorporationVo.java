package com.chinarewards.elt.model.org;

/**
 * The model to save to update entity Corporation.
 * 
 * @author yanxin
 * @since 1.0
 */
public class CorporationVo {

	private String id;
	private String name;
	private String description;
	private String txAccountId;
	private String unitCode;

	/**
	 * This field may be null.
	 */
	private String industryId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTxAccountId() {
		return txAccountId;
	}

	public void setTxAccountId(String txAccountId) {
		this.txAccountId = txAccountId;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
}
