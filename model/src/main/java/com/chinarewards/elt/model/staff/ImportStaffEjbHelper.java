package com.chinarewards.elt.model.staff;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * 入职日期格式字符
	 */
	private String doeFormat;

	/**
	 * 当前日期
	 */
	private Date now;
	/**
	 * CRM已经派发的卡号
	 */
   private List<String> usedMemberCardNumbersInChannel;
	/**
	 * 现有员工编号
	 */
	private List<String> existingStaffNumbers;
	/**
	 * 现有员工卡号
	 */
	private List<String> existingMemberCardNumbers;
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
	 * CRM派发的卡长度
	 */
	private int memberCardNumberLenghtInChannel;
	/**
	 * CRM派发的卡号前缀
	 */
	private String memberCardNumberPrefixInChannel;
	/**
	 * 分配企业开始的数字
	 */
	private String memberCardNumberStartInChannel;
	/**
	 * 分配企业最后的数字
	 */
	private String memberCardNumberEndInChannel;
	/**
	 * 当前要自动分配的卡号计数
	 */
	private String memberCardNumberCurrentInChannel;
	/**
	 * 预导入数据中指定的卡号
	 */
	private List<String> desiredMemberCardNumbers;
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
	// TODO add other necessary tool inside
	/**
	 * the fist card number in channel
	 */
	private Long firstCardNumberInChannel;
	/**
	 * the last card number in channel
	 */
	private Long lastCardNumberInChannel;
	/**
	 * the current card number in channel for auto assigned
	 */
	private Long currentCardNumberInChannel;
	/**
	 * @return the usedMemberCardNumbers
	 */
	public List<String> getUsedMemberCardNumbersInChannel() {
		return usedMemberCardNumbersInChannel;
	}

	/**
	 * @param usedMemberCardNumbersInChannel the usedMemberCardNumbers to set
	 */
	public void setUsedMemberCardNumbersInChannel(List<String> usedMemberCardNumbersInChannel) {
		this.usedMemberCardNumbersInChannel = usedMemberCardNumbersInChannel;
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

	public List<String> getDesiredStaffNumber() {
		return desiredStaffNumber;
	}

	public void setDesiredStaffNumber(List<String> desiredStaffNumber) {
		this.desiredStaffNumber = desiredStaffNumber;
	}

	/**
	 * @return the memberCardNumberLenghtInChannel
	 */
	public int getMemberCardNumberLenghtInChannel() {
		return memberCardNumberLenghtInChannel;
	}

	/**
	 * @param memberCardNumberLenghtInChannel the memberCardNumberLenghtInChannel to set
	 */
	public void setMemberCardNumberLenghtInChannel(
			int memberCardNumberLenghtInChannel) {
		this.memberCardNumberLenghtInChannel = memberCardNumberLenghtInChannel;
	}

	/**
	 * @return the memberCardNumberPrefixInChannel
	 */
	public String getMemberCardNumberPrefixInChannel() {
		return memberCardNumberPrefixInChannel;
	}

	/**
	 * @param memberCardNumberPrefixInChannel the memberCardNumberPrefixInChannel to set
	 */
	public void setMemberCardNumberPrefixInChannel(
			String memberCardNumberPrefixInChannel) {
		this.memberCardNumberPrefixInChannel = memberCardNumberPrefixInChannel;
	}

	/**
	 * @return the memberCardNumberStartInChannel
	 */
	public String getMemberCardNumberStartInChannel() {
		return memberCardNumberStartInChannel;
	}

	/**
	 * @param memberCardNumberStartInChannel the memberCardNumberStartInChannel to set
	 */
	public void setMemberCardNumberStartInChannel(
			String memberCardNumberStartInChannel) {
		this.memberCardNumberStartInChannel = memberCardNumberStartInChannel;
	}

	/**
	 * @return the memberCardNumberEndInChannel
	 */
	public String getMemberCardNumberEndInChannel() {
		return memberCardNumberEndInChannel;
	}

	/**
	 * @param memberCardNumberEndInChannel the memberCardNumberEndInChannel to set
	 */
	public void setMemberCardNumberEndInChannel(String memberCardNumberEndInChannel) {
		this.memberCardNumberEndInChannel = memberCardNumberEndInChannel;
	}

	/**
	 * @return the memberCardNumberCurrentInChannel
	 */
	public String getMemberCardNumberCurrentInChannel() {
		return memberCardNumberCurrentInChannel;
	}

	/**
	 * @param memberCardNumberCurrentInChannel the memberCardNumberCurrentInChannel to set
	 */
	public void setMemberCardNumberCurrentInChannel(
			String memberCardNumberCurrentInChannel) {
		this.memberCardNumberCurrentInChannel = memberCardNumberCurrentInChannel;
	}

	/**
	 * @return the desiredMemberCardNumbers
	 */
	public List<String> getDesiredMemberCardNumbers() {
		return desiredMemberCardNumbers;
	}

	/**
	 * @param desiredMemberCardNumbers the desiredMemberCardNumbers to set
	 */
	public void setDesiredMemberCardNumbers(List<String> desiredMemberCardNumbers) {
		this.desiredMemberCardNumbers = desiredMemberCardNumbers;
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

	public String getDoeFormat() {
		return doeFormat;
	}

	public void setDoeFormat(String doeFormat) {
		this.doeFormat = doeFormat;
	}

	/**
	 * @return the existingMemberCardNumbers
	 */
	public List<String> getExistingMemberCardNumbers() {
		return existingMemberCardNumbers;
	}

	/**
	 * @param existingCardMembers the existingCardMembers to set
	 */
	public void setExistingMemberCardNumbers(List<String> existingMemberCardNumbers) {
		this.existingMemberCardNumbers = existingMemberCardNumbers;
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

	/**
	 * @return the firstCardNumberInChannel
	 */
	public Long getFirstCardNumberInChannel() {
		return firstCardNumberInChannel;
	}

	/**
	 * @param firstCardNumberInChannel the firstCardNumberInChannel to set
	 */
	public void setFirstCardNumberInChannel(Long firstCardNumberInChannel) {
		this.firstCardNumberInChannel = firstCardNumberInChannel;
	}

	/**
	 * @return the lastCardNumberInChannel
	 */
	public Long getLastCardNumberInChannel() {
		return lastCardNumberInChannel;
	}

	/**
	 * @param lastCardNumberInChannel the lastCardNumberInChannel to set
	 */
	public void setLastCardNumberInChannel(Long lastCardNumberInChannel) {
		this.lastCardNumberInChannel = lastCardNumberInChannel;
	}

	/**
	 * @return the currentCardNumberInChannel
	 */
	public Long getCurrentCardNumberInChannel() {
		return currentCardNumberInChannel;
	}

	/**
	 * @param currentCardNumberInChannel the currentCardNumberInChannel to set
	 */
	public void setCurrentCardNumberInChannel(Long currentCardNumberInChannel) {
		this.currentCardNumberInChannel = currentCardNumberInChannel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportStaffEjbHelper [isAllPassed=" + isAllPassed
				+ ", dobFormat=" + dobFormat + ", doeFormat=" + doeFormat
				+ ", now=" + now + ", usedMemberCardNumbersInChannel="
				+ usedMemberCardNumbersInChannel + ", existingStaffNumbers="
				+ existingStaffNumbers + ", existingMemberCardNumbers="
				+ existingMemberCardNumbers + ", existingEmailAddress="
				+ existingEmailAddress + ", existingMobileNos="
				+ existingMobileNos + ", desiredEmailAddress="
				+ desiredEmailAddress + ", desiredMobileNos="
				+ desiredMobileNos + ", desiredStaffNumber="
				+ desiredStaffNumber + ", memberCardNumberLenghtInChannel="
				+ memberCardNumberLenghtInChannel
				+ ", memberCardNumberPrefixInChannel="
				+ memberCardNumberPrefixInChannel
				+ ", memberCardNumberStartInChannel="
				+ memberCardNumberStartInChannel
				+ ", memberCardNumberEndInChannel="
				+ memberCardNumberEndInChannel
				+ ", memberCardNumberCurrentInChannel="
				+ memberCardNumberCurrentInChannel
				+ ", desiredMemberCardNumbers=" + desiredMemberCardNumbers
				+ ", assignedCardNumber=" + assignedCardNumber
				+ ", isEmailFormatInvalid=" + isEmailFormatInvalid
				+ ", existingDepartmentMap=" + existingDepartmentMap
				+ ", isDepartmentInvalid=" + isDepartmentInvalid
				+ ", firstCardNumberInChannel=" + firstCardNumberInChannel
				+ ", lastCardNumberInChannel=" + lastCardNumberInChannel
				+ ", currentCardNumberInChannel=" + currentCardNumberInChannel
				+ "]";
	}

}
