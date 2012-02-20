package com.chinarewards.elt.domain.information;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.chinarewards.elt.domain.org.Corporation;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "team")
public class OrgObject extends ReceivingObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Corporation corporation;
	public Corporation getCorporation() {
		return corporation;
	}
	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
	}



}
