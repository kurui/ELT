package com.chinarewards.elt.domain.org;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.model.staff.ImportStaffResultType;

/**
 * 员工导入的原始数据
 * 
 * @author
 * @since 1.0.0 2010-09-19
 */
@Entity
public class ImportStaffRaw implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649501912061452575L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/**
	 * 导入员工操作的批次导入信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ImportStaffBatch importStaffBatch;

	/**
	 * 行数
	 */
	private Long rowPos;

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
	 * 中文姓名
	 */
	private String name;

	/**
	 * 员工编号
	 */
	private String staffNumber;

	/**
	 * 出生日期
	 */
	private String dob;

	/**
	 * 导入结果
	 */
	@Enumerated(EnumType.STRING)
	private ImportStaffResultType result;
	
	private String jobPosition;
	private String leadership;
	/**
	 * 预导入标志-默认0,--修改1为pass掉
	 */
	private Integer importfal;
	
	public Integer getImportfal() {
		return importfal;
	}

	public void setImportfal(Integer importfal) {
		this.importfal = importfal;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getLeadership() {
		return leadership;
	}

	public void setLeadership(String leadership) {
		this.leadership = leadership;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ImportStaffBatch getImportStaffBatch() {
		return importStaffBatch;
	}

	public void setImportStaffBatch(ImportStaffBatch importStaffBatch) {
		this.importStaffBatch = importStaffBatch;
	}

	public Long getRowPos() {
		return rowPos;
	}

	public void setRowPos(Long rowPos) {
		this.rowPos = rowPos;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMobileTelephoneNumber() {
		return mobileTelephoneNumber;
	}

	public void setMobileTelephoneNumber(String mobileTelephoneNumber) {
		this.mobileTelephoneNumber = mobileTelephoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public ImportStaffResultType getResult() {
		return result;
	}

	public void setResult(ImportStaffResultType result) {
		this.result = result;
	}

}
