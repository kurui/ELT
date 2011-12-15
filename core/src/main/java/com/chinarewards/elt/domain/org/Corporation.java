package com.chinarewards.elt.domain.org;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * It defines a corporation.
 * 
 * @author yanxin
 * @since 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "corporation")
public class Corporation extends Organization {

	private static final long serialVersionUID = -2314873131853974603L;

	/**
	 * Which industry this corporation belongs to.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Industry industry;

	/**
	 * account id of tx
	 */
	private String txAccountId;

	/**
	 * Which unit code this corporation use.
	 */
	private String defaultUnitCode;

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getTxAccountId() {
		return txAccountId;
	}

	public void setTxAccountId(String txAccountId) {
		this.txAccountId = txAccountId;
	}

	public String getDefaultUnitCode() {
		return defaultUnitCode;
	}

	public void setDefaultUnitCode(String defaultUnitCode) {
		this.defaultUnitCode = defaultUnitCode;
	}

}
