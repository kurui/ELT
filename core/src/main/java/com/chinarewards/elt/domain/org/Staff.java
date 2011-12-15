package com.chinarewards.elt.domain.org;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.chinarewards.elt.model.org.Gender;
import com.chinarewards.elt.model.org.NoticeMode;

/**
 * It defines a staff in a corporation.
 * 
 * @author yanxin
 * @since 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "staff")
public class Staff extends Organization {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3618719256920413167L;

	/**
	 * It associate to a Department.
	 */
	@ManyToOne
	private Department department;

	/**
	 * It associate a corporation.
	 */
	@ManyToOne
	private Corporation corporation;

	/**
	 * mobile phone number
	 */
	private String phone;

	/**
	 * email
	 */
	private String email;

	/**
	 * notification mode
	 */
	@Enumerated(EnumType.STRING)
	private NoticeMode noticeMode;

	/**
	 * Flag whether is deleted
	 */
	private boolean deleted;

	/**
	 * account id of outer tx.
	 */
	private String txAccountId;

	/**
	 * surname, e.g. 严 or Michael(迈克尔)
	 */
	private String surname;

	/**
	 * personal name, e.g. 宝宝 or Jackson(杰克逊)
	 */
	private String personalName;

	/**
	 * job number(工号)
	 */
	private String jobNo;

	/**
	 * date of birthday(生日)
	 */
	private Date dob;

	/**
	 * entry date means which day you enter the corporation.(入职日期)
	 */
	private Date entryDate;

	/**
	 * position in the corporation(职位)
	 */
	private String jobPosition;

	/**
	 * gender
	 */
	@Enumerated(EnumType.STRING)
	private Gender gender;

	/**
	 * native place(籍贯)
	 */
	private String nativePlace;

	/**
	 * nation(民族)
	 */
	private String nation;

	/**
	 * identification card code(身份证号)
	 */
	private String IDCardNo;

	/**
	 * address (居住地)
	 */
	private String location;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Corporation getCorporation() {
		return corporation;
	}

	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public NoticeMode getNoticeMode() {
		return noticeMode;
	}

	public void setNoticeMode(NoticeMode noticeMode) {
		this.noticeMode = noticeMode;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getTxAccountId() {
		return txAccountId;
	}

	public void setTxAccountId(String txAccountId) {
		this.txAccountId = txAccountId;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getIDCardNo() {
		return IDCardNo;
	}

	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
