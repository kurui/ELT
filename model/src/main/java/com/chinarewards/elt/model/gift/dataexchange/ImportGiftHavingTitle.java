package com.chinarewards.elt.model.gift.dataexchange;

/**
 * 有无标题行
 * 
 * @author yanrui
 * @since 1.5.2 
 */
public enum ImportGiftHavingTitle {

	HAVING_TITLE("有标题行"),

	NO_TITLE("没有标题行");

	private String message;

	private ImportGiftHavingTitle(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
