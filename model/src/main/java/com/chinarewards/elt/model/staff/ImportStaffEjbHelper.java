package com.chinarewards.elt.model.staff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 有些信息在model层不能检查是否违反规则，需要ejb层帮助实现
 * @author sunhongliang
 *
 */
public class ImportStaffEjbHelper {
	
	public final static String IMPORT_STAFF_DEPARTMENT_DELIMITER = "|";

	public final static char IMPORT_STAFF_DEPARTMENT_ESCAPE_CHAR = '\\';

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
	private List<String> existingStaffNumbers;

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
	private List<String> desiredStaffNumber; 
	/**
	 * the staff raw position in current uploaded file 
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



	public List<String> getDesiredStaffNumber() {
		return desiredStaffNumber;
	}

	public void setDesiredStaffNumber(List<String> desiredStaffNumber) {
		this.desiredStaffNumber = desiredStaffNumber;
	}

	/**
	 * @return the existingStaffNumbers
	 */
	public List<String> getExistingStaffNumbers() {
		return existingStaffNumbers;
	}

	/**
	 * @param existingStaffNumbers the existingStaffNumbers to set
	 */
	public void setExistingStaffNumbers(List<String> existingStaffNumbers) {
		this.existingStaffNumbers = existingStaffNumbers;
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
