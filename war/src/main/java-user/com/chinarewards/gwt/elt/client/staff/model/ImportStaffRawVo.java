/**
 * 
 */
package com.chinarewards.gwt.elt.client.staff.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author sunhongliang
 * @since 0.2.0 2011-01-05
 */
public class ImportStaffRawVo implements IsSerializable {

	private static final long serialVersionUID = 1329978070568965426L;


	/**
	 * 导入员工操作的导入批次id
	 */
	private String importStaffBatchId;

	/**
	 * 
	 */
	private String id;
	
	/**
	 * 部门（用|分隔符分隔部门及其子部门，例如：技术部|开发部）
	 */
	private String department;

	/**
	 * 手机号码
	 */
	private String mobileTelephoneNumber;

	/**
	 * 邮箱
	 */
	private String emailAddress;

	/**
	 * 大客户给其员工指定的会员卡号
	 */
	private String memberCardNumber;

	/**
	 * 中文姓
	 */
	private String lastName;

	/**
	 * 中文名
	 */
	private String firstName;

	/**
	 * 外文姓
	 */
	private String foreignLastName;

	/**
	 * 外文名
	 */
	private String foreignFirstName;

	/**
	 * 员工编号
	 */
	private String staffNumber;

	/**
	 * 出生日期
	 */
	private String dob;

	/**
	 * 入职时间
	 */
	private String dateOfEmployment;

	/**
	 * 职位
	 */
	private String staffPosition;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 籍贯
	 */
	private String nativePlace;

	/**
	 * 民族
	 */
	private String minorityNationality;

	/**
	 * 身份证号码
	 */
	private String idNo;

	/**
	 * 居住地
	 */
	private String location;

	/**
	 * @return the importStaffBatchId
	 */
	public String getImportStaffBatchId() {
		return importStaffBatchId;
	}

	/**
	 * @param importStaffBatchId the importStaffBatchId to set
	 */
	public void setImportStaffBatchId(String importStaffBatchId) {
		this.importStaffBatchId = importStaffBatchId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the mobileTelephoneNumber
	 */
	public String getMobileTelephoneNumber() {
		return mobileTelephoneNumber;
	}

	/**
	 * @param mobileTelephoneNumber the mobileTelephoneNumber to set
	 */
	public void setMobileTelephoneNumber(String mobileTelephoneNumber) {
		this.mobileTelephoneNumber = mobileTelephoneNumber;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the memberCardNumber
	 */
	public String getMemberCardNumber() {
		return memberCardNumber;
	}

	/**
	 * @param memberCardNumber the memberCardNumber to set
	 */
	public void setMemberCardNumber(String memberCardNumber) {
		this.memberCardNumber = memberCardNumber;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the foreignLastName
	 */
	public String getForeignLastName() {
		return foreignLastName;
	}

	/**
	 * @param foreignLastName the foreignLastName to set
	 */
	public void setForeignLastName(String foreignLastName) {
		this.foreignLastName = foreignLastName;
	}

	/**
	 * @return the foreignFirstName
	 */
	public String getForeignFirstName() {
		return foreignFirstName;
	}

	/**
	 * @param foreignFirstName the foreignFirstName to set
	 */
	public void setForeignFirstName(String foreignFirstName) {
		this.foreignFirstName = foreignFirstName;
	}

	/**
	 * @return the staffNumber
	 */
	public String getStaffNumber() {
		return staffNumber;
	}

	/**
	 * @param staffNumber the staffNumber to set
	 */
	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the dateOfEmployment
	 */
	public String getDateOfEmployment() {
		return dateOfEmployment;
	}

	/**
	 * @param dateOfEmployment the dateOfEmployment to set
	 */
	public void setDateOfEmployment(String dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	/**
	 * @return the staffPosition
	 */
	public String getStaffPosition() {
		return staffPosition;
	}

	/**
	 * @param staffPosition the staffPosition to set
	 */
	public void setStaffPosition(String staffPosition) {
		this.staffPosition = staffPosition;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the nativePlace
	 */
	public String getNativePlace() {
		return nativePlace;
	}

	/**
	 * @param nativePlace the nativePlace to set
	 */
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	/**
	 * @return the minorityNationality
	 */
	public String getMinorityNationality() {
		return minorityNationality;
	}

	/**
	 * @param minorityNationality the minorityNationality to set
	 */
	public void setMinorityNationality(String minorityNationality) {
		this.minorityNationality = minorityNationality;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportStaffRawVo [importStaffBatchId=" + importStaffBatchId
				+ ", id=" + id + ", department=" + department
				+ ", mobileTelephoneNumber=" + mobileTelephoneNumber
				+ ", emailAddress=" + emailAddress + ", memberCardNumber="
				+ memberCardNumber + ", lastName=" + lastName + ", firstName="
				+ firstName + ", foreignLastName=" + foreignLastName
				+ ", foreignFirstName=" + foreignFirstName + ", staffNumber="
				+ staffNumber + ", dob=" + dob + ", dateOfEmployment="
				+ dateOfEmployment + ", staffPosition=" + staffPosition
				+ ", gender=" + gender + ", nativePlace=" + nativePlace
				+ ", minorityNationality=" + minorityNationality + ", idNo="
				+ idNo + ", location=" + location + "]";
	}
	

}
