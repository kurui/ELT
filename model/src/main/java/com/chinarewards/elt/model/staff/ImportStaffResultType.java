package com.chinarewards.elt.model.staff;

/**
 * 导入员工结果
 * 
 * @author wunsukun
 * @since 1.0.0 2010-09-19
 */
public enum ImportStaffResultType {

	PENDING("未决"),

	FAILURE("失败"),
	// TODO should add resend job on failure staff
	FAILURE_ON_SENDING_EMAIL("邮件发送失败"), 
	
	SUCCESS("成功");

	private String message;

	private ImportStaffResultType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
