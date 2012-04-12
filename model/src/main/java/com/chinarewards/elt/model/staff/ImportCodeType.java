package com.chinarewards.elt.model.staff;

public enum ImportCodeType {

	SUCCESS("成功"),

	WARN("警告"),
	
	FATAL("严重问题");

	private String message;

	private ImportCodeType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
