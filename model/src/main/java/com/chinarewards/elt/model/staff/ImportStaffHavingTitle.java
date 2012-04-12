package com.chinarewards.elt.model.staff;

/**
 * 有无标题行
 * 
 * @author sunhongliang
 * @since 1.0.0 2010-09-19
 */
public enum ImportStaffHavingTitle {

	HAVING_TITLE("有标题行"),

	NO_TITLE("没有标题行");

	private String message;

	private ImportStaffHavingTitle(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
