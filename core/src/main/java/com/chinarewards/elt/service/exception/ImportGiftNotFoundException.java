package com.chinarewards.elt.service.exception;

import javax.ejb.ApplicationException;

/**
 * 无员工可导入时抛出该异常
 * 
 * @author yanrui
 * @since 1.5.2 
 */
@ApplicationException(rollback = true)
public class ImportGiftNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2048763120543119992L;

	public ImportGiftNotFoundException() {

	}

	public ImportGiftNotFoundException(String message) {
		super(message);
	}

	public ImportGiftNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
