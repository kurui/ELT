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
	 * 现有员工email
	 */
	private List<String> existingName;
	/**
	 * 现有员工手机号
	 */
	private List<String> existingMobileNos;

	/**
	 * 同一上传批次员工email
	 */
	private List<String> desiredName;
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

	public List<String> getDesiredName() {
		return desiredName;
	}

	public void setDesiredName(List<String> desiredName) {
		this.desiredName = desiredName;
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
	 * @param assignedCardNumber
	 *            the assignedCardNumber to set
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
	 * @return the existingName
	 */
	public List<String> getExistingName() {
		return existingName;
	}

	/**
	 * @param existingName
	 *            the existingName to set
	 */
	public void setExistingName(List<String> existingName) {
		this.existingName = existingName;
	}

	/**
	 * @return the existingMobileNos
	 */
	public List<String> getExistingMobileNos() {
		return existingMobileNos;
	}

	/**
	 * @param existingMobileNos
	 *            the existingMobileNos to set
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
	 * @param existingDepartmentMap
	 *            the existingDepartmentMap to set
	 */
	public void setExistingDepartmentMap(
			Map<String, Object[]> existingDepartmentMap) {
		this.existingDepartmentMap = existingDepartmentMap;
	}

	/**
	 * @return the isDepartmentInvalid
	 */
	public Boolean isDepartmentInvalid() {
		return isDepartmentInvalid;
	}

	/**
	 * @param isDepartmentInvalid
	 *            the isDepartmentInvalid to set
	 */
	public void setDepartmentInvalid(Boolean isDepartmentInvalid) {
		this.isDepartmentInvalid = isDepartmentInvalid;
	}

}
