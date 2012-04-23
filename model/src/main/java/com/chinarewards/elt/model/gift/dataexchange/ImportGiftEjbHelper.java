package com.chinarewards.elt.model.gift.dataexchange;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanrui
 * @since 1.5.2 
 *
 */
public class ImportGiftEjbHelper {
	
	public final static String IMPORT_Gift_DEPARTMENT_DELIMITER = "|";

	public final static char IMPORT_Gift_DEPARTMENT_ESCAPE_CHAR = '\\';

	/**
	 * 是否通过所有检查
	 */
	private boolean isAllPassed;
	/**
	 * 出生日期格式字符
	 */
	private String dobFormat;

	/**
	 * 当前日期
	 */
	private Date now;

	/**
	 * 现有员工编号
	 */
	private List<String> existingGiftNumbers;

	/**
	 * 现有员工email
	 */
	private List<String> existingEmailAddress;
	/**
	 * 现有员工手机号
	 */
	private List<String> existingMobileNos;
	/**
	 * 同一上传批次员工email
	 */
	private List<String> desiredEmailAddress;
	/**
	 * 同一上传批次员工手机号
	 */
	private List<String> desiredMobileNos;
	/**
	 * 同一上传批次员工编号
	 */
	private List<String> desiredGiftNumber; 
	/**
	 * the Gift raw position in current uploaded file 
	 */
	private String assignedCardNumber;
	/**
	 * check if email format invalid
	 */
	private Boolean isEmailFormatInvalid;
	/**
	 * existing department map
	 */
	private Map<String, Object[]> existingDepartmentMap = new HashMap<String, Object[]>();
	/**
	 * department parent and child is invalid
	 */
	private Boolean isDepartmentInvalid;



	public List<String> getDesiredGiftNumber() {
		return desiredGiftNumber;
	}

	public void setDesiredGiftNumber(List<String> desiredGiftNumber) {
		this.desiredGiftNumber = desiredGiftNumber;
	}

	/**
	 * @return the existingGiftNumbers
	 */
	public List<String> getExistingGiftNumbers() {
		return existingGiftNumbers;
	}

	/**
	 * @param existingGiftNumbers the existingGiftNumbers to set
	 */
	public void setExistingGiftNumbers(List<String> existingGiftNumbers) {
		this.existingGiftNumbers = existingGiftNumbers;
	}

	public List<String> getDesiredEmailAddress() {
		return desiredEmailAddress;
	}

	public void setDesiredEmailAddress(List<String> desiredEmailAddress) {
		this.desiredEmailAddress = desiredEmailAddress;
	}

	public List<String> getDesiredMobileNos() {
		return desiredMobileNos;
	}

	public void setDesiredMobileNos(List<String> desiredMobileNos) {
		this.desiredMobileNos = desiredMobileNos;
	}

	


	/**
	 * @return the assignedCardNumber
	 */
	public String getAssignedCardNumber() {
		return assignedCardNumber;
	}

	/**
	 * @param assignedCardNumber the assignedCardNumber to set
	 */
	public void setAssignedCardNumber(String assignedCardNumber) {
		this.assignedCardNumber = assignedCardNumber;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String getDobFormat() {
		return dobFormat;
	}

	public void setDobFormat(String dobFormat) {
		this.dobFormat = dobFormat;
	}

	public Boolean isEmailFormatInvalid() {
		return isEmailFormatInvalid;
	}

	public void setEmailFormatInvalid(Boolean isEmailFormatValid) {
		this.isEmailFormatInvalid = isEmailFormatValid;
	}

	public Boolean isAllPassed() {
		return isAllPassed;
	}

	public void setAllPassed(Boolean isAllPassed) {
		this.isAllPassed = isAllPassed;
	}

	



	/**
	 * @return the existingEmailAddress
	 */
	public List<String> getExistingEmailAddress() {
		return existingEmailAddress;
	}

	/**
	 * @param existingEmailAddress the existingEmailAddress to set
	 */
	public void setExistingEmailAddress(List<String> existingEmailAddress) {
		this.existingEmailAddress = existingEmailAddress;
	}

	/**
	 * @return the existingMobileNos
	 */
	public List<String> getExistingMobileNos() {
		return existingMobileNos;
	}

	/**
	 * @param existingMobileNos the existingMobileNos to set
	 */
	public void setExistingMobileNos(List<String> existingMobileNos) {
		this.existingMobileNos = existingMobileNos;
	}

	/**
	 * @return the existingDepartmentMap
	 */
	public Map<String, Object[]> getExistingDepartmentMap() {
		return existingDepartmentMap;
	}

	/**
	 * @param existingDepartmentMap the existingDepartmentMap to set
	 */
	public void setExistingDepartmentMap(Map<String, Object[]> existingDepartmentMap) {
		this.existingDepartmentMap = existingDepartmentMap;
	}

	/**
	 * @return the isDepartmentInvalid
	 */
	public Boolean isDepartmentInvalid() {
		return isDepartmentInvalid;
	}

	/**
	 * @param isDepartmentInvalid the isDepartmentInvalid to set
	 */
	public void setDepartmentInvalid(Boolean isDepartmentInvalid) {
		this.isDepartmentInvalid = isDepartmentInvalid;
	}

	
}
