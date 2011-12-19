package com.chinarewards.elt.model.reward.search;

public class JudgeParam {
	private String id;
	private String name;
	/**
	 * 是否提名过
	 */
	private String isNominate;
	public String getIsNominate() {
		return isNominate;
	}
	public void setIsNominate(String isNominate) {
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
