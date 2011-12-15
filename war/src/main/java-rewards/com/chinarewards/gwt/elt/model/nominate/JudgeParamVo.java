package com.chinarewards.gwt.elt.model.nominate;

import java.io.Serializable;

public class JudgeParamVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	/**
	 * 是否提名过
	 */
	private boolean isNominate;
	public boolean getIsNominate() {
		return isNominate;
	}
	public void setIsNominate(boolean isNominate) {
		this.isNominate = isNominate;
	}
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
}
