package com.chinarewards.elt.model.gift.dataexchange;

/**
 * 导入员工结果
 * 
 * @author yanrui
 * @since 1.5.2 
 */
public enum ImportGiftResultType {

	PENDING("未决"),

	FAILURE("失败"),
	// TODO should add resend job on failure Gift
	FAILURE_ON_SENDING_EMAIL("邮件发送失败"), 
	
	SUCCESS("成功");

	private String message;

	private ImportGiftResultType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
